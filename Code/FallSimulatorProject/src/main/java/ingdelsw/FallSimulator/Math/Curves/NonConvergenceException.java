package ingdelsw.FallSimulator.Math.Curves;

public class NonConvergenceException extends Exception {

	public NonConvergenceException(int numberIterations) {
		super("Il metodo non converge dopo " + numberIterations + " iterazioni.");
	}
}
