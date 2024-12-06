package ingdelsw.fallsimulator.math.curves;

public class NonConvergenceException extends Exception {

	public NonConvergenceException(int numberIterations) {
		super("Il metodo non converge dopo " + numberIterations + " iterazioni.");
	}
}