package edu.unl.pal.featurefinder.tests;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import edu.unl.pal.featurefinder.Main;

class FeatureFinderTest {
    @TestFactory
    Collection<DynamicTest> featureTests() {
        List<DynamicTest> tests = new ArrayList<>();
        for (String feature : Main.featureOrder) {
            if (feature.equals("polymorphism")) {
                // Polymorphism is a special case that requires multiple files, so we have a dedicated test class for it.
                continue;
            }
            tests.add(DynamicTest.dynamicTest("Detects feature: " + feature, () -> {
                String javaSource = FeatureTestSources.getSourceForFeature(feature);
                Path tempFile = Files.createTempFile("FeatureTest_", ".java");
                Files.write(tempFile, javaSource.getBytes(StandardCharsets.UTF_8));
                String[] classPath = { tempFile.getParent().toString() };
                String[] sourcePath = { tempFile.getParent().toString() };
                Set<String> found = Main.analyzeFile(classPath, sourcePath, tempFile);
                assertTrue(found.contains(feature), "Feature not detected: " + feature);
                Files.deleteIfExists(tempFile);
            }));
        }
        return tests;
    }
}
