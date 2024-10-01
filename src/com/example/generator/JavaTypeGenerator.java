package com.example.generator;

import java.util.Locale;
import java.util.regex.Pattern;

public class JavaTypeGenerator {
    public String generate(String input) {
        if(input == null || input.isBlank()) throw new IllegalArgumentException("Null or Empty input is given");
        input = input.trim();
        if(input.equalsIgnoreCase("package")) throw new IllegalArgumentException("Package name must be given");

        String[] splittedLines = input.split("\n");
        if(splittedLines.length == 1) {
            if(splittedLines[0].startsWith("class ")) {
                String classLine = splittedLines[0];
                String classBlock = getClassBlock(classLine);
                String packageStatement = null;
                return generateJavaSource(classBlock, packageStatement);
            } else {
                return null;
            }
        } else if(splittedLines.length == 2) {
            String packageLine = splittedLines[0];
            String classLine = splittedLines[1];

            String classBlock = getClassBlock(classLine);
            String packageStatement = generatePackageStatement(packageLine);
            return generateJavaSource(classBlock, packageStatement);
        } else {
            return null;
        }

    }

    private String getClassBlock(String input) {
        String classString = "";
        String[] tokens = input.split(" ");
        if(tokens[0].equals("class")) {
            classString = generateClass(tokens[1]);
        }
        return classString;
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

    private String generatePackageStatement(String packageLine) {
        String[] splittedPackageLine = packageLine.split(" ");
        String packageName = splittedPackageLine[1];
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

    private String generateJavaSource(String classBlock, String packageStatement) {
        if(packageStatement == null) {
            return classBlock;
        } else {
            String source = """
                %s
                
                %s
                """.formatted(packageStatement, classBlock);

            return source;
        }
    }
}
