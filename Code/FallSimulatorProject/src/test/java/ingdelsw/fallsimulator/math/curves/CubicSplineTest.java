package ingdelsw.fallsimulator.math.curves;

import ingdelsw.fallsimulator.math.Point;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class CubicSplineTest {

    private Point startPoint;
    private Point endPoint;
    private List<Point> controlPoints;
    private CubicSpline cubicSpline;

    @Before
    public void setUp() {
        startPoint = new Point(0, 0);
        endPoint = new Point(10, 10);
        controlPoints = new ArrayList<>();
        controlPoints.add(new Point(2, 3));
        controlPoints.add(new Point(5, 7));
        controlPoints.add(new Point(7, 8));
        cubicSpline = new CubicSpline(startPoint, endPoint, controlPoints);
    }

    @Test
    public void testConstructorInitialization() {
        assertNotNull("La funzione spline non dovrebbe essere null", cubicSpline);
        assertEquals("Il punto iniziale dovrebbe essere corretto", startPoint, cubicSpline.getStartPoint());
        assertEquals("Il punto finale dovrebbe essere corretto", endPoint, cubicSpline.getEndPoint());
    }

    @Test
    public void testCalculatePoints() {
        Point[] points = cubicSpline.calculatePoints();
        assertNotNull("I punti calcolati non dovrebbero essere null", points);
        assertEquals("Il numero di punti calcolati dovrebbe essere corretto", Curve.getNumPoints(), points.length);
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getX(), points[0].getX(), 0.001);
        assertEquals("Il primo punto dovrebbe essere uguale al punto iniziale", startPoint.getY(), points[0].getY(), 0.001);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
        assertEquals("L'ultimo punto dovrebbe essere uguale al punto finale", endPoint.getY(), points[points.length - 1].getY(), 0.01);
    }

    @Test
    public void testCalculateSlopes() {
        double[] slopes = cubicSpline.calculateSlopes();
        assertNotNull("Le pendenze non dovrebbero essere null", slopes);
        assertEquals("Il numero di pendenze dovrebbe essere corretto", Curve.getNumPoints(), slopes.length);
    }

    @Test
    public void testEvaluateY() {
        double xValue = 5;
        double yValue = cubicSpline.evaluateY(xValue);
        assertTrue("La valutazione di Y dovrebbe essere valida", yValue >= startPoint.getY() && yValue <= endPoint.getY());
    }

    @Test
    public void testCurveName() {
        assertEquals("Il nome della curva dovrebbe essere 'spline'", "spline", cubicSpline.curveName());
    }

    @Test
    public void testLinearSegmentFallback() {
        // Testa il comportamento fallback in assenza di più punti di controllo
        List<Point> minimalPoints = new ArrayList<>();
        CubicSpline linearSpline = new CubicSpline(startPoint, endPoint, minimalPoints);
        double[] slopes = linearSpline.calculateSlopes();

        assertNotNull("Le pendenze non dovrebbero essere null", slopes);
        assertEquals("Il numero di pendenze dovrebbe essere corretto", Curve.getNumPoints(), slopes.length);

        double expectedSlope = Math.atan((endPoint.getY() - startPoint.getY()) / (endPoint.getX() - startPoint.getX()));
        for (double slope : slopes) {
            assertEquals("La pendenza dovrebbe essere costante per un segmento lineare", expectedSlope, slope, 1e-6);
        }
    }
}
