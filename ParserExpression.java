package com.shpp.p2p.cs.osimahin.assignment10;

import java.util.ArrayList;

/**
 * this class parses a string to the correct form
 */
public class ParserExpression {
    //string that comes for parsing
    private final String text;
    //number of the symbol being processed
    private int counter = 0;
    //the number of the item in the collection
    private int counterElement = 0;
    //collection of OperatorName elements
    private ArrayList<OperatorsName> data = new ArrayList<>();

    public ParserExpression(String text) {
        this.text = text.toLowerCase().replace(",", ".").replaceAll("\\s", "");
        parserMethod();
        System.out.println("We entered the constructor -> ParserExpression ");
    }

    /**
     * This method checks each element of the string "text" in a loop
     * and checks for each element whether it can be added to the collection
     */
    private void parserMethod() {
        while (counter < text.length()) {
            char element = text.charAt(counter);
            switch (element) {
                case '*':
                    if (data.size() != 0) {
                        if (data.get(data.size() - 1).operators == Operators.VARIABLE) {
                            data.add(new OperatorsName(Operators.MULTIPLICATION, String.valueOf(element)));
                        }

                    }
                    counter++;
                    continue;
                case '/':
                    if (data.size() != 0) {
                        if (data.get(data.size() - 1).operators == Operators.VARIABLE) {
                            data.add(new OperatorsName(Operators.DIVISION, String.valueOf(element)));
                        }
                    }
                    counter++;
                    continue;
                case '^':
                    if (data.size() != 0) {
                        if (data.get(data.size() - 1).operators == Operators.VARIABLE) {
                            data.add(new OperatorsName(Operators.DEGREE, String.valueOf(element)));
                        }
                    }
                    counter++;
                    continue;
                case '+':
                    if (data.size() != 0) {
                        if (data.get(data.size() - 1).operators == Operators.VARIABLE)
                            data.add(new OperatorsName(Operators.PLUS, String.valueOf(element)));
                    }
                    counter++;
                    continue;
                case '-':
                    if (data.size() != 0) {
                        if (data.get(data.size() - 1).operators == Operators.MINUS) {
                            data.remove(data.size() - 1);
                            data.add(new OperatorsName(Operators.PLUS, "+"));
                        } else if (data.get(data.size() - 1).operators == Operators.PLUS) {
                            data.remove(data.size() - 1);
                            data.add(new OperatorsName(Operators.MINUS, String.valueOf(element)));
                        } else {
                            data.add(new OperatorsName(Operators.MINUS, String.valueOf(element)));
                        }
                    }
                    if (data.size() == 0) {
                        data.add(new OperatorsName(Operators.MINUS, String.valueOf(element)));
                    }
                    counter++;
                    continue;
                default:
                    //forms a number
                    if (element >= '0' && element <= '9') {
                        if (data.size() != 0) {
                            if (data.get(data.size() - 1).operators == Operators.VARIABLE) {
                                data.add(new OperatorsName(Operators.MULTIPLICATION, "*"));
                            }
                        }
                        data.add(parserNumber());

                        //forms a variable
                    } else if (element >= 'a' && element <= 'z') {
                        if (data.size() != 0) {
                            if (data.get(data.size() - 1).operators == Operators.VARIABLE) {
                                data.add(new OperatorsName(Operators.MULTIPLICATION, "*"));
                            }
                        }
                        data.add(parserVariable());
                        //if the character is unknown, it throws an error
                    } else {
                        data.add(new OperatorsName(Operators.ERROR, "!!!!!!!"));
                        counter++;
                    }
            }
        }
        //if the collection starts with a plus, it removes it
        if (data.size()!=0 && data.get(0).operators == Operators.PLUS) {
            data.remove(0);
        }
        //adding END at the end, which means that the expression is finished
        data.add(new OperatorsName(Operators.END, "~"));
    }

    /**
     * collects the variable if it is not of the same character
     * @return object OperatorsName
     */
    private OperatorsName parserVariable() {
        StringBuilder builder = new StringBuilder();
        char value = text.charAt(counter);
        do {
            builder.append(value);
            counter++;
            if (counter == text.length()) {
                break;
            }
            value = text.charAt(counter);
        }
        while (value >= 'a' && value <= 'z');
        return new OperatorsName(Operators.VARIABLE, builder.toString());
    }

    /**
     * collects the number
     * @return object OperatorsName
     */
    private OperatorsName parserNumber() {
        StringBuilder builder = new StringBuilder();
        char value = text.charAt(counter);
        do {
            //if the dot is followed by a dot again, returns an error
            if (builder.toString().contains(".") && text.charAt(counter) == '.') {
                counter++;
                return new OperatorsName(Operators.ERROR, "!!!!!!!");
            }
            builder.append(value);
            counter++;
            if (counter == text.length()) {
                break;
            }
            value = text.charAt(counter);
        }
        while (value >= '0' && value <= '9' || value == '.');
        //if ends with a dot, removes it
        if (builder.toString().endsWith(".")) {
            String substring = builder.substring(0, builder.length() - 1);
            return new OperatorsName(Operators.VARIABLE, substring);
        }
        return new OperatorsName(Operators.VARIABLE, builder.toString());
    }

    /**
     * alternately outputs the elements of the collection
     * @return element of collection
     */
    protected OperatorsName nextElement() {
        if (data.size() > counterElement) {
            return data.get(counterElement++);
        } else {
            counterElement = 0;
            return null;
        }
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
