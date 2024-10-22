package com.example.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class InterfaceBlockGenerator {

    private InterfaceBlockGenerator() {}

    private static InterfaceBlockGenerator instance = new InterfaceBlockGenerator();

    public static InterfaceBlockGenerator getInstance() {
        return instance;
    }

    public String generate(String interfaceLine, List<String> attributeLines) {
        String[] tokens = interfaceLine.split(" ");
        String interfaceName = formatInterfaceName(tokens[1]);
        String signatures = generateSignatures(attributeLines);
        String block = """
                public interface %s {
                    %s
                }""".formatted(interfaceName, signatures);

        if(signatures.isEmpty()){
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

    public String generateSignatures(List<String> attributeLines) {
        if(attributeLines.isEmpty() || attributeLines == null) return "";
        List<String> signatures = new ArrayList<>();
        for (String attributeLine : attributeLines) {
            String[] tokens = attributeLine.split(" ");
            String type = tokens[0];
            String methodName = tokens[1];
            signatures.add("public " + type + " " + methodName + "();");
        }
        return signatures.stream().collect(Collectors.joining("\n"));
    }

    private String formatInterfaceName(String interfaceName) {
        String firstChar = interfaceName.substring(0,1);
        String remainingPart = interfaceName.length()>1?interfaceName.substring(1):"";
        return firstChar.toUpperCase(Locale.ENGLISH) + remainingPart;
    }
}
