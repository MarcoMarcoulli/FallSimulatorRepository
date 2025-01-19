package ingdelsw.fallsimulator.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import ingdelsw.fallsimulator.math.Point;

public class InputControllerTest {

    private InputController controller;

    @Before
    public void setup() {
        InputController.resetInstance(); 
        controller = InputController.getController(); 
    }

    @Test
    public void testSetStartPoint() {
        Point start = new Point(10, 20);
        controller.setStartpoint(start);

        assertEquals("Start point X should match", 10, controller.getStartPoint().getX(), 0.001);
        assertEquals("Start point Y should match", 20, controller.getStartPoint().getY(), 0.001);
    }

    @Test
    public void testSetEndPoint_Valid() {
        Point start = new Point(10, 20);
        Point end = new Point(15, 25);

        controller.setStartpoint(start);
        controller.setEndpoint(end);

        assertEquals("End point X should match", 15, controller.getEndPoint().getX(), 0.001);
        assertEquals("End point Y should match", 25, controller.getEndPoint().getY(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEndPoint_InvalidHigherThanStart() {
        Point start = new Point(10, 20);
        Point invalidEnd = new Point(15, 10);

        controller.setStartpoint(start);
        controller.setEndpoint(invalidEnd);
    }

    @Test
    public void testSetEndPoint_InvalidVertical() {
        Point start = new Point(10, 50);
        Point invalidEnd = new Point(10, 100); 

        controller.setStartpoint(start);
        controller.setEndpoint(invalidEnd); 

        assertNotEquals("The X coordinate of endPoint should not match startPoint", 
                        start.getX(), controller.getEndPoint().getX());
    }

    @Test
    public void testAddIntermediatePoint_Valid() {
        Point start = new Point(10, 20);
        Point end = new Point(30, 50);
        Point intermediate = new Point(20, 35); 

        controller.setStartpoint(start);
        controller.setEndpoint(end); 
        controller.addIntermediatePoint(intermediate);

        List<Point> intermediatePoints = controller.getIntermediatePoint();
        assertEquals("There should be one intermediate point", 1, intermediatePoints.size());
        assertEquals("Intermediate point X should match", 20, intermediatePoints.get(0).getX(), 0.001);
        assertEquals("Intermediate point Y should match", 35, intermediatePoints.get(0).getY(), 0.001);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddIntermediatePoint_OutOfBounds() {
        Point start = new Point(10, 20);
        Point end = new Point(30, 10);
        Point outOfBounds = new Point(35, 15);

        controller.setStartpoint(start);
        controller.setEndpoint(end);
        controller.addIntermediatePoint(outOfBounds);
    }

    @Test
    public void testClearIntermediatePoints() {
        Point start = new Point(10, 50);
        Point end = new Point(20, 100); 
        Point intermediate = new Point(15, 75);

        controller.setStartpoint(start);
        controller.setEndpoint(end); 
        controller.addIntermediatePoint(intermediate); 

        controller.clearIntermediatePoints();

        assertTrue("Intermediate points list should be empty", controller.getIntermediatePoint().isEmpty());
    }

    @Test
    public void testClearInput() {
        Point start = new Point(10, 50);
        Point end = new Point(20, 100); 
        Point intermediate = new Point(15, 75);

        controller.setStartpoint(start);
        controller.setEndpoint(end); 
        controller.addIntermediatePoint(intermediate); 

        controller.clearInput();

        assertNull("Start point should be null", controller.getStartPoint());
        assertNull("End point should be null", controller.getEndPoint());
        assertTrue("Intermediate points list should be empty", controller.getIntermediatePoint().isEmpty());
    }
}

