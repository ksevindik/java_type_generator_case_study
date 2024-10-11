package com.example.generator;

public class SourceCodeStructure {
    private String classBlock = "";
    private String packageStatement = "";

    public String getClassBlock() {
        return classBlock;
    }

    public void setClassBlock(String classBlock) {
        this.classBlock = classBlock;
    }

    public String getPackageStatement() {
        return packageStatement;
    }

    public void setPackageStatement(String packageStatement) {
        this.packageStatement = packageStatement;
    }

    public String toJavaSource() {
        throw new RuntimeException("not implemented");
    }
}
