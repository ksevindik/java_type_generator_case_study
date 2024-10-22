package com.example.generator;

import java.util.Locale;

public class SourceCodeStructure {
    private String classBlock = "";
    private String packageStatement = "";
    private String interfaceBlock = "";

    public SourceCodeStructure(InputSource inputSource) {
        this.packageStatement = PackageStatementGenerator.getInstance().generate(inputSource.getPackageLine());
        this.classBlock = ClassBlockGenerator.getInstance().generate(inputSource.getClassLine(), inputSource.getAttributeLines());
        if(classBlock.isEmpty() && inputSource.getInterfaceLine() != null) {
            this.interfaceBlock = InterfaceBlockGenerator.getInstance().generate(inputSource.getInterfaceLine(), inputSource.getAttributeLines());
        }
    }

    public String toJavaSource() {
        if(packageStatement.isEmpty() && classBlock.isEmpty() && interfaceBlock.isEmpty()){
            return null;
        } else if (!packageStatement.isEmpty() && classBlock.isEmpty() && interfaceBlock.isEmpty()) {
            return null;
        }

        if(packageStatement.isEmpty()) {
            String block = interfaceBlock == "" ? classBlock : interfaceBlock;
            return block;
        } else {
            String block = interfaceBlock == "" ? classBlock : interfaceBlock;
            String source = """
                %s
                
                %s
                """.formatted(packageStatement, block);

            return source;
        }
    }
}
