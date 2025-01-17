package ingdelsw.fallsimulator.controller;

import ingdelsw.fallsimulator.UI.Layout;
import ingdelsw.fallsimulator.UI.PlanetIcon;
import ingdelsw.fallsimulator.math.Point;
import ingdelsw.fallsimulator.simulation.SimulationManager;
import ingdelsw.fallsimulator.simulation.MassIcon;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventHandlerTest {

    private static EventHandler eventHandler;

    @BeforeClass
    public static void setUpClass() {
        // Inizializza il toolkit JavaFX
        Platform.startup(() -> {});

        eventHandler = EventHandler.getHandler();
    }

    @Test
    public void testHandleGravitySelection() {
        
        eventHandler.handleGravitySelection(PlanetIcon.EARTH);

        assertTrue("La gravità dovrebbe essere maggiore di zero", eventHandler.getG() > 0);

        assertEquals("Lo stato dovrebbe essere WAITING_FOR_START_POINT",
                EventHandler.UIStates.WAITING_FOR_START_POINT, eventHandler.getState());
    }

    @Test
    public void testHandleCubicSplineClick() {
    	
        eventHandler.handleCubicSplineClick();

        assertEquals("Lo stato dovrebbe essere INSERTING_INTERMEDIATE_POINTS",
                EventHandler.UIStates.INSERTING_INTERMEDIATE_POINTS, eventHandler.getState());
    }

    @Test
    public void testHandleCancelInputClick() {
        eventHandler.handleCancelInputClick();

        assertEquals("Lo stato dovrebbe essere IDLE", EventHandler.UIStates.IDLE, eventHandler.getState());

        assertTrue("Non dovrebbero esserci simulazioni attive", eventHandler.getSimulations().isEmpty());
    }
}
