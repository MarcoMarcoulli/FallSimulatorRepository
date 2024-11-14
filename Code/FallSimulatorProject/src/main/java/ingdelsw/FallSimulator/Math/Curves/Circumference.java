// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ingdelsw.FallSimulator.Math.Curves;

import ingdelsw.FallSimulator.Math.Point;

class Circumference extends Curve {
	
	private double r;
	private int convexity;

	public Circumference(Point startPoint, Point endPoint, int convexity) {
    	super(startPoint, endPoint);
        this.convexity=convexity;
        if(convexity == -1)
        	this.r = (Math.pow(intervalX, 2)+Math.pow(intervalY, 2))/(2 * intervalY) + 1;
        else this.r = (Math.pow(intervalX, 2)+Math.pow(intervalY, 2))/(2 * intervalX);
    }
	
	public Circumference(Point startPoint, Point endPoint, int convexity, double r) {
    	super(startPoint, endPoint);
    	this.r = r;
        this.convexity = convexity;
    }

	public double getR() {
		return r;
	}

	public double evaluateFunction(double var) {
    	return Math.sqrt(2*var*r - Math.pow(var, 2));
    }

	
	public double aCoefficient() {
		return Math.pow(intervalX, 2) + Math.pow(intervalY, 2);
	}

	
	public double bCoefficient() {
		return intervalX*((Math.pow(intervalX, 2) + Math.pow(intervalY, 2)));
	}

	
	public double cCoefficient() {
		return (Math.pow(Math.pow(intervalX, 2) + Math.pow(intervalY, 2), 2)/4) - Math.pow(intervalY, 2)*Math.pow(r, 2);
	}

	
	public float delta() {
	}

	
	public float xCenter(Point startPoint) {
	}

	
	public float yCenter(Point startPoint) {
	}
}
