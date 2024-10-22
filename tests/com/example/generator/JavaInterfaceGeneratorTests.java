package com.example.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JavaInterfaceGeneratorTests {

    @Test
    public void shouldFailCreatingJavaClassFileGivenInvalidInterfaceName() {
        try{
            //given
            String input = """
                    interface
                    """;
            //when
            JavaTypeGenerator generator = new JavaTypeGenerator();
            generator.generate(input);
            //
            Assertions.fail("Expected IllegalArgumentException but didn't");
        } catch (IllegalArgumentException ex) {
            Assertions.assertTrue(true);
        } catch (Exception ex) {
            Assertions.fail("Expected IllegalArgumentException but thrown different exception");
        }
    }

    @Test
    public void shouldCreateJavaClassFileGivenValidInterfaceName() {
        //given
        String input = """
                interface foo
                """;
        //when
        JavaTypeGenerator generator = new JavaTypeGenerator();
        String output = generator.generate(input);
        //then
        String expected = """
                public interface Foo {
                }""";
        Assertions.assertEquals(expected, output);
    }

    @Test
    public void shouldCreateJavaClassFileGivenInterfaceAndMethod() {
        //given
        String input = """
                interface foo
                String getName
                """;
        //when
        JavaTypeGenerator generator = new JavaTypeGenerator();
        String output = generator.generate(input);
        //then
        String expected = """
                public interface Foo {
                    public String getName();
                }""";
        Assertions.assertEquals(expected, output);
    }
}
