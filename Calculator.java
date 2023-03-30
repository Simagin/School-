package com.shpp.p2p.cs.osimahin.assignment10;

/**
 * This class performs calculations on a string of characters
 */
public class Calculator {
    //parser class variable
    private final Parser parser;

    public Calculator(String[] array) {
        parser = new Parser(array);
    }

    /**
     * with the help of the onePriority() method, the parser calculates the expression of the object
     * @return expression value
     */
    public double calculate() {
        if (parser.auditExpression()) {
            OperatorsName operatorsName = parser.nextElement();
            //checks if the collection is not empty
            if (operatorsName.operators == Operators.END) {
                System.out.println(" incorrect data in the collection ");
                return -0.0;
            }
            parser.stepBack();
            return onePriority();
        } else {
            System.out.println(" expression failed validation!");
        }
        return -0.0;
    }

    /** RECURSIVE DESCENT PARSER
     * the methods below recursively evaluate an expression
     * onePriority returns the result if the operator PLUS, MINUS
     * twoPriority returns the result if the operator MULTIPLICATION, DIVISION
     * threePriority returns the result if the operator DEGREE
     * fourPriority returns the result if the operator VARIABLE
     * @return expression value
     */
    private Double onePriority() {
        double value = twoPriority();
        while (true) {
            OperatorsName operator = parser.nextElement();
            switch (operator.operators) {
                case PLUS:
                    value += twoPriority();
                    break;
                case MINUS:
                    value -= twoPriority();
                    break;
                case END:
                    parser.stepBack();
                    return value;
            }
        }
    }
    private Double twoPriority() {
        double value = threePriority();
        while (true) {
            OperatorsName operator = parser.nextElement();
            switch (operator.operators) {
                case MULTIPLICATION:
                    value *= threePriority();
                    break;
                case DIVISION:
                    double var = threePriority();
                    if (var == 0) {
                        System.out.println("DIVISION ON \"0\", value not correct");
                        value /= 1;
                    } else {
                        value /= var;
                    }
                    break;
                case PLUS:
                case MINUS:
                case END:
                    parser.stepBack();

                    return value;
            }
        }
    }
    private Double threePriority() {
        double value = fourPriority();
        while (true) {
            OperatorsName operator = parser.nextElement();
            switch (operator.operators) {
                case DEGREE:
                    double variable = threePriority();
                    value = Math.pow(value, variable);
                    break;
                case DIVISION:
                case MULTIPLICATION:
                case PLUS:
                case MINUS:
                case END:
                    parser.stepBack();
                    return value;
            }
        }
    }
    private Double fourPriority() {
        OperatorsName operator = parser.nextElement();
        if (operator.operators == Operators.VARIABLE) {
            return Double.valueOf(operator.value);
        }
        if (operator.operators == Operators.MINUS) {
            double value;
            operator = parser.nextElement();
            if (operator.operators == Operators.VARIABLE) {
                value = Double.parseDouble(operator.value);
                return -value;
            }
        }
        System.out.println(" ERROR!!! the calculation is not correct!");
        return 0.0;
    }
}