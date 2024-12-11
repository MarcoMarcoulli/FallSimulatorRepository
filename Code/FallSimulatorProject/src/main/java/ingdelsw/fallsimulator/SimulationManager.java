// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ingdelsw.fallsimulator;

//codice esaminato ed approvato, trovo che le classi siano ben strutturate ed i metodi chiari
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ingdelsw.fallsimulator.listeners.MassArrivalListener;
import ingdelsw.fallsimulator.math.Point;
import ingdelsw.fallsimulator.math.curves.Curve;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class SimulationManager {
	
	private static final Logger logger = LogManager.getLogger(SimulationManager.class);

    private Mass mass; 
    private Curve curve;
    private Point[] points;
    private double[] slopes;
    private double[] times;
    
    Pane controlPane;
    private MassArrivalListener listener;
   
    private long startTime; // Tempo di inizio dell'animazione in nanosecondi

    public SimulationManager(Curve curve, MassArrivalListener listener) {
        mass = null;
        this.curve = curve;
        this.points = curve.calculatePoints();
        this.listener = listener;
    }

    public Curve getCurve() {
        return curve;
    }

    public void setMass(Mass mass) {
        this.mass = mass;
    }

    public Mass getMass() {
        return mass;
    }

    public Point[] getPoints() {
        return points;
    }
    
    public double getArrivalTime() {
    	return times[points.length - 1];
    }

    public void setSlopes(double[] slopes) {
        this.slopes = slopes;
    }
    

    public double[] calculateTimeParametrization(double g) {
        times = new double[points.length];
        times[0] = 0;
        times[1] = Double.MIN_VALUE;
        double h1;
        double h2;
        double v1;
        double v2;
        double v1y;
        double v2y;
        double integrand;
        double dy;
        logger.info("parametrizzazione curva rispetto al tempo");
        for (int i = 1; i < points.length-1; i++) {
        	h1 = points[i].getY() - curve.getStartPoint().getY();
        	
        	if(h1==0){
        		times[i+1] = times[i] + Double.MIN_VALUE;
        		continue;
        	}
        	
        	h2 = points[i+1].getY() - curve.getStartPoint().getY();
        	v1 = Math.sqrt(2*g*h1);
        	v2 = Math.sqrt(2*g*h2);
        	v1y = v1*Math.abs(Math.sin(slopes[i]));
        	v2y = v2*Math.abs(Math.sin(slopes[i+1]));
        	dy = (Math.abs(points[i+1].getY() - points[i].getY()));
        	integrand = ((1/v1y + 1/v2y)/2);
        	
        	times[i+1] = times[i] + integrand * dy;
        	
        	logger.debug("h1 [{}] : {}",i, h1);
        	logger.debug("velocità [{}] : {}",i, v1y);
            logger.debug(" tempi [{}] : {}",i+1, times[i+1]);
        }
        return times;
    }

	
	public void setMassArrivalListener(MassArrivalListener listener)
    {
    	this.listener = listener;
    }
	
    public void startAnimation() {
    	
    	AnimationTimer timer; 
    	
    	double x0 = points[0].getX() - mass.getMassDiameter() / 2;
    	double y0 = points[0].getY() - mass.getMassDiameter() / 2;
    	
        mass.getIcon().relocate(x0, y0);

        startTime = 0;
        
        timer = new AnimationTimer() {
        	
        	@Override
        	public void handle(long now) {
        	    if (startTime == 0) 
        	        startTime = now; // Inizializza il tempo di inizio

        	    double elapsedTime = (now - startTime) / 1_000_000_000.0; // Tempo trascorso in secondi

        	    // Trova l'indice più vicino utilizzando la ricerca binaria
        	    int index = Arrays.binarySearch(times, elapsedTime);

        	    if (index < 0) {
        	        // Se non c'è una corrispondenza esatta, `binarySearch` restituisce -(insertionPoint) - 1
        	        index = -index - 2; // Otteniamo l'indice dell'elemento precedente
        	    }

        	    // Gestione dell'ultimo segmento
        	    if (index >= times.length - 1) {
        	        double newX = points[points.length - 1].getX() - mass.getMassDiameter() / 2;
        	        double newY = points[points.length - 1].getY() - mass.getMassDiameter() / 2;
        	        mass.getIcon().relocate(newX, newY);
        	        this.stop();
        	        listener.onMassArrival(SimulationManager.this, true);
        	        return;
        	    }

        	    // Gestione di una condizione speciale
        	    if (index < times.length - 2 && points[index + 2].getY() < points[0].getY()) {
        	        mass.getIcon().relocate(points[index].getX() - mass.getMassDiameter() / 2,
        	                                points[index].getY() - mass.getMassDiameter() / 2);
        	        this.stop();
        	        listener.onMassArrival(SimulationManager.this, false);
        	        return;
        	    }

        	    // Calcola la posizione interpolata tra points[index] e points[index + 1]
        	    double ratio = (elapsedTime - times[index]) / (times[index + 1] - times[index]);
        	    double x = points[index].getX() + (points[index + 1].getX() - points[index].getX()) * ratio - mass.getMassDiameter() / 2;
        	    double y = points[index].getY() + (points[index + 1].getY() - points[index].getY()) * ratio - mass.getMassDiameter() / 2;

        	    // Posiziona la massa
        	    mass.getIcon().relocate(x, y);
        	}

        };

        // Avvia l'AnimationTimer
        timer.start();
    }
}