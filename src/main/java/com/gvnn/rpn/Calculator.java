package com.gvnn.rpn;

import java.util.Stack;

public class Calculator {

    private Stack<Double> valuesStack = new Stack<Double>();
    private Stack<Instruction> instructionsStack = new Stack<Instruction>();
    private int currentTokenIndex = 0;

    private Double tryParseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    /**
     * Processes a RPN string token
     *
     * @param token           RPN token
     * @param isUndoOperation indicates if the operation is an undo operation.
     * @throws CalculatorException
     */
    private void processToken(String token, boolean isUndoOperation) throws CalculatorException {
        Double value = tryParseDouble(token);
        if (value == null) {
            processOperator(token, isUndoOperation);
        } else {
            // it's a digit
            valuesStack.push(Double.parseDouble(token));
            if (!isUndoOperation) {
                instructionsStack.push(null);
            }
        }
    }

    /**
     * Executes an operation on the stack
     *
     * @param operatorString  RPN valid operator
     * @param isUndoOperation indicates if the operation is an undo operation.
     * @throws CalculatorException
     */
    private void processOperator(String operatorString, boolean isUndoOperation) throws CalculatorException {

        // check if there is an empty stack
        if (valuesStack.isEmpty()) {
            throw new CalculatorException("empty stack");
        }

        // searching for the operator
        Operator operator = Operator.getEnum(operatorString);
        if (operator == null) {
            throw new CalculatorException("invalid operator");
        }

        // clear value stack and instructions stack
        if (operator == Operator.CLEAR) {
            clearStacks();
            return;
        }

        // undo evaluates the last instruction in stack
        if (operator == Operator.UNDO) {
            undoLastInstruction();
            return;
        }

        // Checking that there are enough operand for the operation
        if (operator.getOperandsNumber() > valuesStack.size()) {
            throwInvalidOperand(operatorString);
        }

        // getting operands
        Double firstOperand = valuesStack.pop();
        Double secondOperand = (operator.getOperandsNumber() > 1) ? valuesStack.pop() : null;
        // calculate
        Double result = operator.calculate(firstOperand, secondOperand);

        if (result != null) {
            valuesStack.push(result);
            if (!isUndoOperation) {
                instructionsStack.push(new Instruction(Operator.getEnum(operatorString), firstOperand));
            }
        }

    }

    private void undoLastInstruction() throws CalculatorException {
        if (instructionsStack.isEmpty()) {
            throw new CalculatorException("no operations to undo");
        }

        Instruction lastInstruction = instructionsStack.pop();
        if (lastInstruction == null) {
            valuesStack.pop();
        } else {
            eval(lastInstruction.getReverseInstruction(), true);
        }
    }

    private void clearStacks() {
        valuesStack.clear();
        instructionsStack.clear();
    }

    private void throwInvalidOperand(String operator) throws CalculatorException {
        throw new CalculatorException(
                String.format("operator %s (position: %d): insufficient parameters", operator, currentTokenIndex));
    }

    /**
     * Returns the values valuesStack
     */
    public Stack<Double> getValuesStack() {
        return valuesStack;
    }

    /**
     * Helper method to return a specific item in the valuesStack
     *
     * @param index index of the element to return
     */
    public Double getStackItem(int index) {
        return valuesStack.get(index);
    }

    /**
     * Evals a RPN expression and pushes the result into the valuesStack
     *
     * @param input valid RPN expression
     * @throws CalculatorException
     */
    public void eval(String input) throws CalculatorException {
        eval(input, false);
    }

    /**
     * Evals a RPN expression and pushes the result into the valuesStack
     *
     * @param input           valid RPN expression
     * @param isUndoOperation indicates if the operation is an undo operation.
     *                        undo operations use the same evaluation functions as the standard ones
     *                        but they are not pushed into instructionsStack
     * @throws CalculatorException
     */
    private void eval(String input, boolean isUndoOperation) throws CalculatorException {
        if (input == null) {
            throw new CalculatorException("Input cannot be null.");
        }
        currentTokenIndex = 0;
        String[] result = input.split("\\s");
        for (String aResult : result) {
            currentTokenIndex++;
            processToken(aResult, isUndoOperation);
        }
    }
}
