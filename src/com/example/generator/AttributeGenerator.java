package com.example.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttributeGenerator {
    private AttributeGenerator() {

    }

    private static AttributeGenerator instance = new AttributeGenerator();

    public static AttributeGenerator getInstance() {
        return instance;
    }

    public String generate(List<String> attributeLines) {
        String attributeString = null;
        List<String> declarationList = new ArrayList<>();
        List<String> getterSetterList = new ArrayList<>();

        if (!attributeLines.isEmpty()) {
            for (String attributeLine : attributeLines) {
                String[] attributeTokens = attributeLine.split(" ");
                if (attributeTokens.length < 2) {
                    throw new IllegalArgumentException("Attribute type or name cannot be null");
                }
                String attributeType = attributeTokens[0];
                String attributeName = attributeTokens[1];

                String declaration = "private " + attributeType + " " + attributeName + ";";
                declarationList.add(declaration);

                String getterSetter = generateGetterSetter(attributeName, attributeType);
                getterSetterList.add(getterSetter);
            }
        }

        String declarationPart = generateDeclarationPart(declarationList);
        String getterSetterPart = generateGetterSetterPart(getterSetterList);
        attributeString = generateAttributeString(declarationPart, getterSetterPart);

        return attributeString;
    }

    private String generateGetterSetter(String attributeName, String attributeType) {
        String getter = generateGetter(attributeName);
        String setter = generateSetter(attributeName, attributeType);
        String getterSetter = "%s\n%s".formatted(getter, setter);

        return getterSetter;
    }

    private String generateGetter(String attributeName) {
        String signature = "public get" + capitalizeFirstLetter(attributeName) + "()";
        String body = "return this." + attributeName + ";";
        String getter = """
                %s {
                    %s
                }""".formatted(signature, body).indent(4);

        return getter;
    }

    private String generateSetter(String attributeName, String attributeType) {
        String methodName = "public set" + capitalizeFirstLetter(attributeName);
        String parameters = attributeType + " " + attributeName;
        String signature = methodName + "(" + parameters + ")";
        String body = "this." + attributeName + " = " + attributeName + ";";

        return """
                %s {
                    %s
                }""".formatted(signature, body).indent(4);
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private static String generateGetterSetterPart(List<String> getterSetterList) {
        return getterSetterList.stream().collect(Collectors.joining("\n"));
    }

    private static String generateDeclarationPart(List<String> declarationList) {
        return declarationList.stream().collect(Collectors.joining("\n")).indent(4);
    }

    private static String generateAttributeString(String declarationPart, String getterSettersString) {
        return """
                %s
                %s""".formatted(declarationPart, getterSettersString);
    }
}
