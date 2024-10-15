package com.example.generator;

public class PackageStatementGenerator {

    public String generate(String packageLine) {
        if(packageLine == null || packageLine.isEmpty()) return "";
        String[] splittedPackageLine = packageLine.split(" ");
        String packageName = splittedPackageLine[1];
        return "package " + packageName + ";";
    }
}
