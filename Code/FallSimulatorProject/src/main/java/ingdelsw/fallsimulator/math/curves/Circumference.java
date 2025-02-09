// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ingdelsw.fallsimulator.math.curves;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ingdelsw.fallsimulator.math.Point;

public class Circumference extends Curve {
	
	private static final Logger logger = LogManager.getLogger(Circumference.class);
	
	private double r; //circumference radius
	private int convexity; //circumference convexity

    
    //initial circumference constructor
    public Circumference(Point startPoint, Point endPoint, int convexity) {
    	super(startPoint, endPoint);
        this.convexity=convexity;
        if(convexity == -1) {//convexity down
        	//radius for orizontal tangent in start point
        	this.r = (Math.pow(intervalX, 2)+Math.pow(intervalY, 2))/(2 * intervalY) + 1;
        }
        //convexity up
        //radius for vertical tangent in start point
        else this.r = (Math.pow(intervalX, 2)+Math.pow(intervalY, 2))/(2 * intervalX); 
    }
    
    //selected radius circumference constructor
    public Circumference(Point startPoint, Point endPoint, int convexity, double r) {
    	super(startPoint, endPoint);
    	this.r = r;
        this.convexity = convexity;
    }
    
    
    public double getR()
    {
    	return r;
    }
    
    //function for a circumference passing from the origin
    public double evaluateFunction(double variable) {
    	return Math.sqrt(2*variable*r - Math.pow(variable, 2));
    }
    
    @Override
    public Point[] calculatePoints() {
    	Point[] points = new Point[NUMPOINTS];//array for the points 
    	
    	//absolute coordinates of the center
    	double xCenter = xCenter(startPoint) + startPoint.getX();
    	double yCenter = yCenter(startPoint) + startPoint.getY();
    	double x;
    	double y;
    	
    	if(convexity == 1)
    	{
    		double t;
    		double  xPow;
    		logger.info("calcolo punti circonferenza con concavità verso l'alto");
        	for (int i=0; i < NUMPOINTS; i++) {
        		t = (double) i / (NUMPOINTS - 1); // index normalized respect to numpoints
        		//more points density at the beginning with a cubic relation
        		xPow = intervalX * Math.pow(t, 3); 
        		x = startPoint.getX() + xPow;
                y = yCenter + evaluateFunction(x + r - xCenter);
                points[i] = new Point(x,y);
                logger.debug("Punto[{}]: x = {}, y = {}", i, x, y);
            }
    	}
    	
    	else if(convexity == -1)
    	{
    		double t;
    		double  yPow;
    		logger.info("calcolo punti circonferenza con concavità verso il basso");
        	for (int i=0; i < NUMPOINTS; i++) {
        		t = (double) i / (NUMPOINTS - 1);
                yPow = intervalY * Math.pow(t, 3); 
        		y = startPoint.getY() + yPow;
                x = xCenter + (intervalX/Math.abs(intervalX))*evaluateFunction(y + r - yCenter);
                points[i] = new Point(x,y);
                logger.debug("Punto[{}]: x = {}, y = {}", i, x, y);
            }
    	}
    	return points;
    }
    
    //second order equation coefficients
    private double aCoefficient()
    {
    	return Math.pow(intervalX, 2) + Math.pow(intervalY, 2);
    }
    
    private double bCoefficient()
    {
    	return intervalX*(Math.pow(intervalX, 2) + Math.pow(intervalY, 2));
    }
    
    private double cCoefficient()
    {
    	return (Math.pow(Math.pow(intervalX, 2) + Math.pow(intervalY, 2), 2)/4) - Math.pow(intervalY, 2)*Math.pow(r, 2);
    }
    
    private double delta()
    {
    	return Math.pow(bCoefficient(), 2) - 4*aCoefficient()*cCoefficient();
    }
    
    //calculates center relative X coordinate
    private double xCenter(Point startPoint)
    {	
    	double xCenter;
    	xCenter = (bCoefficient() + convexity*(intervalX/Math.abs(intervalX))*Math.sqrt(delta()))/(2*aCoefficient());
    	logger.debug(" yCenter : {}", xCenter+startPoint.getX());
    	return xCenter;
    }
    
    //calculates center relative Y coordinate
    private double yCenter(Point startPoint)
    {
    	double yCenter = (Math.pow(intervalX, 2) + Math.pow(intervalY, 2) - 2*xCenter(startPoint)*intervalX)/(2*intervalY);
    	logger.debug(" yCenter : {}", yCenter+startPoint.getY());
    	return yCenter;
    } 
    
    @Override
    public double[] calculateSlopes()
    {
    	double[] slopes = new double[NUMPOINTS];
    	
    	if(convexity == 1)
    	{
    		double t;
    		double xCubic;
    		double x;
    		double x0 = r - xCenter(startPoint);
    		logger.info("calcolo pendenze circonferenza con concavità verso l'alto");
        	for (int i=0; i < NUMPOINTS; i++) {
        		t = (double) i / (NUMPOINTS - 1); 
                xCubic = intervalX * Math.pow(t, 3);    
                x = x0 +xCubic;
                //circumference derivative
                slopes[i] = Math.atan((r-x)/Math.sqrt(2*r*x - Math.pow(x, 2))); 
                logger.debug("pendenza[{}]: {} ", i, (slopes[i] / Math.PI) * 180);
            }
    	}
    	
    	else if(convexity == -1)
    	{
    		double t;
    		double yPow;
    		double y;
    		double y0 = r - yCenter(startPoint);
    		logger.info("calcolo pendenze circonferenza con concavità verso il basso");
        	for (int i=0; i < NUMPOINTS; i++) {
        		t = (double) i / (NUMPOINTS - 1);
                yPow = intervalY * Math.pow(t, 3);
                y = y0 + yPow;
                //circumference derivative
                slopes[i] = Math.PI/2 - Math.atan((r-y)/Math.sqrt(2*r*y - Math.pow(y, 2))); 
                logger.debug("pendenza[{}]: {} ", i, (slopes[i] / Math.PI) * 180);
            }
    	}
    	return slopes;
    }
    
    public String curveName()
	{
		return "circonferenza";
	}
}
