package ingdelsw.fallsimulator.controller;

import ingdelsw.fallsimulator.UI.PlanetIcon;
import javafx.application.Platform;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventHandlerTest {

    private static EventHandler eventHandler;

    @BeforeClass
    public static void setUpClass() {
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
