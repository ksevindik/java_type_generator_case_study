package com.example.generator;

import java.util.ArrayList;
import java.util.List;

public class InputSource {
    private String rawInput;
    private String classLine;
    private String packageLine;
    private List<String> attributeLines = new ArrayList<>();

    public InputSource(String rawInput) {
        this.rawInput = validateInput(rawInput);
        String[] splittedLines = rawInput.split("\n");
        for (String line : splittedLines) {
            if(line.startsWith("class ")) {
                this.classLine = line;
            } else if (line.startsWith("package ")) {
                this.packageLine = line;
            } else {
                this.attributeLines.add(line);
            }
        }
    }

    private String validateInput(String input) {
        if(input == null || input.isBlank()) throw new IllegalArgumentException("Null or Empty input is given");
        if(input.matches("(?i)(\\s*package\\s*)$")) throw new IllegalArgumentException("Package name must be given");
        return input.trim();
    }

    public String getClassLine() {
        return classLine;
    }

    public String getPackageLine() {
        return packageLine;
    }

    public List<String> getAttributeLines() {
        return attributeLines;
    }
}
