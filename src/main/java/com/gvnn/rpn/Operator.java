package com.gvnn.rpn;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public enum Operator {

    ADDITION("+", "-", 2) {
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            return secondOperand + firstOperand;
        }
    },

    SUBTRACTION("-", "+", 2) {
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand - firstOperand;
        }
    },

    MULTIPLICATION("*", "/", 2) {
        public Double calculate(Double firstOperand, Double secondOperand) {
            return secondOperand * firstOperand;
        }
    },

    DIVISION("/", "*", 2) {
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            if (firstOperand == 0)
                throw new CalculatorException("Cannot divide by 0.");
            return secondOperand / firstOperand;
        }
    },

    SQUAREROOT("sqrt", "pow", 1) {
        public Double calculate(Double firstOperand, Double secondOperand) {
            return sqrt(firstOperand);
        }
    },

    POWER("pow", "sqrt", 1) {
        public Double calculate(Double firstOperand, Double secondOperand) {
            return pow(firstOperand, 2.0);
        }
    },

    UNDO("undo", null, 0) {
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    },

    CLEAR("clear", null, 0) {
        public Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException {
            throw new CalculatorException("Invalid operation");
        }
    };
    // using map for a constant lookup cost
    private static final Map<String, Operator> lookup = new HashMap<String, Operator>();

    static {
        for (Operator o : values()) {
            lookup.put(o.getSymbol(), o);
        }
    }

    private String symbol;
    private String opposite;
    private int operandsNumber;

    Operator(String symbol, String opposite, int operandsNumber) {
        this.symbol = symbol;
        this.opposite = opposite;
        this.operandsNumber = operandsNumber;
    }

    public static Operator getEnum(String value) {
        return lookup.get(value);
    }

    public abstract Double calculate(Double firstOperand, Double secondOperand) throws CalculatorException;

    public String getSymbol() {
        return symbol;
    }

    public String getOpposite() {
        return opposite;
    }

    public int getOperandsNumber() {
        return operandsNumber;
    }

    @Override
    public String toString() {
        return symbol;
    }
}