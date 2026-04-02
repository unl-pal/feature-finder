package edu.unl.pal.featurefinder.tests;

import java.util.HashMap;
import java.util.Map;

public class FeatureTestSources {
    private static final Map<String, String> sources = new HashMap<>();

    static {
        sources.put("loops", "public class Loops { void m() { for(int i=0;i<1;i++){} } }");
        sources.put("nested loops", "public class NestedLoops { void m() { for(int i=0;i<1;i++){ while(false){} } } }");
        sources.put("conditionals", "public class Cond { void m() { if(true){} } }");
        sources.put("switch statements", "public class Switch { void m() { switch(1) { case 1: break; } } }");
        sources.put("recursion", "public class Rec { int f(int n){ return n==0?1:f(n-1); } }");
        sources.put("exception-based control flow", "public class Ex { void m() { try{}catch(Exception e){} } }");
        sources.put("arrays", "public class Arr { int[] a = new int[1]; }");
        sources.put("collections", "import java.util.List; public class Coll { List<Integer> l; }");
        sources.put("maps", "import java.util.Map; public class MapTest { Map<String,Integer> m; }");
        sources.put("recursive data structures", "public class RecDS { RecDS next; }");
        sources.put("integers", "public class Ints { int x = 1; }");
        sources.put("floats", "public class Floats { float f = 1.0f; }");
        sources.put("boolean", "public class Bool { boolean b = true; }");
        sources.put("enumerations", "public enum E { A, B }");
        sources.put("strings", "public class Str { String s = \"hi\"; }");
        sources.put("inheritance", "class A{} class B extends A{}");
        sources.put("interfaces", "interface I{} class C implements I{}");
        sources.put("anonymous classes", "class C { Runnable r = new Runnable(){ public void run(){} }; }");
        sources.put("abstract classes", "abstract class A{} class B extends A{}");
        sources.put("polymorphism", "class C { class A{void m(){}} class B extends A{void m(){}} void f(A a){a.m();} }");
        sources.put("generics", "class G<T>{ G<String> g;}");
        sources.put("raw types", "class G<T>{} class R { G g; }");
        sources.put("object casting", "class A{} class B extends A{} void f(A a){B b=(B)a;}");
        sources.put("primitive casting", "class C { void f(){ int x=(int)1.0; } }");
        sources.put("instance methods", "class A{void m(){}}");
        sources.put("static methods", "class A{static void m(){}}");
        sources.put("threads", "class T extends Thread{} void f(){ new T().start(); }");
        sources.put("synchronization", "class S { synchronized void m(){} }");
        sources.put("interleavings", "class T extends Thread{} class S { synchronized void m(){} } void f(){ new T().start(); }");
        sources.put("lambdas", "class C { Runnable r = () -> {}; }");
        sources.put("Network and File I/O", "import java.io.File; class F { File f; }");
        sources.put("GUI", "import javax.swing.JButton; class G { JButton b; }");
        sources.put("RE Pattern Syntax", "import java.util.regex.Pattern; class R { Pattern p; }");
        sources.put("streams", "import java.util.stream.Stream; class S { Stream<String> s; }");
        sources.put("reflection", "class R { void f() { try { Class.forName(\"java.lang.String\"); } catch(Exception e){} } }");
        sources.put("Math", "class M { void f() { Math.abs(-1); } }");
    }

    public static String getSourceForFeature(String feature) {
        return sources.getOrDefault(feature, "public class Dummy{} // No test source");
    }
}
