package ingdelsw.fallsimulator.math.curves;

import ingdelsw.fallsimulator.math.Point;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CircumferenceTest {

    private Point startPoint;
    private Point endPoint;
    private Circumference circumferenceConcaveUp;
    private Circumference circumferenceConcaveDown;

    @Before
    public void setUp() {
        // Inizializza punti di test
        startPoint = new Point(0, 0);
        endPoint = new Point(10, 10);

        // Crea istanze della classe Circumference
        circumferenceConcaveUp = new Circumference(startPoint, endPoint, 1);
        circumferenceConcaveDown = new Circumference(startPoint, endPoint, -1);
    }

    @Test
    public void testConstructorInitialization() {
        // Verifica che i valori iniziali siano corretti
        assertNotNull("Il costruttore non dovrebbe restituire null", circumferenceConcaveUp);
        assertEquals("Il punto iniziale dovrebbe corrispondere", startPoint, circumferenceConcaveUp.getStartPoint());
        assertEquals("Il punto finale dovrebbe corrispondere", endPoint, circumferenceConcaveUp.getEndPoint());
    }

    @Test
    public void testCalculatePointsConcaveUp() {
        Point[] points = circumferenceConcaveUp.calculatePoints();
        
        assertNotNull("Il risultato non dovrebbe essere null", points);
        assertEquals("Il numero di punti calcolati dovrebbe essere corretto", Curve.getNumPoints(), points.length);

        // Verifica che il primo e l'ultimo punto corrispondano ai punti di partenza e arrivo
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getX(), points[0].getX(), 0.001);
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getY(), points[0].getY(), 0.001);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
    }

    @Test
    public void testCalculatePointsConcaveDown() {
        Point[] points = circumferenceConcaveDown.calculatePoints();

        assertNotNull("Il risultato non dovrebbe essere null", points);
        assertEquals("Il numero di punti calcolati dovrebbe essere corretto", Curve.getNumPoints(), points.length);

        // Verifica che il primo e l'ultimo punto corrispondano ai punti di partenza e arrivo
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getX(), points[0].getX(), 0.001);
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getY(), points[0].getY(), 0.001);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
    }

    @Test
    public void testCalculateSlopes() {
        double[] slopesUp = circumferenceConcaveUp.calculateSlopes();
        double[] slopesDown = circumferenceConcaveDown.calculateSlopes();

        assertNotNull("Le pendenze non dovrebbero essere null", slopesUp);
        assertNotNull("Le pendenze non dovrebbero essere null", slopesDown);
        assertEquals("Il numero di pendenze dovrebbe corrispondere al numero di punti", Curve.getNumPoints(), slopesUp.length);
        assertEquals("Il numero di pendenze dovrebbe corrispondere al numero di punti", Curve.getNumPoints(), slopesDown.length);
    }

    @Test
    public void testEvaluateFunction() {
        double result = circumferenceConcaveUp.evaluateFunction(5.0);
        assertTrue("Il risultato dovrebbe essere positivo", result > 0);
    }

    @Test
    public void testCurveName() {
        assertEquals("Il nome della curva dovrebbe essere 'circonferenza'", "circonferenza", circumferenceConcaveUp.curveName());
    }

    @Test
    public void testRadiusInitialization() {
        double rUp = circumferenceConcaveUp.getR();
        double rDown = circumferenceConcaveDown.getR();

        assertTrue("Il raggio dovrebbe essere positivo", rUp > 0);
        assertTrue("Il raggio dovrebbe essere positivo", rDown > 0);
    }
}
