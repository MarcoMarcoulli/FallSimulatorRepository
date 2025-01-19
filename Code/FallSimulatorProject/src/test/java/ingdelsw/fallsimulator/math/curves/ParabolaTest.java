package ingdelsw.fallsimulator.math.curves;

import ingdelsw.fallsimulator.math.Point;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParabolaTest {

    private Point startPoint;
    private Point endPoint;
    private Parabola parabola;

    @Before
    public void setUp() {
        startPoint = new Point(0, 0);
        endPoint = new Point(100, 50);
        parabola = new Parabola(startPoint, endPoint);
    }

    @Test
    public void testConstructorInitialization() {
        assertNotNull("La parabola non dovrebbe essere null", parabola);
        assertEquals("Il punto iniziale dovrebbe essere corretto", startPoint, parabola.getStartPoint());
        assertEquals("Il coefficiente a dovrebbe essere calcolato correttamente", 
            (endPoint.getX() - startPoint.getX()) / Math.pow(endPoint.getY() - startPoint.getY(), 2), 
            parabola.getA(), 0.001);
    }

    @Test
    public void testEvaluateX() {
        double y = 25; 
        double expectedX = parabola.getA() * Math.pow(y, 2);
        assertEquals("Il valore di X dovrebbe essere calcolato correttamente", expectedX, parabola.evaluateX(y), 0.001);
    }

    @Test
    public void testCalculatePoints() {
        Point[] points = parabola.calculatePoints();
        assertNotNull("I punti della parabola non dovrebbero essere null", points);
        assertEquals("Il numero di punti dovrebbe essere corretto", Parabola.getNumPoints(), points.length);
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getX(), points[0].getX(), 0.001);
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getY(), points[0].getY(), 0.001);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
    }

    @Test
    public void testCalculateSlopes() {
        double[] slopes = parabola.calculateSlopes();
        assertNotNull("Le pendenze non dovrebbero essere null", slopes);
        assertEquals("Il numero di pendenze dovrebbe essere corretto", Parabola.getNumPoints(), slopes.length);
        assertTrue("La pendenza iniziale dovrebbe essere valida", slopes[0] > 0);
        assertTrue("La pendenza finale dovrebbe essere valida", slopes[slopes.length - 1] > 0);
    }

    @Test
    public void testCurveName() {
        assertEquals("Il nome della curva dovrebbe essere 'parabola'", "parabola", parabola.curveName());
    }
}
