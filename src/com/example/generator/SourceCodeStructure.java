package com.example.generator;

import java.util.Locale;

public class SourceCodeStructure {
    private String classBlock = "";
    private String packageStatement = "";
    private String interfaceBlock = "";

    public SourceCodeStructure(InputSource inputSource) {
        this.packageStatement = PackageStatementGenerator.getInstance().generate(inputSource.getPackageLine());
        this.classBlock = ClassBlockGenerator.getInstance().generate(inputSource.getClassLine(), inputSource.getAttributeLines());

        String interfaceLine = inputSource.getInterfaceLine();
        String[] tokens = interfaceLine.split(" ");
        String key = tokens[0];
        String interfaceName = formatInterfaceName(tokens[1]);
        if(key.equals("interface")) {
            this.interfaceBlock = """
                    public interface %s {
                    }""".formatted(interfaceName);
        }
    }

    private String formatInterfaceName(String interfaceName) {
        String firstChar = interfaceName.substring(0,1);
        String remainingPart = interfaceName.length()>1?interfaceName.substring(1):"";
        return firstChar.toUpperCase(Locale.ENGLISH) + remainingPart;
    }

    public String toJavaSource() {
        if(!packageStatement.isEmpty() && (classBlock.isEmpty() || interfaceBlock.isEmpty())){
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
