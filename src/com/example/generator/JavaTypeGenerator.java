package com.example.generator;

import java.util.Locale;
import java.util.regex.Pattern;

public class JavaTypeGenerator {
    public String generate(String input) {
        if(input == null || input.isBlank()) throw new IllegalArgumentException("Null or Empty input is given");
        String[] tokens = input.split(" ");
        if(tokens[0].equals("class")) {
            return generateClass(tokens[1]);
        }
        return null;
    }

    private void validateClassName(String className) {
        Pattern pattern = Pattern.compile("^[A-Za-z_][A-Za-z0-9_$]*$");
        if(!pattern.matcher(className).matches())
            throw new IllegalArgumentException("Class name contains illegal chars :" + className);
    }

    private String formatClassName(String className) {
        String firstChar = className.substring(0,1);
        String remainingPart = className.length()>1?className.substring(1):"";
        return firstChar.toUpperCase(Locale.ENGLISH) + remainingPart;
    }

    private String generateClassBlock(String className) {
        return """
                class %s {
                }
                """.formatted(className);
    }

    private String generateClass(String rawClassName) {
        validateClassName(rawClassName);
        String formattedClassName = formatClassName(rawClassName);
        return generateClassBlock(formattedClassName);
    }
}
