package com.example.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClassBlockGenerator {

    private ClassBlockGenerator() {

    }

    private static ClassBlockGenerator instance = new ClassBlockGenerator();

    public static ClassBlockGenerator getInstance() {
        return instance;
    }

    public String generate(String classLine, List<String> attributeLine) {
        if(classLine == null) return "";

        String attributeString = AttributeGenerator.getInstance().generate(attributeLine);

        String classBlock = "";
        String[] tokens = classLine.split(" ");
        if(tokens[0].equals("class")) {
            String className = tokens[1];
            classBlock = generateClassBlock(className, attributeString);
        }

        return classBlock;
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

    private String generateClassBlock(String className, String attributeString) {
        validateClassName(className);
        String formattedClassName = formatClassName(className);
        String block = """
                public class %s {
                %s}""".formatted(formattedClassName, attributeString);

        if(attributeString.trim().isEmpty()) {
            return squizeEmptyBody(block);
        } else {
            return block;
        }
    }

    public String squizeEmptyBody(String block) {
        String[] splittedLines = block.split("\n");
        List<String> newLines = new ArrayList<>();
        for (String line : splittedLines) {
            if(!line.trim().isEmpty()) {
                newLines.add(line);
            }
        }
        return  newLines.stream().collect(Collectors.joining("\n"));
    }
}
