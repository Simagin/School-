package com.shpp.p2p.cs.osimahin.assignment10;

public class OperatorsName {
    protected Operators operators;
    protected String value;

    public OperatorsName(Operators operators, String value) {
        this.operators = operators;
        this.value = value;
    }

    @Override
    public String toString() {
        return " " + value +" ";
    }
}
