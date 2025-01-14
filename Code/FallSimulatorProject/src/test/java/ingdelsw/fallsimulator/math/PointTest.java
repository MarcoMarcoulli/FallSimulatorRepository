package ingdelsw.fallsimulator.math;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PointTest {

    private Point point;

    @Before
    public void setup() {
        point = new Point(3.5, 4.5);
    }

    @Test
    public void testConstructor() {
        Point p = new Point(1.0, 2.0);
        assertEquals("X value should be 1.0", 1.0, p.getX(), 0.001);
        assertEquals("Y value should be 2.0", 2.0, p.getY(), 0.001);
    }

    @Test
    public void testGetX() {
        assertEquals("X value should match", 3.5, point.getX(), 0.001);
    }

    @Test
    public void testGetY() {
        assertEquals("Y value should match", 4.5, point.getY(), 0.001);
    }

    @Test
    public void testSetX() {
        point.setX(10.0);
        assertEquals("X value should be updated to 10.0", 10.0, point.getX(), 0.001);
    }

    @Test
    public void testSetY() {
        point.setY(20.0);
        assertEquals("Y value should be updated to 20.0", 20.0, point.getY(), 0.001);
    }

    @Test
    public void testSetXNegativeValue() {
        point.setX(-5.0);
        assertEquals("X value should be updated to -5.0", -5.0, point.getX(), 0.001);
    }

    @Test
    public void testSetYNegativeValue() {
        point.setY(-7.5);
        assertEquals("Y value should be updated to -7.5", -7.5, point.getY(), 0.001);
    }
}
