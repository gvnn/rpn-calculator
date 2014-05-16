import com.gvnn.rpn.CalculatorException;
import com.gvnn.rpn.Operator;
import org.junit.Test;

import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;

public class OperatorTest {

    @Test
    public void testCalculate() throws CalculatorException {
        Random r = new Random();
        double firstOperand = r.nextDouble();
        double secondOperand = r.nextDouble();
        assertEquals(secondOperand + firstOperand, Operator.ADDITION.calculate(firstOperand, secondOperand), 0);
        assertEquals(secondOperand - firstOperand, Operator.SUBTRACTION.calculate(firstOperand, secondOperand), 0);
        assertEquals(secondOperand * firstOperand, Operator.MULTIPLICATION.calculate(firstOperand, secondOperand), 0);
        assertEquals(secondOperand / firstOperand, Operator.DIVISION.calculate(firstOperand, secondOperand), 0);
        assertEquals(pow(firstOperand, 2.0), Operator.POWER.calculate(firstOperand, null), 0);
        assertEquals(sqrt(secondOperand), Operator.SQUAREROOT.calculate(secondOperand, null), 0);
    }

    @Test(expected = CalculatorException.class)
    public void testDivideByZero() throws CalculatorException {
        Operator.DIVISION.calculate(0.0, 0.0);
    }

    @Test(expected = CalculatorException.class)
    public void testInvalidOperations() throws CalculatorException {
        Operator.UNDO.calculate(0.0, 0.0);
        Operator.CLEAR.calculate(0.0, 0.0);
    }
}
