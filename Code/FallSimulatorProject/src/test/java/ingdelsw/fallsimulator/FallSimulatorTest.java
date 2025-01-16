package ingdelsw.fallsimulator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import ingdelsw.fallsimulator.UI.Layout;
import ingdelsw.fallsimulator.controller.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FallSimulatorTest extends ApplicationTest {

    private Stage stage;
    private Layout layout;
    private EventHandler eventHandler;

    @Override
    public void start(Stage stage) {
        this.stage = stage; // Salva il riferimento nello stage della classe
        Layout.resetInstance(); // Reset del singleton Layout
        layout = Layout.getLayout(null);
        eventHandler = EventHandler.getHandler();

        layout.addWindowResizingObserver(eventHandler); // Associa l'osservatore

        Scene scene = new Scene(layout.getBorderPane(), 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Fall Simulator");
        stage.show();
    }
    
    @Before
    public void setup() {
        Layout.resetInstance();
    }

    @Test
    public void testSingletonInstances() {
        assertNotNull("Layout singleton instance should not be null", layout);
        assertNotNull("EventHandler singleton instance should not be null", eventHandler);
    }

    @Test
    public void testStageInitialization() {
        assertNotNull("The primary stage should not be null", stage);
        assertEquals("Fall Simulator", stage.getTitle()); 
    }

    @Test
    public void testSceneInitialization() {
        Scene scene = stage.getScene();
        assertNotNull("The scene should not be null", scene);
        assertNotNull("The root node of the scene should not be null", scene.getRoot());
        assertEquals("The root node should be the BorderPane from Layout", layout.getBorderPane(), scene.getRoot());
    }

    @Test
    public void testObserverPatternAssociation() {
        assertEquals("The observer should be the EventHandler instance", eventHandler, layout.getWindowResizingObserver());
    }
}