package com.example.generator;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaTypeGenerator {
    public String generate(String input) {
        input = validateInput(input);
        SourceCodeStructure sc = generateSourceCodeStructure(input);
        return generateJavaSource(sc);
    }

    private SourceCodeStructure generateSourceCodeStructure(String input) {
        SourceCodeStructure sourceCodeStructure = new SourceCodeStructure();
        String className = null;


        String[] splittedLines = input.split("\n");
        for (String line : splittedLines) {
            if(line.startsWith("class ")) {
                className = line.split("")[1];
            } else if (line.startsWith("package ")) {
                sourceCodeStructure.setPackageStatement(generatePackageStatement(line));
            } else {
                List<String> attributeList = sourceCodeStructure.getAttributeList();
                attributeList.add(generateAttributeStatement(line));
            }

        }

        generateClassBlock(className, sourceCodeStructure.getAttributeList());

        return sourceCodeStructure;
    }

    private String validateInput(String input) {
        if(input == null || input.isBlank()) throw new IllegalArgumentException("Null or Empty input is given");
        if(input.matches("(?i)(\\s*package\\s*)$")) throw new IllegalArgumentException("Package name must be given");
        return input.trim();
    }

//    private String getClassBlock(String input) {
//        String classString = "";
//        String[] tokens = input.split(" ");
//        if(tokens[0].equals("class")) {
//            classString = generateClass(tokens[1]);
//        }
//        return classString;
//    }

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

    private String generateClassBlock(String className, List<String> attributeList) {
        return """
                class %s {
                $s
                }""".formatted(className, attributeList.get(0));
    }

//    private String generateClass(String rawClassName) {
//        validateClassName(rawClassName);
//        String formattedClassName = formatClassName(rawClassName);
//        return generateClassBlock(formattedClassName);
//    }

    private String generateAttributeStatement(String attributeLine) {
        String[] splittedLine = attributeLine.split(" ");
        String attributeType = splittedLine[0];
        String attributeName = splittedLine[1];
        String attributeStatement = """
                private %s %s;
                
                public %s get%s() {
                        return %s;
                    }
                    
                    public void set%s(%s %s) {
                        this.name = %s;
                    }
                """.formatted(attributeType, attributeName, attributeType, attributeName, attributeName, attributeName, attributeType, attributeName, attributeName);
        return attributeStatement;
    }

    private String generateJavaSource(SourceCodeStructure sc) {
        String classBlock = sc.getClassBlock();
        String packageStatement = sc.getPackageStatement();
        if(!packageStatement.isEmpty() && classBlock.isEmpty()){
            return null;
        }
        if(packageStatement.isEmpty()) {
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
