package com.example.generator;

public class SourceCodeStructure {
    private String classBlock = "";
    private String packageStatement = "";

    public SourceCodeStructure(InputSource inputSource) {
        this.packageStatement = new PackageStatementGenerator().generate(inputSource.getPackageLine());
        this.classBlock = new ClassBlockGenerator().generate(inputSource.getClassLine(), inputSource.getAttributeLines());
    }

    public String toJavaSource() {
        if(!packageStatement.isEmpty() && classBlock.isEmpty()){
            return null;
        }
        if(packageStatement.isEmpty()) {
            return classBlock;
        } else {
            String source = """
                %s
                
                %s
                """.formatted(packageStatement, classBlock);

            return source;
        }
    }
}
