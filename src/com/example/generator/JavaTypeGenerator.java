package com.example.generator;

import java.util.Locale;
import java.util.regex.Pattern;

public class JavaTypeGenerator {
    public String generate(String input) {
        if(input == null || input.isBlank()) throw new IllegalArgumentException("Null or Empty input is given");
        input = input.trim();
        if(input.equalsIgnoreCase("package")) throw new IllegalArgumentException("Package name must be given");

        String[] splitted = input.split("\n");
        if(splitted.length == 1) {
            String[] tokens = input.split(" ");
            if(tokens[0].equals("class")) {
                String classString = generateClass(tokens[1]);
                return classString;
            }
        }

        if(splitted.length == 2) {
            String packageLine = splitted[0];
            String classLine = splitted[1];

            String[] tokens = classLine.split(" ");
            String classString = "";
            if(tokens[0].equals("class")) {
                classString = generateClass(tokens[1]);
            }

            String[] splittedPackageLine = packageLine.split(" ");
            String packageString = generatePackageBlock(splittedPackageLine[1]);

            String source = """
                %s
                
                %s
                """.formatted(packageString, classString);
            return source;
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

    private String generatePackageBlock(String packageName) {
        return "package " + packageName + ";";
    }

    private String generateClassBlock(String className) {
        return """
                class %s {
                }""".formatted(className);
    }

    private String generateClass(String rawClassName) {
        validateClassName(rawClassName);
        String formattedClassName = formatClassName(rawClassName);
        return generateClassBlock(formattedClassName);
    }
}
