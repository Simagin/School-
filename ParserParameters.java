package com.shpp.p2p.cs.osimahin.assignment10;

import java.util.HashMap;

/**
 * this class forms each element of the collection into a key-value
 */
public class ParserParameters {
    //element of the collection into a key-value
    private HashMap<String, String> parameters = new HashMap<>();

    public ParserParameters(String[] array) {
        parserParameters(array);
    }

    /**
     * prepares each element of the array and passes it to the parserVariable() method
     *
     * @param args array
     */
    private void parserParameters(String[] args) {
        for (int i = 1; i < args.length; i++) {
            String line = args[i].replace(",", ".").replaceAll("\\s", "");
            parserVariable(line.toLowerCase());
        }
    }

    /**
     * splits an array element into key and value
     */
    private void parserVariable(String text) {
        int counterPoint = 0;
        boolean minus = false;
        String key = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char temporary = text.charAt(i);
            //if character is , the value is written and the stringbuilder is erased
            if (temporary == '=') {
                key = sb.toString();
                sb.delete(0, sb.length());
            } else {
                if (temporary >= 'a' && temporary <= 'z' && "".equals(key)) {
                    sb.append(temporary);
                    continue;
                }
                //if the key is generated and the number starts with a minus,
                // then the minus flag becomes tru to make the number negative at the end
                if (temporary == '-' && !"".equals(key)) {
                    minus = true;
                }
                if (temporary >= '0' && temporary <= '9' || temporary == '.') {
                    if (counterPoint > 0 && temporary == '.') {
                        continue;
                    }
                    if (temporary == '.' && counterPoint == 0) {
                        counterPoint++;
                        sb.append(temporary);
                        continue;
                    }
                    sb.append(temporary);
                }
            }
        }
        String variable = sb.toString();
        if (minus) {
            variable = "-" + variable;
        }
        if (sb.toString().endsWith(".")) {
            variable = variable.substring(0, sb.length() - 1);
        }
        parameters.put(key, variable);
    }

    /**
     * checks if such a key exists in the collection
     * @param name key to check
     * @return is there such a key in the collection
     */
    protected boolean keyIsEmpty(String name) {
        return parameters.containsKey(name);
    }

    /**
     * returns a value of key
     * @param name key
     * @return value
     */
    protected String getValue(String name) {
        return parameters.get(name);
    }
}
