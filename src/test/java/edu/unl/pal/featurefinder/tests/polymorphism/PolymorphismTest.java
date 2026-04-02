package edu.unl.pal.featurefinder.tests.polymorphism;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.unl.pal.featurefinder.Main;

public class PolymorphismTest {
    // @Test
    void detectsPolymorphism() throws Exception {
        Path dir = Files.createTempDirectory("PolymorphismTestDir_");

        Path aFile = dir.resolve("A.java");
        Path bFile = dir.resolve("B.java");
        Path testFile = dir.resolve("TestPolymorphism.java");

        Files.write(aFile, (
            "public class A { public void m() {} }\n").getBytes(StandardCharsets.UTF_8));
        Files.write(bFile, (
            "public class B extends A { @Override public void m() {} }\n").getBytes(StandardCharsets.UTF_8));
        Files.write(testFile, (
            "public class TestPolymorphism {\n" +
            "    public void f() { A a = new B(); a.m(); }\n" +
            "}\n").getBytes(StandardCharsets.UTF_8));

        String[] classPath = { dir.toString() };
        String[] sourcePath = { dir.toString() };
        Set<String> found = Main.analyzeFile(classPath, sourcePath, testFile);
        assertTrue(found.contains("polymorphism"), "Feature not detected: polymorphism");

        Files.deleteIfExists(aFile);
        Files.deleteIfExists(bFile);
        Files.deleteIfExists(testFile);
        Files.deleteIfExists(dir);
    }
}
