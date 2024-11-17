// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ingdelsw.FallSimulator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ingdelsw.FallSimulator.Math.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class InputManager {
	
	private Point startPoint, endPoint;
	
	protected static final Logger LOGGER = LogManager.getLogger();
	
	public Point getStartPoint() {
		return startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
		LOGGER.debug("startPoint : " + this.startPoint);
	}

	//controllo che il punto di arrivo sia più basso del punto di partenza
	public void setEndPoint(Point endPoint) {
		if(endPoint.getY()<=startPoint.getY()) 
    		throw new IllegalArgumentException("Il punto di arrivo deve essere più in basso di quello di partenza");
    	else if(endPoint.getX() == startPoint.getX()) //faccio in modo che i punti di partenza e arrivo non siano esattamente sulla stessa verticale per evitare curve degeneri
    		this.endPoint = new Point(endPoint.getX()+1, endPoint.getY());
    	else {
    		this.endPoint=endPoint;
    	}
		LOGGER.debug("startPoint : " + this.startPoint);
	}
	
	// Mostra una finestra di errore quando viene catturata l'eccezione
	public void handleException(Exception e) {
        Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
        alert.showAndWait();
    }
	
	// Metodo per cancellare i punti inseriti
    public void clearInput() {
        startPoint=null;
        endPoint=null;
    }
}
