# Feature Finder

This is a simple Java project that uses the Eclipse JDT parser to analyze Java source files. It looks for specific features in the code.

## Usage

1. Build the project with Gradle:
   ```sh
   ./gradlew build
   ```
2. Run the script on a Java source file:
   ```sh
   ./gradlew run --args='path/to/YourFile.java'
   ```

## Main Features
- Parses Java source files using Eclipse JDT
- Identifies and reports on specific programming features

## Project Structure
- `src/main/java/com/example/jdtparser/Main.java`: Main entry point and visitor implementation
- `build.gradle`: Gradle build configuration

## Requirements
- Java 8 or higher
- Internet connection for dependency download
