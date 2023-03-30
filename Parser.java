package com.shpp.p2p.cs.osimahin.assignment10;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class substitutes the necessary numbers into the expression instead of letters
 */
public class Parser {
    //parsed expression
    private final ParserExpression expression;
    //parsed parameters
    private final ParserParameters parameters;
    //a collection of ready-made elements for calculation
    private final ArrayList<OperatorsName> expressionParser = new ArrayList<>();
    //item to be taken from the collection
    private int counterElement = 0;
    //a static collection that stores already parsed expressions
    private static HashMap<String, ParserExpression> collection = new HashMap<>();


    public Parser(String[] array) {
        //checks if this expression is parsed
        if (!collection.containsKey(array[0])) {
            collection.put(array[0], new ParserExpression(array[0]));
        }
        expression = collection.get(array[0]);
        parameters = new ParserParameters(array);
        convertExpression();
    }

    /**
     * substitutes numbers instead of letters
     */
    private void convertExpression() {
        OperatorsName name = expression.nextElement();
        while (name != null) {
            if (parameters.keyIsEmpty(name.value)) {
                expressionParser.add(new OperatorsName(name.operators, parameters.getValue(name.value)));
            }else {
                expressionParser.add(new OperatorsName(name.operators, name.value));
            }
            name = expression.nextElement();
        }
    }

    /**
     * checks if all values are known
     */
    protected boolean auditExpression() {
        for (OperatorsName name : expressionParser) {
            char element = name.value.charAt(0);
            if (element >= 'a' && element <= 'z' || name.operators == Operators.ERROR) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return returns the element and increments the counter by 1
     */
    protected OperatorsName nextElement() {
        return expressionParser.get(counterElement++);
    }

    /**
     * takes a step back
     */
    protected void stepBack() {
        counterElement--;
    }

}
