// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ingdelsw.fallsimulator.math.curves;

import ingdelsw.fallsimulator.math.NonConvergenceException;
import ingdelsw.fallsimulator.math.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cycloid extends Curve {
	
    private static final Logger logger = LogManager.getLogger(Cycloid.class);

    private double alfa; //parameter value for the end point
    private double r; // radius of the generator circle

    public Cycloid(Point startPoint, Point endPoint) throws NonConvergenceException {
        super(startPoint, endPoint);
        alfa = calculateAlfa();
        r = calculateR(intervalY);
    }

    //function to calculate zeros with Newton-Raphson method
    private double f(double a) {
        return ((a - Math.sin(a)) / (1 - Math.cos(a))) - (intervalX / intervalY);
    }
     
    //derivative of the function
    private double df(double a) {
        double numerator = Math.pow(Math.sin(a), 2) - a * Math.sin(a);
        double denominator = Math.pow(1 - Math.cos(a), 2);
        return 1 + numerator / denominator;
    }

    // Newton-Raphson method for finding t
    private double calculateAlfa() throws NonConvergenceException {
        double alfaLocal = 4 * Math.atan(intervalX / (2 * intervalY)); // good initial approximation
        int maxIterations = 100;
        for (int i = 0; i < maxIterations; i++) {
            double alfaNew = alfaLocal - f(alfaLocal) / df(alfaLocal);

            //method convergence control
            if (Math.abs(alfaNew - alfaLocal) < 1e-6) {
                logger.info("Convergenza raggiunta: alfa = {}", alfaNew);
                return alfaNew; // return value when it is near enough
            }
            alfaLocal = alfaNew;
        }
        logger.error("Il metodo di Newton-Raphson non converge dopo {} iterazioni", maxIterations);
        throw new NonConvergenceException(maxIterations);
    }

    private double calculateR(double y) {
        double rad = y / (1 - Math.cos(alfa));
        logger.debug("Raggio calcolato: {}", r);
        return rad;
    }

    //x component parametric function for a cycloid with vertical tangent in the origin
    public double evaluateX(double a) {
        return r * (a - Math.sin(a));
    }

    //y component parametric function for a cycloid with vertical tangent in the origin
    public double evaluateY(double a) {
        return r * (1 - Math.cos(a));
    }

    public Point[] calculatePoints() {
        Point[] points = new Point[NUMPOINTS];
        double t;
        double aCubic;
        double x;
        double y;
        logger.info("calcolo punti cicloide");
        for (int i = 0; i < NUMPOINTS; i++) {
            t = (double) i / (NUMPOINTS - 1);
            aCubic = alfa * Math.pow(t, 3);
            x = startPoint.getX() + evaluateX(aCubic);
            y = startPoint.getY() + evaluateY(aCubic);
            points[i] = new Point(x, y);
            logger.debug("Punto[{}]: x = {}, y = {}", i, x, y);
        }
        return points;
    }

    public double[] calculateSlopes() {
        double[] slopes = new double[NUMPOINTS];
        double t;
        double aCubic;
        logger.info("calcolo pendenze cicloide");
        slopes[0] = Math.PI/2;
        logger.debug("pendenza[0]: {} ", (slopes[0] / Math.PI) * 180);
        for (int i = 1; i < NUMPOINTS; i++) {
            t = (double) i / (NUMPOINTS - 1);
            aCubic = alfa * Math.pow(t, 3);
            slopes[i] = Math.atan(Math.sin(aCubic) / (1 - Math.cos(aCubic)));
            logger.debug("pendenza[{}]: {} ", i, (slopes[i] / Math.PI) * 180);
        }
        return slopes;
    }

    public String curveName() {
        return "cicloide";
    }
}
