package com.example.generator;

import javax.xml.transform.Source;
import java.util.Locale;
import java.util.regex.Pattern;

public class JavaTypeGenerator {

    public String generate(String input) {
        input = validateInput(input);
        SourceCodeStructureGenerator sourceCodeStructureGenerator = new SourceCodeStructureGenerator();
        SourceCodeStructure sc = sourceCodeStructureGenerator.generate(input);
        return sc.toJavaSource();
    }

    private String validateInput(String input) {
        if(input == null || input.isBlank()) throw new IllegalArgumentException("Null or Empty input is given");
        if(input.matches("(?i)(\\s*package\\s*)$")) throw new IllegalArgumentException("Package name must be given");
        return input.trim();
    }

    private String generateJavaSource(SourceCodeStructure sc) {
        String classBlock = sc.getClassBlock();
        String packageStatement = sc.getPackageStatement();
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
