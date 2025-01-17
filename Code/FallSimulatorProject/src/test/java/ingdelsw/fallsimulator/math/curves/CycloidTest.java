package ingdelsw.fallsimulator.math.curves;

import ingdelsw.fallsimulator.math.NonConvergenceException;
import ingdelsw.fallsimulator.math.Point;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CycloidTest {

    private Point startPoint;
    private Point endPoint;
    private Cycloid cycloid;

    @Before
    public void setUp() throws NonConvergenceException {
        // Inizializza i punti di inizio e fine per i test
        startPoint = new Point(0, 0);
        endPoint = new Point(10, 5);
        cycloid = new Cycloid(startPoint, endPoint);
    }

    @Test
    public void testConstructorInitialization() {
        // Verifica che i punti iniziale e finale siano correttamente impostati
        assertEquals("Il punto iniziale deve essere corretto", startPoint, cycloid.getStartPoint());
        assertEquals("Il punto finale deve essere corretto", endPoint, cycloid.getEndPoint());
    }

    @Test
    public void testCalculatePoints() {
        // Verifica che i punti calcolati siano corretti
        Point[] points = cycloid.calculatePoints();

        assertNotNull("L'array di punti non dovrebbe essere null", points);
        assertEquals("Il numero di punti dovrebbe essere corretto", Curve.getNumPoints(), points.length);

        // Controlla che il primo e l'ultimo punto corrispondano ai punti iniziale e finale
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getX(), points[0].getX(), 0.001);
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getY(), points[0].getY(), 0.001);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
    }

    @Test
    public void testCalculateSlopes() {
        // Verifica che le pendenze siano calcolate correttamente
        double[] slopes = cycloid.calculateSlopes();

        assertNotNull("L'array delle pendenze non dovrebbe essere null", slopes);
        assertEquals("Il numero delle pendenze dovrebbe essere corretto", Curve.getNumPoints(), slopes.length);

        // Controlla alcune proprietà delle pendenze
        assertEquals("La prima pendenza dovrebbe essere infinita", Math.PI / 2, slopes[0], 0.01);
        assertTrue("Le pendenze dovrebbero essere finite", Double.isFinite(slopes[slopes.length - 1]));
    }

    @Test
    public void testCurveName() {
        // Verifica che il nome della curva sia corretto
        assertEquals("Il nome della curva dovrebbe essere 'cicloide'", "cicloide", cycloid.curveName());
    }

    @Test
    public void testEvaluateFunctions() {
        // Verifica che le funzioni evaluateX e evaluateY funzionino correttamente
        double a = Math.PI / 4; // Un valore arbitrario di alfa

        double x = cycloid.evaluateX(a);
        double y = cycloid.evaluateY(a);

        assertTrue("Il valore di x dovrebbe essere positivo", x > 0);
        assertTrue("Il valore di y dovrebbe essere positivo", y > 0);
    }

    @Test(expected = NonConvergenceException.class)
    public void testNonConvergence() throws NonConvergenceException {
        // Prova a creare una cicloide con punti che causano non convergenza
        new Cycloid(new Point(0, 0), new Point(1000, 0));
    }
}

