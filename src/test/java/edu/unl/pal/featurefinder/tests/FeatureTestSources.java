package edu.unl.pal.featurefinder.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureTestSources {
    private static final Map<String, List<String>> sources = new HashMap<>();

    static {
        sources.put("loops", List.of(
            // Simple for loop
            "public class Loops1 { void m() { for(int i=0;i<1;i++){} } }",
            // While loop
            "public class Loops2 { void m() { int i=0; while(i<1){i++;} } }",
            // Do-while loop
            "public class Loops3 { void m() { int i=0; do { i++; } while(i<1); } }",
            // Enhanced for loop
            "public class Loops4 { void m() { for(String s : new String[]{\"a\"}) {} } }",
            // Nested loops
            "public class Loops5 { void m() { for(int i=0;i<1;i++) for(int j=0;j<1;j++){} } }",
            // Labeled loop
            "public class Loops6 { void m() { outer: for(int i=0;i<1;i++) break outer; } }"
        ));
        sources.put("nested loops", List.of(
            // for inside for
            "public class NL1 { void m() { for(int i=0;i<1;i++) { for(int j=0;j<1;j++) {} } } }",
            // while inside for
            "public class NL2 { void m() { for(int i=0;i<1;i++) { while(false) {} } } }",
            // do-while inside while
            "public class NL3 { void m() { int i=0; while(i<1) { do { i++; } while(i<1); } } }",
            // triple nested loops
            "public class NL4 { void m() { for(int i=0;i<1;i++) for(int j=0;j<1;j++) for(int k=0;k<1;k++) {} } }",
            // labeled nested loops
            "public class NL5 { void m() { outer: for(int i=0;i<1;i++) inner: for(int j=0;j<1;j++) break outer; } }"
        ));
        sources.put("conditionals", List.of(
            // Simple if
            "public class Cond1 { void m() { if(true){} } }",
            // If-else
            "public class Cond2 { void m() { if(false){} else{} } }",
            // Else-if chain
            "public class Cond3 { void m() { if(false){} else if(true){} else{} } }",
            // Nested if
            "public class Cond4 { void m() { if(true){ if(false){} } } }",
            // Conditional (ternary) expression
            "public class Cond5 { int m(boolean b) { return b ? 1 : 2; } }"
        ));
        sources.put("switch statements", List.of(
            // Classic switch, single case
            "public class Switch1 { void m() { switch(1) { case 1: break; } } }",
            // Switch with multiple cases
            "public class Switch2 { void m() { switch(2) { case 1: break; case 2: break; } } }",
            // Switch with default
            "public class Switch3 { void m() { switch(3) { default: break; } } }",
            // Nested switch
            "public class Switch4 { void m() { switch(1) { case 1: switch(2) { case 2: break; } break; } } }",
            // Switch expression (Java 14+)
            "public class Switch5 { int m(int x) { return switch(x) { case 1 -> 10; default -> 0; }; } }"
        ));
        sources.put("recursion", List.of(
            // Direct recursion
            "public class Rec1 { int f(int n){ return n==0?1:f(n-1); } }",
            // Mutual recursion
            "public class Rec2 { int f(int n){ return n==0?1:g(n-1); } int g(int n){ return n==0?1:f(n-1); } }",
            // Tail recursion
            "public class Rec3 { int f(int n, int acc){ return n==0?acc:f(n-1,acc*n); } }",
            // Recursion with objects
            "public class Rec4 { Rec4 next; int sum() { return next == null ? 1 : 1 + next.sum(); } }"
        ));
        sources.put("exception-based control flow", List.of(
            // Simple try-catch
            "public class Ex1 { void m() { try{}catch(Exception e){} } }",
            // Try-finally
            "public class Ex2 { void m() { try{}finally{} } }",
            // Try-catch-finally
            "public class Ex3 { void m() { try{}catch(Exception e){}finally{} } }",
            // Multi-catch
            "public class Ex4 { void m() { try{}catch(Exception|RuntimeException e){} } }",
            // Throw statement
            "public class Ex5 { void m() { throw new RuntimeException(); } }",
            // Catch with multiple exception types (separate blocks)
            "public class Ex6 { void m() { try{}catch(IllegalArgumentException e){}catch(NullPointerException e){} } }",
            // Nested try-catch
            "public class Ex7 { void m() { try{ try{}catch(Exception e){} }catch(Exception e){} } }"
        ));
        sources.put("arrays", List.of(
            "public class Arr1 { int[] a = new int[1]; }",
            "public class Arr2 { int f(int[] arr) { return arr.length; } }",
            "public class Arr3 { void m() { int[] arr = {1,2,3}; } }"
        ));
        sources.put("collections", List.of(
            // field
            "import java.util.List; public class C1 { List<String> l; }",
            // return type
            "import java.util.List; public class C2 { List<Integer> f() { return null; } }",
            // local variable
            "import java.util.List; public class C3 { void m() { List<Double> d; } }",
            // method argument
            "import java.util.List; public class C4 { void m(List<Float> f) {} }",
            // inheritance
            "import java.util.ArrayList; public class C5 extends ArrayList<String> {}"
        ));
        sources.put("maps", List.of(
            // Field
            "import java.util.Map; public class Map1 { Map<String,Integer> m; }",
            // Return type
            "import java.util.Map; public class Map2 { Map<String,Double> f() { return null; } }",
            // Local variable
            "import java.util.Map; public class Map3 { void m() { Map<Long,Long> m; } }",
            // Method argument
            "import java.util.Map; public class Map4 { void m(Map<Float,Float> m) {} }",
            // Inheritance
            "import java.util.HashMap; public class Map5 extends HashMap<String,Integer> {}",
            // Usage with generics (wildcard)
            "import java.util.Map; public class Map6 { void m(Map<?,?> m) {} }"
        ));
        sources.put("recursive data structures", List.of(
            // Simple singly-linked list
            "public class RecDS1 { RecDS1 next; }",
            // Doubly-linked list
            "public class RecDS2 { RecDS2 next; RecDS2 prev; }",
            // Tree node
            "public class RecDS3 { RecDS3 left; RecDS3 right; }",
            // Graph node
            "public class RecDS4 { java.util.List<RecDS4> neighbors; }",
            // Recursive structure with value
            "public class RecDS5 { int value; RecDS5 child; }"
        ));
        sources.put("integers", List.of(
            // Field
            "public class IntField { int x = 1; }",
            // Return type
            "public class IntReturn { int f() { return 42; } }",
            // Local variable
            "public class IntLocal { void m() { int y = 2; } }",
            // Method argument
            "public class IntArg { void m(int z) {} }",
            // Array of ints
            "public class IntArray { int[] arr = {1,2,3}; }"
        ));
        sources.put("floats", List.of(
            // Field
            "public class FloatField { float f = 1.0f; }",
            // Return type
            "public class FloatReturn { float f() { return 3.14f; } }",
            // Local variable
            "public class FloatLocal { void m() { float x = 2.7f; } }",
            // Method argument
            "public class FloatArg { void m(float y) {} }",
            // Array of floats
            "public class FloatArray { float[] arr = {1.0f, 2.0f}; }"
        ));
        sources.put("boolean", List.of(
            // Field
            "public class BoolField { boolean b = true; }",
            // Return type
            "public class BoolReturn { boolean f() { return false; } }",
            // Local variable
            "public class BoolLocal { void m() { boolean b = true; } }",
            // Method argument
            "public class BoolArg { void m(boolean b) {} }",
            // Array of booleans
            "public class BoolArray { boolean[] arr = {true, false}; }"
        ));
        sources.put("enumerations", List.of(
            // Enum declaration
            "public enum E { A, B }",
            // Enum as field
            "public class EnumField { java.lang.Thread.State e = java.lang.Thread.State.NEW; }",
            // Enum as return type
            "public class EnumReturn { java.lang.Thread.State f() { return java.lang.Thread.State.NEW; } }",
            // Enum as method argument
            "public class EnumArg { void m(java.lang.Thread.State e) {} }",
            // Enum as local variable
            "public class EnumLocal { void m() { java.lang.Thread.State e = java.lang.Thread.State.NEW; } }",
            // Enum array
            "public class EnumArray { java.lang.Thread.State[] arr = java.lang.Thread.State.values(); }"
        ));
        sources.put("strings", List.of(
            // Field
            "public class StrField { String s = \"hi\"; }",
            // Return type
            "public class StrReturn { String f() { return \"bye\"; } }",
            // Local variable
            "public class StrLocal { void m() { String s = \"foo\"; } }",
            // Method argument
            "public class StrArg { void m(String s) {} }",
            // Array of strings
            "public class StrArray { String[] arr = {\"a\", \"b\"}; }"
        ));
        sources.put("inheritance", List.of(
            // Simple class inheritance
            "class A{} class B extends A{}",
            // Multi-level inheritance
            "class C{} class D extends C{} class E extends D{}",
            // Inheritance with field of parent type
            "class F{} class G extends F{} class H { F f; }",
            // Inheritance as return type
            "class I{} class J extends I{} class K { I f() { return new J(); } }",
            // Inheritance as method argument
            "class L{} class M extends L{} class N { void m(L l) {} }",
            // Inheritance as local variable
            "class O{} class P extends O{} class Q { void m() { O o = new P(); } }",
            // Interface inheritance
            "interface R {} interface S extends R {}",
            // Abstract class inheritance
            "abstract class T {} class U extends T {}"
        ));
        sources.put("interfaces", List.of(
            // Simple interface implementation
            "interface I1{} class C1 implements I1{}",
            // Multiple interfaces
            "interface I2{} interface I3{} class C2 implements I2, I3{}",
            // Interface as field
            "interface I4{} class C3 { I4 i; }",
            // Interface as return type
            "interface I5{} class C4 { I5 f() { return null; } }",
            // Interface as method argument
            "interface I6{} class C5 { void m(I6 i) {} }",
            // Interface as local variable
            "interface I7{} class C6 { void m() { I7 i = null; } }",
            // Interface inheritance
            "interface I8{} interface I9 extends I8{}"
        ));
        sources.put("anonymous classes", List.of(
            // Anonymous class as field
            "class C1 { Runnable r = new Runnable(){ public void run(){} }; }",
            // Anonymous class as local variable
            "class C2 { void m() { Runnable r = new Runnable(){ public void run(){} }; } }",
            // Anonymous class as method argument
            "class C3 { void m(Runnable r) {} void f() { m(new Runnable(){ public void run(){} }); } }",
            // Anonymous class as return type
            "class C4 { Runnable f() { return new Runnable(){ public void run(){} }; } }",
            // Anonymous class implementing interface
            "interface I { void x(); } class C5 { I i = new I() { public void x() {} }; }",
            // Anonymous class extending abstract class
            "abstract class A { abstract void y(); } class C6 { A a = new A() { void y() {} }; }"
        ));
        sources.put("abstract classes", List.of(
            // Simple abstract class extension
            "abstract class A1{} class B1 extends A1{}",
            // Abstract class with abstract method, implemented in subclass
            "abstract class A2 { abstract void m(); } class B2 extends A2 { void m() {} }",
            // Abstract class as field
            "abstract class A3{} class C1 { A3 a; }",
            // Abstract class as return type
            "abstract class A4{} class C2 { A4 f() { return null; } }",
            // Abstract class as method argument
            "abstract class A5{} class C3 { void m(A5 a) {} }",
            // Abstract class as local variable
            "abstract class A6{} class C4 { void m() { A6 a = null; } }",
            // Multi-level abstract class inheritance
            "abstract class A7{} abstract class B3 extends A7{} class C5 extends B3{}"
        ));
        // Polymorphism test is now a dedicated fixture and test class.
        sources.put("generics", List.of(
            // Generic class with type parameter
            "class G1<T> { T t; }",
            // Generic field
            "class G2 { java.util.List<String> l; }",
            // Generic return type
            "class G3 { java.util.List<Integer> f() { return null; } }",
            // Generic method argument
            "class G4 { void m(java.util.List<Double> l) {} }",
            // Generic local variable
            "class G5 { void m() { java.util.List<Float> l = null; } }",
            // Wildcard generics
            "class G6 { void m(java.util.List<?> l) {} }",
            // Bounded generics
            "class G7<T extends Number> { T t; }",
            // Generic with multiple bounds
            "class G8<T extends Number & Comparable<T>> { T t; }",
            // Generic usage with Map
            "class G9 { java.util.Map<String, Integer> m; }"
        ));
        sources.put("raw types", List.of(
            // Raw type as field
            "class G1<T> {} class R1 { G1 g; }",
            // Raw type as return type
            "class G2<T> {} class R2 { G2 f() { return null; } }",
            // Raw type as method argument
            "class G3<T> {} class R3 { void m(G3 g) {} }",
            // Raw type as local variable
            "class G4<T> {} class R4 { void m() { G4 g = null; } }",
            // Raw type with collection
            "import java.util.List; class R5 { List l; }"
        ));
        sources.put("object casting", List.of(
            // Downcast in method body
            "class A1{} class B1 extends A1{} void f(A1 a){B1 b=(B1)a;}",
            // Upcast in method body
            "class A2{} class B2 extends A2{} void f(B2 b){A2 a=(A2)b;}",
            // Cast as field initialization
            "class A3{} class B3 extends A3{} class C1 { A3 a = (A3)new B3(); }",
            // Cast as return type
            "class A4{} class B4 extends A4{} class C2 { A4 f(B4 b) { return (A4)b; } }",
            // Cast as method argument
            "class A5{} class B5 extends A5{} class C3 { void m(A5 a) {} void f(B5 b) { m((A5)b); } }",
            // Cast as local variable
            "class A6{} class B6 extends A6{} class C4 { void m(A6 a) { B6 b = (B6)a; } }",
            // Casting with interfaces
            "interface I {} class D1 implements I {} void f(Object o) { I i = (I)o; }"
        ));
        sources.put("primitive casting", List.of(
            // Downcast double to int in method body
            "class C1 { void f(){ int x=(int)1.0; } }",
            // Upcast int to double in method body (implicit)
            "class C2 { void f(){ double d=1; } }",
            // Upcast int to long (implicit)
            "class C3 { void f(){ long l=1; } }",
            // Upcast float to double (implicit)
            "class C4 { void f(){ double d=1.0f; } }",
            // Upcast char to int (implicit)
            "class C5 { void f(){ int i='a'; } }",
            // Upcast short to int (implicit)
            "class C6 { void f(){ short s=1; int i=s; } }",
            // Upcast byte to int (implicit)
            "class C7 { void f(){ byte b=1; int i=b; } }",
            // Upcast int to float (implicit)
            "class C8 { void f(){ float f=1; } }",
            // Cast as field initialization
            "class C9 { float f = (float)42; }",
            // Cast as return type
            "class C10 { short f() { return (short)1000; } }",
            // Cast as method argument
            "class C11 { void m(byte b) {} void f() { m((byte)2); } }",
            // Cast as local variable
            "class C12 { void f() { long l = (long)3.14; } }",
            // Between different primitive types
            "class C13 { void f() { char c = (char)65; } }"
        ));
        sources.put("instance methods", List.of(
            // Simple instance method
            "class A1 { void m() {} }",
            // Instance method with return type
            "class A2 { int f() { return 1; } }",
            // Instance method with arguments
            "class A3 { void g(int x, String y) {} }",
            // Overloaded instance methods
            "class A4 { void h() {} void h(int x) {} }",
            // Overridden instance method
            "class A5 { void i() {} } class B1 extends A5 { void i() {} }",
            // Instance method as field (method reference)
            "import java.util.function.Supplier; class A6 { Supplier<Integer> s = this::f; int f() { return 1; } }",
            // Instance method as local variable (method reference)
            "import java.util.function.Supplier; class A7 { void m() { Supplier<Integer> s = this::f; } int f() { return 2; } }"
        ));
        sources.put("static methods", List.of(
            // Simple static method
            "class S1 { static void m() {} }",
            // Static method with return type
            "class S2 { static int f() { return 1; } }",
            // Static method with arguments
            "class S3 { static void g(int x, String y) {} }",
            // Overloaded static methods
            "class S4 { static void h() {} static void h(int x) {} }",
            // Static method hiding (not overriding)
            "class S5 { static void i() {} } class S6 extends S5 { static void i() {} }",
            // Static method as field (method reference)
            "import java.util.function.Supplier; class S7 { static int f() { return 1; } static Supplier<Integer> s = S7::f; }",
            // Static method as local variable (method reference)
            "import java.util.function.Supplier; class S8 { static int f() { return 2; } void m() { Supplier<Integer> s = S8::f; } }"
        ));
        sources.put("threads", List.of(
            // Subclassing Thread and starting
            "class T1 extends Thread{} void f(){ new T1().start(); }",
            // Implementing Runnable and starting
            "class R1 implements Runnable { public void run() {} } void f() { new Thread(new R1()).start(); }",
            // Thread with arguments (via constructor)
            "class T2 extends Thread { int x; T2(int x) { this.x = x; } public void run() {} } void f() { new T2(5).start(); }",
            // Anonymous Thread subclass
            "class T3 { void f() { new Thread() { public void run() {} }.start(); } }",
            // Anonymous Runnable
            "class R2 { void f() { new Thread(new Runnable() { public void run() {} }).start(); } }",
            // Lambda Runnable (Java 8+)
            "class L1 { void f() { new Thread(() -> {}).start(); } }"
        ));
        sources.put("synchronization", List.of(
            // Synchronized instance method
            "class S1 { synchronized void m(){} }",
            // Synchronized static method
            "class S2 { static synchronized void m(){} }",
            // Synchronized block on this
            "class S3 { void m() { synchronized(this) {} } }",
            // Synchronized block on field
            "class S4 { final Object lock = new Object(); void m() { synchronized(lock) {} } }",
            // Nested synchronized blocks
            "class S5 { void m() { synchronized(this) { synchronized(new Object()) {} } } }",
            // Synchronized block in static method
            "class S6 { static void m() { synchronized(S6.class) {} } }"
        ));
        sources.put("interleavings", List.of(
            // Two threads, one synchronized method
            "class T1 extends Thread{} class S1 { synchronized void m(){} } void f(){ new T1().start(); }",
            // Two threads, both with synchronized methods
            "class T2 extends Thread { synchronized void m(){} } class S2 extends Thread { synchronized void m(){} } void f(){ new T2().start(); new S2().start(); }",
            // Synchronized block on shared object
            "class S3 { final Object lock = new Object(); void m() { synchronized(lock) {} } } class T3 extends Thread { S3 s; public void run() { s.m(); } } void f() { S3 s = new S3(); new T3().start(); }",
            // Shared variable between threads
            "class Shared { int x = 0; synchronized void inc() { x++; } } class T4 extends Thread { Shared s; public void run() { s.inc(); } } void f() { Shared s = new Shared(); new T4().start(); new T4().start(); }",
            // Thread interaction with wait/notify
            "class Sync { synchronized void m() throws Exception { wait(); notify(); } } class T5 extends Thread { Sync s; public void run() { try { s.m(); } catch(Exception e){} } } void f() { Sync s = new Sync(); new T5().start(); }"
        ));
        sources.put("lambdas", List.of(
            // Lambda assigned to field
            "class L1 { Runnable r = () -> {}; }",
            // Lambda assigned to local variable
            "class L2 { void m() { Runnable r = () -> {}; } }",
            // Lambda as method argument
            "class L3 { void m(Runnable r) {} void f() { m(() -> {}); } }",
            // Lambda as return value
            "class L4 { Runnable f() { return () -> {}; } }",
            // Lambda with generics (Supplier)
            "import java.util.function.Supplier; class L5 { Supplier<Integer> s = () -> 42; }",
            // Lambda as Callable
            "import java.util.concurrent.Callable; class L6 { Callable<String> c = () -> \"hi\"; }"
        ));
        sources.put("Network and File I/O", List.of(
            // File as field
            "import java.io.File; class F1 { File f; }",
            // File as local variable
            "import java.io.File; class F2 { void m() { File f = new File(\"foo.txt\"); } }",
            // File as method argument
            "import java.io.File; class F3 { void m(File f) {} }",
            // File as return type
            "import java.io.File; class F4 { File f() { return null; } }",
            // FileInputStream usage
            "import java.io.FileInputStream; class F5 { void m() throws Exception { FileInputStream fis = new FileInputStream(\"foo.txt\"); } }",
            // FileOutputStream usage
            "import java.io.FileOutputStream; class F6 { void m() throws Exception { FileOutputStream fos = new FileOutputStream(\"foo.txt\"); } }",
            // FileReader usage
            "import java.io.FileReader; class F7 { void m() throws Exception { FileReader fr = new FileReader(\"foo.txt\"); } }",
            // FileWriter usage
            "import java.io.FileWriter; class F8 { void m() throws Exception { FileWriter fw = new FileWriter(\"foo.txt\"); } }",
            // Socket usage
            "import java.net.Socket; class F9 { void m() throws Exception { Socket s = new Socket(\"localhost\", 80); } }",
            // ServerSocket usage
            "import java.net.ServerSocket; class F10 { void m() throws Exception { ServerSocket ss = new ServerSocket(8080); } }"
        ));
        sources.put("GUI", List.of(
            // JButton as field
            "import javax.swing.JButton; class G1 { JButton b; }",
            // JButton as local variable
            "import javax.swing.JButton; class G2 { void m() { JButton b = new JButton(); } }",
            // JButton as method argument
            "import javax.swing.JButton; class G3 { void m(JButton b) {} }",
            // JButton as return type
            "import javax.swing.JButton; class G4 { JButton f() { return new JButton(); } }",
            // JFrame usage
            "import javax.swing.JFrame; class G5 { void m() { JFrame f = new JFrame(); } }",
            // JPanel usage
            "import javax.swing.JPanel; class G6 { void m() { JPanel p = new JPanel(); } }",
            // JLabel usage
            "import javax.swing.JLabel; class G7 { void m() { JLabel l = new JLabel(); } }",
            // Adding JButton to JFrame
            "import javax.swing.*; class G8 { void m() { JFrame f = new JFrame(); JButton b = new JButton(); f.add(b); } }"
        ));
        sources.put("RE Pattern Syntax", List.of(
            // Pattern as field
            "import java.util.regex.Pattern; class R1 { Pattern p; }",
            // Pattern as local variable
            "import java.util.regex.Pattern; class R2 { void m() { Pattern p = Pattern.compile(\"a.*b\"); } }",
            // Pattern as method argument
            "import java.util.regex.Pattern; class R3 { void m(Pattern p) {} }",
            // Pattern as return type
            "import java.util.regex.Pattern; class R4 { Pattern f() { return Pattern.compile(\"x\"); } }",
            // Using Pattern.compile
            "import java.util.regex.Pattern; class R5 { Pattern p = Pattern.compile(\"[0-9]+\"); }",
            // Using matcher
            "import java.util.regex.Pattern; class R6 { boolean m(String s) { return Pattern.compile(\"a.*b\").matcher(s).matches(); } }",
            // Using split
            "import java.util.regex.Pattern; class R7 { String[] m(String s) { return Pattern.compile(\",\").split(s); } }"
        ));
        sources.put("streams", List.of(
            // Stream as field
            "import java.util.stream.Stream; class S1 { Stream<String> s; }",
            // Stream as local variable
            "import java.util.stream.Stream; class S2 { void m() { Stream<Integer> s = Stream.of(1,2,3); } }",
            // Stream as method argument
            "import java.util.stream.Stream; class S3 { void m(Stream<Double> s) {} }",
            // Stream as return type
            "import java.util.stream.Stream; class S4 { Stream<String> f() { return Stream.of(\"a\"); } }",
            // Stream from collection
            "import java.util.*; import java.util.stream.*; class S5 { void m() { List<String> l = List.of(\"a\",\"b\"); Stream<String> s = l.stream(); } }",
            // Stream from array
            "import java.util.stream.Stream; class S6 { void m() { Stream<String> s = Stream.of(new String[]{\"a\",\"b\"}); } }",
            // Intermediate operation (map)
            "import java.util.stream.Stream; class S7 { void m() { Stream<Integer> s = Stream.of(1,2).map(x -> x+1); } }",
            // Terminal operation (collect)
            "import java.util.*; import java.util.stream.*; class S8 { void m() { List<Integer> l = Stream.of(1,2).toList(); } }",
            // Method reference in stream
            "import java.util.stream.Stream; class S9 { void m() { Stream<String> s = Stream.of(\"a\").map(String::toUpperCase); } }"
        ));
        sources.put("reflection", List.of(
            // Class.forName usage
            "class R1 { void f() { try { Class.forName(\"java.lang.String\"); } catch(Exception e){} } }",
            // getDeclaredMethods
            "class R2 { void f() { try { Class<?> c = String.class; c.getDeclaredMethods(); } catch(Exception e){} } }",
            // getFields
            "class R3 { void f() { try { Class<?> c = String.class; c.getFields(); } catch(Exception e){} } }",
            // setAccessible
            "class R4 { void f() { try { java.lang.reflect.Field f = String.class.getDeclaredFields()[0]; f.setAccessible(true); } catch(Exception e){} } }",
            // newInstance
            "class R5 { void f() { try { Object o = String.class.getConstructor().newInstance(); } catch(Exception e){} } }"
        ));
        sources.put("Math", List.of(
            // Math.abs in method
            "class M1 { void f() { Math.abs(-1); } }",
            // Math.max as field
            "class M2 { int m = Math.max(1, 2); }",
            // Math.min as local variable
            "class M3 { void f() { int x = Math.min(3, 4); } }",
            // Math.pow as method argument
            "class M4 { void f(double d) {} void g() { f(Math.pow(2, 3)); } }",
            // Math.sqrt as return type
            "class M5 { double f() { return Math.sqrt(16); } }",
            // Static import of Math
            "import static java.lang.Math.*; class M6 { void f() { sin(0); cos(0); } }",
            // Math.random usage
            "class M7 { double r = Math.random(); }"
        ));
    }

    public static List<String> getSourcesForFeature(String feature) {
        return sources.getOrDefault(feature, List.of("public class Dummy{} // No test source"));
    }
}
