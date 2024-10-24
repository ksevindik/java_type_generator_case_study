package com.example.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JavaTypeGeneratorTests {

    private JavaTypeGenerator generator;

    @BeforeEach
    public void setUp() {
        generator = new JavaTypeGenerator();
    }

    @Test
    public void shouldFailCreatingJavaClassFileGivenEmptyString() {
        try {
            //given arrange
            String input = "";
            //when act
            String output = generator.generate(input);
            //then assert
            Assertions.fail("It should have failed with IllegalArgumentException, but didn't");
        } catch(IllegalArgumentException ex) {
            Assertions.assertTrue(true);
        } catch (Exception ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    public void shouldFailCreatingJavaClassFileGivenNullInput() {
        try {
            //given arrange
            String input = null;
            //when act
            String output = generator.generate(input);
            //then assert
            Assertions.fail("It should have failed with IllegalArgumentException, but didn't");
        } catch(IllegalArgumentException ex) {
            Assertions.assertTrue(true);
        } catch (Exception ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    public void shouldCreateJavaClassFileGivenValidInputContainingOnlyClassName() {
        //given
        String input = "class Foo";
        //when
        String output = generator.generate(input);
        //then
        String exptectedOutput = """
                public class Foo {
                }""";
        Assertions.assertEquals(exptectedOutput, output);
    }

    @Test
    public void shouldFailCreatingJavaClassFileGivenInvalidClassNameInput() {
        //given
        String input = "class %Foo";
        //when
        try {
            generator.generate(input);
            //then assert
            Assertions.fail("It should have failed with IllegalArgumentException, but didn't");
        } catch(Exception ex) {
            verifyInvalidClassNameFailure(ex);
        }
    }

    private void verifyInvalidClassNameFailure(Exception ex) {
        Assertions.assertInstanceOf(IllegalArgumentException.class,ex);
        Assertions.assertEquals("Class name contains illegal chars :%Foo",ex.getMessage());
    }

    @Test
    public void shouldCreateJavaClassFileGivenValidInputContainingOnlyClassNameWithLowercaseFirstChar() {
        //given
        String input = "class foo";
        //when
        String output = generator.generate(input);
        //then
        String exptectedOutput = """
                public class Foo {
                }""";
        Assertions.assertEquals(exptectedOutput, output);
    }

    @Test
    public void shouldFailGeneratingJavaClassFileGivenOnlyPackageKeyword() {
        //given
        String input = " package ";
        //given
        try {
            String output = generator.generate(input);
            Assertions.fail("It should have failed with IllegalArgumentException, but didn't");
        } catch(IllegalArgumentException ex) {
            Assertions.assertTrue(true);
        } catch (Exception ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    public void shouldReturnNullWhenGivenOnlyPackageName() {
        //given
        String input = "package org.example.casestudies";
        //when
        String output = generator.generate(input);
        //then
        Assertions.assertNull(output);
    }

    @Test
    public void shouldGenerateJavaClassFileWhenGivenValidPackageAndClassName() {
        //given
        String input = """
                package org.example.casestudies
                class foo
                """;
        //when
        String output = generator.generate(input);
        //then
        String expectedOutput = """
                package org.example.casestudies;
                
                public class Foo {
                }
                """;
        Assertions.assertEquals(expectedOutput, output);
    }

    @Test
    public void shouldFailCreatingJavaClassFileWhenGivenNullAttributeName() {
        try {
        //given
        String input = """
                package org.example.casestudies
                class foo
                String
                """;
        //when
        String output = generator.generate(input);
        //then
            Assertions.fail("It should have failed with IllegalArgumentException, but didn't");
        } catch (IllegalArgumentException ex) {
            Assertions.assertTrue(true);
        } catch (Exception ex) {
            Assertions.fail(ex);
        }

    }

    @Test
    public void shouldGenerateJavaClassFileWhenGivenValidPackageClassNameAndSingleAttribute() {
        //given
        String input = """
                package org.example.casestudies
                class foo
                String name
                """;
        //when
        String output = generator.generate(input);
        //then
        String expectedOutput = """
                package org.example.casestudies;
                
                public class Foo {
                    private String name;
                    
                    public getName() {
                        return this.name;
                    }
                    
                    public setName(String name) {
                        this.name = name;
                    }
                }
                """;
        Assertions.assertEquals(expectedOutput, output);
    }

    @Test
    public void shouldGenerateJavaClassFileWhenGivenValidPackageClassNameAndMultipleAttribute() {
        //given
        String input = """
                package org.example.casestudies
                class foo
                String name
                Integer age
                """;
        //when
        String output = generator.generate(input);
        //then
        String expectedOutput = """
                package org.example.casestudies;
                
                public class Foo {
                    private String name;
                    private Integer age;
                    
                    public getName() {
                        return this.name;
                    }
                    
                    public setName(String name) {
                        this.name = name;
                    }
                    
                    public getAge() {
                        return this.age;
                    }
                    
                    public setAge(Integer age) {
                        this.age = age;
                    }
                }
                """;
        Assertions.assertEquals(expectedOutput, output);
    }


}
