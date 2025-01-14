package ingdelsw.fallsimulator.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class NonConvergenceExceptionTest {

    @Test
    public void testExceptionMessage() {
        int numberIterations = 100;
        String expectedMessage = "Il metodo non converge dopo " + numberIterations + " iterazioni.";

        NonConvergenceException exception = new NonConvergenceException(numberIterations);

        assertEquals("The exception message should match the expected message", expectedMessage, exception.getMessage());
    }

    @Test(expected = NonConvergenceException.class)
    public void testThrowException() throws NonConvergenceException {
        throwNonConvergenceException();
    }

    private void throwNonConvergenceException() throws NonConvergenceException {
        int numberIterations = 100;
        throw new NonConvergenceException(numberIterations);
    }
}
