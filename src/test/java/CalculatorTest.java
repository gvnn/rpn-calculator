import com.gvnn.rpn.Calculator;
import com.gvnn.rpn.CalculatorException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testAritmeticOperators() throws Exception {
        Calculator calculator = new Calculator();

        calculator.eval("5 2");
        assertEquals(5, calculator.getValuesStack().get(0), 0);
        assertEquals(2, calculator.getStackItem(1), 0);

        // substraction
        calculator.eval("clear");
        calculator.eval("5 2 -");
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(3, calculator.getStackItem(0), 0);
        calculator.eval("3 -");
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(0, calculator.getStackItem(0), 0);

        // negative
        calculator.eval("clear");
        calculator.eval("1 2 3 4 5 *");
        assertEquals(4, calculator.getValuesStack().size());
        calculator.eval("clear 3 4 -");
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(-1, calculator.getStackItem(0), 0);


        // division
        calculator.eval("clear");
        calculator.eval("7 12 2 /");
        assertEquals(7, calculator.getStackItem(0), 0);
        assertEquals(6, calculator.getStackItem(1), 0);
        calculator.eval("*");
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(42, calculator.getStackItem(0), 0);
        calculator.eval("4 /");
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(10.5, calculator.getStackItem(0), 0);

        //multiplication
        calculator.eval("clear");
        calculator.eval("1 2 3 4 5");
        calculator.eval("* * * *");
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(120, calculator.getStackItem(0), 0);

    }

    @Test
    public void testSqrt() throws Exception {
        Calculator calculator = new Calculator();
        calculator.eval("2 sqrt");
        calculator.eval("clear 9 sqrt");
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(3, calculator.getStackItem(0), 0);
    }

    @Test
    public void testInsuficientParameters() {
        Calculator calculator = new Calculator();
        try {
            calculator.eval("1 2 3 * 5 + * * 6 5");
        } catch (CalculatorException e) {
            assertEquals("operator * (position: 8): insufficient parameters", e.getMessage());
        }
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(11, calculator.getStackItem(0), 0);
    }

    @Test
    public void testUndo() throws Exception {
        Calculator calculator = new Calculator();
        calculator.eval("5 4 3 2");
        assertEquals(4, calculator.getValuesStack().size());
        calculator.eval("undo undo *");
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(20, calculator.getStackItem(0), 0);
        calculator.eval("5 *");
        assertEquals(1, calculator.getValuesStack().size());
        assertEquals(100, calculator.getStackItem(0), 0);
        calculator.eval("undo");
        assertEquals(2, calculator.getValuesStack().size());
        assertEquals(20, calculator.getStackItem(0), 0);
        assertEquals(5, calculator.getStackItem(1), 0);
        calculator.eval("+ undo - undo / undo * undo sqrt undo pow undo");
        assertEquals(2, calculator.getValuesStack().size());
        assertEquals(20, calculator.getStackItem(0), 0.0000000001);
        assertEquals(5, calculator.getStackItem(1), 0.0000000001);
    }

    @Test(expected = CalculatorException.class)
    public void testOnlyOperators() throws Exception {
        Calculator calculator = new Calculator();
        calculator.eval("+ +");
    }

    @Test(expected = CalculatorException.class)
    public void testInvalidCharacters() throws Exception {
        Calculator calculator = new Calculator();
        calculator.eval("2 a +");
    }

    @Test(expected = CalculatorException.class)
    public void testNoSpaces() throws Exception {
        Calculator calculator = new Calculator();
        calculator.eval("22+");
    }

    @Test(expected = CalculatorException.class)
    public void testNoSpaces2() throws Exception {
        Calculator calculator = new Calculator();
        calculator.eval("2 2+ 3");
    }

    @Test(expected = CalculatorException.class)
    public void testDivideByZero() throws Exception {
        Calculator calculator = new Calculator();
        calculator.eval("1 0 /");
    }

    @Test(expected = CalculatorException.class)
    public void testNullInput() throws Exception {
        Calculator calculator = new Calculator();
        calculator.eval(null);
    }
}
