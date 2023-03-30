package com.shpp.p2p.cs.osimahin.assignment10;

public class Assignment10Part1 {

    public static void main(String[] args) {

        if (args.length!=0) {
            Calculator calc = new Calculator(args);
            System.out.println(calc.calculate() + "\n");
        }else {
            System.out.println("No parameters ");
        }

    }
}
