package com.example.generator;

public class JavaTypeGenerator {
    public String generate(String rawInput) {
        return new SourceCodeStructure(new InputSource(rawInput)).toJavaSource();
    }
}
