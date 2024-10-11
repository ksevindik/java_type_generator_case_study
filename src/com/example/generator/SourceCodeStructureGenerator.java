package com.example.generator;

import java.util.ArrayList;
import java.util.List;

public class SourceCodeStructureGenerator {

    public SourceCodeStructure generate(String input) {
        SourceCodeStructure sourceCodeStructure = new SourceCodeStructure();
        String classLine = null;
        List<String> attributeLines = new ArrayList<>();

        String[] splittedLines = input.split("\n");
        for (String line : splittedLines) {
            if(line.startsWith("class ")) {
                classLine = line;
            } else if (line.startsWith("package ")) {
                PackageStatementGenerator packageStatementGenerator = new PackageStatementGenerator();
                sourceCodeStructure.setPackageStatement(packageStatementGenerator.generate(line));
            } else {
                attributeLines.add(line);
            }
        }

        ClassBlockGenerator classBlockGenerator = new ClassBlockGenerator();
        sourceCodeStructure.setClassBlock(classBlockGenerator.generate(classLine, attributeLines));

        return sourceCodeStructure;
    }
}
