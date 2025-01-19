package ingdelsw.fallsimulator.math;

public class NonConvergenceException extends Exception {

	//exception defined for non convergence of Raphson Newton method for calculating cycloid radius
	public NonConvergenceException(int numberIterations) {
		super("Il metodo non converge dopo " + numberIterations + " iterazioni.");
	}
}
