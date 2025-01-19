package ingdelsw.fallsimulator.simulation;

import ingdelsw.fallsimulator.math.Point;
import ingdelsw.fallsimulator.math.curves.Curve;
import ingdelsw.fallsimulator.math.curves.Parabola;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimulationManagerTest {

    private static Image testImage;

    @BeforeClass
    public static void setUpClass() {
        Platform.startup(() -> {});
        testImage = new Image(SimulationManagerTest.class.getResource("/images/Newton.png").toExternalForm());
    }

    @Test
    public void testInitialization() {
        Point startPoint = new Point(0, 0);
        Point endPoint = new Point(100, 50);
        Curve curve = new Parabola(startPoint, endPoint); 

        SimulationManager manager = new SimulationManager(curve);

        assertEquals("La curva dovrebbe essere inizializzata correttamente", curve, manager.getCurve());
        assertNotNull("I punti della curva non dovrebbero essere null", manager.getPoints());
        assertNull("La massa inizialmente dovrebbe essere null", manager.getMass());
    }

    @Test
    public void testSetMass() {
        Point startPoint = new Point(0, 0);
        Point endPoint = new Point(100, 50);
        Curve curve = new Parabola(startPoint, endPoint);

        SimulationManager manager = new SimulationManager(curve);

        ImageView icon = new ImageView(testImage);
        Mass mass = new Mass(startPoint, MassIcon.NEWTON, icon);
        manager.setMass(mass);

        assertEquals("La massa dovrebbe essere impostata correttamente", mass, manager.getMass());
    }

    @Test
    public void testCalculateTimeParametrization() {
        Point startPoint = new Point(0, 0);
        Point endPoint = new Point(100, 50);
        Parabola curve = new Parabola(startPoint, endPoint);

        SimulationManager manager = new SimulationManager(curve);

        double[] slopes = curve.calculateSlopes();
        manager.setSlopes(slopes);

        double g = 9.81; 
        double[] times = manager.calculateTimeParametrization(g);

        assertNotNull("La parametrizzazione temporale non dovrebbe essere null", times);
        assertEquals("La lunghezza del vettore dei tempi dovrebbe corrispondere al numero di punti", manager.getPoints().length, times.length);
        assertEquals("Il primo tempo dovrebbe essere zero", 0, times[0], 1e-6);
        assertTrue("I tempi successivi dovrebbero essere crescenti", times[1] > times[0]);
    }

    @Test
    public void testStartAnimation() {
        Point startPoint = new Point(0, 0);
        Point endPoint = new Point(100, 50);
        Parabola curve = new Parabola(startPoint, endPoint);

        SimulationManager manager = new SimulationManager(curve);

        double[] slopes = curve.calculateSlopes();
        manager.setSlopes(slopes);

        ImageView icon = new ImageView(testImage);
        Mass mass = new Mass(startPoint, MassIcon.NEWTON, icon);
        manager.setMass(mass);

        double g = 9.81;
        manager.calculateTimeParametrization(g);

        manager.startAnimation();

        assertEquals("La massa dovrebbe essere inizialmente posizionata sul punto di partenza",
                startPoint.getX(), mass.getIcon().getLayoutX(), 1e-6);
        assertEquals("La massa dovrebbe essere inizialmente posizionata sul punto di partenza",
                startPoint.getY(), mass.getIcon().getLayoutY(), 1e-6);
    }
}
