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

   or run on a whole directory containing Java source files:
   ```sh
   ./gradlew run --args='path/to/directory'
   ```

The program will generate a `features.csv` file in the current directory, containing the identified features for each analyzed Java source file.  The structure looks like:

```csv
filename,loops,nested loops,conditionals,switch statements,recursion,exception-based control flow,arrays,collections,maps,recursive data structures,integers,floats,boolean,enumerations,strings,inheritance,interfaces,anonymous classes,abstract classes,polymorphism,generics,raw types,object casting,primitive casting,instance methods,static methods,threads,synchronization,interleavings,lambdas,Network and File I/O,GUI,RE Pattern Syntax,streams,reflection,Math
./src/main/java/edu/unl/pal/featurefinder/Main.java,YES,NO,YES,YES,YES,YES,YES,YES,YES,NO,YES,NO,YES,NO,YES,YES,NO,NO,NO,YES,YES,NO,YES,NO,YES,YES,NO,NO,NO,YES,YES,NO,NO,YES,NO,NO
```

## Main Features
- Parses Java source files using Eclipse JDT
- Identifies and reports on specific programming features

## Project Structure
- `src/main/java/edu/unl/pal/featurefinder/Main.java`: Main entry point and visitor implementation
- `build.gradle`: Gradle build configuration

## Requirements
- Java 8 or higher

# Identified Features

- **Data Types:**
   - arrays
   - collections
   - maps
   - recursive data structures
   - integers
   - floats
   - boolean
   - enumerations
   - strings
   - generics
   - raw types
- **Syntactic Constructs:**
   - loops: `for`, `while`, `do-while` loops
      - nested loops: loops nested within loops
   - conditionals: `if`, `if-else` statements, ternary operators
   - switch statements
   - instance methods
   - static methods
   - lambdas
- **Object-Oriented Features:**
   - exception-based control flow
   - inheritance
   - interfaces
   - anonymous classes
   - abstract classes
   - polymorphism
   - object casting
   - primitive casting
   - recursion
- **API Usage:**
   - threads: use of `java.lang.Thread`or `java.lang.Runnable`
   - synchronization: use of `synchronized` keyword, `java.util.concurrent` classes
   - interleavings: if it uses threads and synchronization, it likely has interleavings
   - Network and File I/O: use of `java.io`, `java.nio`, and `java.net` classes
   - GUI: use of `javax.swing`, `java.awt`, and `javafx.` classes
   - RE Pattern Syntax: use of `java.util.regex` package
   - streams: use of `java.util.stream` API
   - reflection: use of `java.lang.reflect` package or certain methods like `Class.forName()`, `Method.invoke()`, etc.
   - Math: use of `java.lang.Math` methods
