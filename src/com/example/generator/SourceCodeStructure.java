package com.example.generator;

import java.util.ArrayList;
import java.util.List;

public class SourceCodeStructure {
    private String classBlock = "";
    private String packageStatement = "";
    private List<String> attributeList = new ArrayList<>();

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

    public List<String> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<String> attributeList) {
        this.attributeList = attributeList;
    }
}
