package ingdelsw.fallsimulator.UI;

import static org.junit.Assert.*;

import org.junit.After;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LayoutTest {

    private static Layout layout;
    private static boolean toolkitInitialized = false;

    @BeforeClass
    public static void setUpClass() {
        if (!toolkitInitialized) {
            // Inizializza JavaFX Toolkit solo se non è già inizializzato
            Platform.startup(() -> {});
            toolkitInitialized = true;
        }
        layout = Layout.getLayout();
    }

    @AfterClass
    public static void tearDownClass() {
        Layout.resetInstance();
    }
    
    @After
    public void tearDown() {
        Layout.resetInstance();
    }

    @Test
    public void testSingletonInstance() {
        Layout layout1 = Layout.getLayout();
        Layout layout2 = Layout.getLayout();
        assertSame("Layout deve essere una singola istanza", layout1, layout2);
    }

    @Test
    public void testRootInitialization() {
        assertNotNull("Il root non dovrebbe essere null", layout.getBorderPane());
    }

    @Test
    public void testControlPanelInitialization() {
        VBox controlPanel = layout.getControlPanel();
        assertNotNull("Il pannello di controllo non dovrebbe essere null", controlPanel);
        assertTrue("Il pannello di controllo dovrebbe contenere almeno un nodo", controlPanel.getChildren().size() > 0);
    }

    @Test
    public void testCanvasInitialization() {
        Canvas pointsCanvas = layout.getPointsCanvas();
        Canvas curveCanvas = layout.getCurveCanvas();
        assertNotNull("Il Canvas per i punti non dovrebbe essere null", pointsCanvas);
        assertNotNull("Il Canvas per le curve non dovrebbe essere null", curveCanvas);
    }

    @Test
    public void testAnimationPaneInitialization() {
        Pane animationPane = layout.getAnimationPane();
        assertNotNull("Il Pane per l'animazione non dovrebbe essere null", animationPane);
        assertTrue("Il Pane per l'animazione dovrebbe essere trasparente ai click", animationPane.isMouseTransparent());
    }

    @Test
    public void testStackPaneInitialization() {
        StackPane stackPane = layout.getStackPane();
        assertNotNull("Lo StackPane non dovrebbe essere null", stackPane);
        assertEquals("Lo StackPane dovrebbe contenere 3 livelli", 3, stackPane.getChildren().size());
    }

    @Test
    public void testButtonInitialization() {
        Button btnCycloid = layout.getBtnCycloid();
        Button btnParabola = layout.getBtnParabola();
        Button btnCircumference = layout.getBtnCircumference();
        Button btnCubicSpline = layout.getBtnCubicSpline();

        assertNotNull("Il bottone Cicloide non dovrebbe essere null", btnCycloid);
        assertNotNull("Il bottone Parabola non dovrebbe essere null", btnParabola);
        assertNotNull("Il bottone Circonferenza non dovrebbe essere null", btnCircumference);
        assertNotNull("Il bottone Spline Cubica non dovrebbe essere null", btnCubicSpline);
    }

    @Test
    public void testLabelsInitialization() {
        Label startPointMessage = layout.getStartPointMessage();
        Label endPointMessage = layout.getEndPointMessage();
        Label chooseCurveMessage = layout.getChooseCurveMessage();

        assertNotNull("Il messaggio per il punto di partenza non dovrebbe essere null", startPointMessage);
        assertNotNull("Il messaggio per il punto di arrivo non dovrebbe essere null", endPointMessage);
        assertNotNull("Il messaggio per la selezione della curva non dovrebbe essere null", chooseCurveMessage);
    }

    @Test
    public void testClearMethod() {
        layout.clear();
        VBox controlPanel = layout.getControlPanel();
        assertNotNull("Il pannello di controllo non dovrebbe essere null dopo clear", controlPanel);
        assertEquals("Il pannello di controllo dovrebbe contenere 2 elementi iniziali", 2, controlPanel.getChildren().size());
    }
}
