package com.example.generator;

import java.util.ArrayList;
import java.util.Arrays;
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
        String methodDefinitions = generateMethodDefinitions(attributeLines);
        return generateBody(interfaceName,methodDefinitions);
    }

    private String generateBody(String interfaceName, String methodDefinitions) {
        String block;
        if(methodDefinitions.isEmpty()){
            block = """
                    public interface %s {
                    }""".formatted(interfaceName);
        } else {
            block = """
                    public interface %s {
                        %s
                    }""".formatted(interfaceName, methodDefinitions);
        }
        return block;
    }

    public String generateMethodDefinitions(List<String> attributeLines) {
        if(attributeLines.isEmpty()) return "";
        else return attributeLines.stream().map(this::generateMethodDefinition).collect(Collectors.joining("\n"));
    }

    private String generateMethodDefinition(String attributeLine) {
        String[] tokens = attributeLine.split(" ");
        String type = tokens[0];
        String methodName = tokens[1];
        String params = generateParams(tokens);
        return """
                public %s %s(%s);""".formatted(type, methodName, params);
    }

    private String generateParams(String[] tokens) {
        String returnType = "";
        String parameterName = "";
        List<String> paramList = new ArrayList<>();
        for(int i = 2; i < tokens.length; i++){
            if(i % 2 == 0) {
                returnType = tokens[i];
            } else {
                parameterName = tokens[i];
                String parameter = generateParam(returnType, parameterName);
                paramList.add(parameter);
            }
        }

        return paramList.stream().collect(Collectors.joining(", "));
    }

    private String generateParam(String returnType, String parameterName) {
        if(returnType.isEmpty()) {
            return "";
        } else {
            return """
                    %s %s""".formatted(returnType,parameterName);
        }
    }


    private String formatInterfaceName(String interfaceName) {
        String firstChar = interfaceName.substring(0,1);
        String remainingPart = interfaceName.length()>1?interfaceName.substring(1):"";
        return firstChar.toUpperCase(Locale.ENGLISH) + remainingPart;
    }
}
