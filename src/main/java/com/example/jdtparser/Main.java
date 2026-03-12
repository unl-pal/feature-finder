package com.example.jdtparser;

import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SwitchExpression;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static ASTParser getParser(String source, String[] sourcePath, String[] classPath, File file) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        parser.setBindingsRecovery(true);
        parser.setStatementsRecovery(true);
        Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, "1.8");
        parser.setCompilerOptions(options);
        parser.setUnitName(file.getPath());
        parser.setEnvironment(classPath, sourcePath, new String[] { "UTF-8", "UTF-8" }, true);

        return parser;
    }
    
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java -jar feature-finder.jar <JavaSourceFile.java>");
            return;
        }
        String inputSource = "argv-mhe";
        String[] classPath = {Paths.get("build", "classes", "java", "main").toString()};
        String[] sourcePath = { Paths.get(inputSource).toString() , Paths.get("src", "main", "java").toString()};
        String source = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
        ASTParser parser = getParser(source, sourcePath, classPath, Paths.get(args[0]).toFile());
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        java.util.Set<String> foundFeatures = new java.util.HashSet<>();
        FeatureVisitor visitor = new FeatureVisitor(foundFeatures);
        cu.accept(visitor);
        visitor.finalizeAnalysis();
        // Heuristic: if both threads and synchronization are present, flag possible interleavings
        if (foundFeatures.contains("threads") && foundFeatures.contains("synchronization")) {
            foundFeatures.add("interleavings");
        }
        System.out.println("Features found: " + foundFeatures);
    }

    static class FeatureVisitor extends ASTVisitor {

        private final Set<String> features;

        /* ---------- STATE ---------- */

        private int loopDepth = 0;

        private IMethodBinding currentMethod;
        private ITypeBinding currentClass;

        /* call graph for recursion detection */
        private final Map<IMethodBinding, Set<IMethodBinding>> callGraph = new HashMap<>();

        /* overridden methods */
        private final Set<IMethodBinding> overriddenMethods = new HashSet<>();


        public FeatureVisitor(Set<String> features) {
            this.features = features;
        }

        /* -------------------------------------------------- */
        /*                     UTILITIES                      */
        /* -------------------------------------------------- */

        private boolean isSubtypeOf(ITypeBinding type, String qname) {

            while (type != null) {

                if (qname.equals(type.getQualifiedName()))
                    return true;

                for (ITypeBinding iface : type.getInterfaces())
                    if (isSubtypeOf(iface, qname))
                        return true;

                type = type.getSuperclass();
            }

            return false;
        }

        private void detectPrimitive(ITypeBinding t) {

            if (t == null) return;

            if (t.isPrimitive()) {

                switch (t.getName()) {

                    case "byte":
                    case "short":
                    case "int":
                    case "long":
                    case "char":
                        features.add("integers");
                        break;

                    case "float":
                    case "double":
                        features.add("floats");
                        break;

                    case "boolean":
                        features.add("boolean");
                        break;
                }
            }

            if ("java.lang.String".equals(t.getQualifiedName()))
                features.add("strings");
        }

        private void detectCollections(ITypeBinding t) {

            if (t == null) return;

            if (isSubtypeOf(t, "java.util.Map"))
                features.add("maps");

            else if (isSubtypeOf(t, "java.util.Collection"))
                features.add("collections");
        }

        private void detectIO(ITypeBinding t) {

            if (t == null) return;

            String q = t.getQualifiedName();

            if (q.startsWith("java.io")
                    || q.startsWith("java.nio")
                    || q.startsWith("java.net"))
                features.add("Network and File I/O");
        }

        private void detectGUI(ITypeBinding t) {

            if (t == null) return;

            String q = t.getQualifiedName();

            if (q.startsWith("javax.swing")
                    || q.startsWith("java.awt")
                    || q.startsWith("javafx"))
                features.add("GUI");
        }

        private void detectStreams(IMethodBinding m) {

            if (m == null) return;

            ITypeBinding t = m.getDeclaringClass();

            if (t != null && t.getQualifiedName().startsWith("java.util.stream"))
                features.add("streams");
        }

        private void detectRegex(IMethodBinding m) {

            if (m == null) return;

            ITypeBinding t = m.getDeclaringClass();

            if (t != null && t.getQualifiedName().startsWith("java.util.regex"))
                features.add("RE Pattern Syntax");
        }

        private void detectReflection(IMethodBinding m) {

            if (m == null) return;

            ITypeBinding t = m.getDeclaringClass();

            if (t != null && t.getQualifiedName().startsWith("java.lang.reflect"))
                features.add("reflection");
        }

        private void detectMath(IMethodBinding m) {

            if (m == null) return;

            ITypeBinding t = m.getDeclaringClass();

            if (t != null && "java.lang.Math".equals(t.getQualifiedName()))
                features.add("Math");
        }


        /* -------------------------------------------------- */
        /*                  CONTROL FLOW                      */
        /* -------------------------------------------------- */

        private void enterLoop() {
            features.add("loops");
            if (loopDepth > 0)
                features.add("nested loops");
            loopDepth++;
        }

        private void exitLoop() {
            loopDepth--;
        }

        @Override
        public boolean visit(ForStatement node) {
            enterLoop();
            return true;
        }

        @Override
        public void endVisit(ForStatement node) {
            exitLoop();
        }

        @Override
        public boolean visit(WhileStatement node) {
            enterLoop();
            return true;
        }

        @Override
        public void endVisit(WhileStatement node) {
            exitLoop();
        }

        @Override
        public boolean visit(DoStatement node) {
            enterLoop();
            return true;
        }

        @Override
        public void endVisit(DoStatement node) {
            exitLoop();
        }

        @Override
        public boolean visit(IfStatement node) {
            features.add("conditionals");
            return true;
        }

        @Override
        public boolean visit(ConditionalExpression node) {
            features.add("conditionals");
            return true;
        }

        @Override
        public boolean visit(SwitchStatement node) {
            features.add("switch statements");
            return true;
        }

        @Override
        public boolean visit(SwitchExpression node) {
            features.add("switch statements");
            return true;
        }

        /* -------------------------------------------------- */
        /*                     METHODS                        */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(MethodDeclaration node) {

            currentMethod = node.resolveBinding();

            if (currentMethod != null) {

                if (Modifier.isStatic(currentMethod.getModifiers()))
                    features.add("static methods");
                else
                    features.add("instance methods");

                /* detect overriding */
                ITypeBinding superType = currentMethod.getDeclaringClass().getSuperclass();

                while (superType != null) {

                    for (IMethodBinding m : superType.getDeclaredMethods()) {

                        if (currentMethod.overrides(m)) {
                            overriddenMethods.add(currentMethod.getMethodDeclaration());
                        }
                    }

                    superType = superType.getSuperclass();
                }
            }

            return true;
        }

        @Override
        public void endVisit(MethodDeclaration node) {
            currentMethod = null;
        }

        /* -------------------------------------------------- */
        /*                    INVOCATIONS                     */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(MethodInvocation node) {

            IMethodBinding target = node.resolveMethodBinding();

            if (target == null) return true;

            /* build call graph */

            if (currentMethod != null) {

                callGraph
                    .computeIfAbsent(currentMethod.getMethodDeclaration(),
                            k -> new HashSet<>())
                    .add(target.getMethodDeclaration());
            }

            /* polymorphism detection */

            Expression expr = node.getExpression();

            if (expr != null) {

                ITypeBinding declaredType = expr.resolveTypeBinding();
                ITypeBinding runtimeType = target.getDeclaringClass();

                if (declaredType != null
                        && runtimeType != null
                        && !declaredType.equals(runtimeType)
                        && overriddenMethods.contains(target.getMethodDeclaration()))
                    features.add("polymorphism");
            }

            /* libraries */

            detectStreams(target);
            detectRegex(target);
            detectReflection(target);
            detectMath(target);

            return true;
        }

        /* -------------------------------------------------- */
        /*                   EXCEPTIONS                       */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(TryStatement node) {
            features.add("exception-based control flow");
            return true;
        }

        @Override
        public boolean visit(ThrowStatement node) {
            features.add("exception-based control flow");
            return true;
        }

        /* -------------------------------------------------- */
        /*                    ARRAYS                          */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(ArrayCreation node) {
            features.add("arrays");
            return true;
        }

        @Override
        public boolean visit(ArrayAccess node) {
            features.add("arrays");
            return true;
        }

        /* -------------------------------------------------- */
        /*                TYPE DECLARATIONS                   */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(TypeDeclaration node) {

            currentClass = node.resolveBinding();

            if (node.getSuperclassType() != null)
                features.add("inheritance");

            if (node.superInterfaceTypes().size() > 0)
                features.add("interfaces");

            if (node.isInterface())
                features.add("interfaces");

            if (Modifier.isAbstract(node.getModifiers()))
                features.add("abstract classes");

            return true;
        }

        @Override
        public boolean visit(FieldDeclaration node) {

            if (currentClass == null) return true;

            ITypeBinding fieldType = node.getType().resolveBinding();

            if (fieldType != null && fieldType.equals(currentClass))
                features.add("recursive data structures");

            return true;
        }

        /* -------------------------------------------------- */
        /*                   TYPES                            */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(VariableDeclarationFragment node) {

            if (node.resolveBinding() != null) {

                ITypeBinding t = node.resolveBinding().getType();

                detectPrimitive(t);
                detectCollections(t);
                detectIO(t);
                detectGUI(t);
            }

            return true;
        }

        @Override
        public boolean visit(ClassInstanceCreation node) {

            ITypeBinding t = node.resolveTypeBinding();

            detectCollections(t);
            detectIO(t);
            detectGUI(t);

            if (isSubtypeOf(t, "java.lang.Thread")
                    || isSubtypeOf(t, "java.lang.Runnable"))
                features.add("threads");

            return true;
        }

        /* -------------------------------------------------- */
        /*                  ENUMS                             */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(EnumDeclaration node) {
            features.add("enumerations");
            return true;
        }

        /* -------------------------------------------------- */
        /*                 GENERICS                           */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(ParameterizedType node) {
            features.add("generics");
            return true;
        }

        @Override
        public boolean visit(SimpleType node) {

            ITypeBinding b = node.resolveBinding();

            if (b != null && b.isGenericType())
                features.add("raw types");

            return true;
        }

        /* -------------------------------------------------- */
        /*                    CASTS                           */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(CastExpression node) {

            ITypeBinding t = node.getType().resolveBinding();

            if (t == null) return true;

            if (t.isPrimitive())
                features.add("primitive casting");
            else
                features.add("object casting");

            return true;
        }

        /* -------------------------------------------------- */
        /*             LAMBDAS / ANONYMOUS                    */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(LambdaExpression node) {
            features.add("lambdas");
            return true;
        }

        @Override
        public boolean visit(AnonymousClassDeclaration node) {
            features.add("anonymous classes");
            return true;
        }

        /* -------------------------------------------------- */
        /*              SYNCHRONIZATION                       */
        /* -------------------------------------------------- */

        @Override
        public boolean visit(SynchronizedStatement node) {
            features.add("synchronization");
            return true;
        }

        /* -------------------------------------------------- */
        /*             RECURSION (post-pass)                  */
        /* -------------------------------------------------- */

        public void finalizeAnalysis() {

            for (IMethodBinding m : callGraph.keySet()) {

                if (isRecursive(m, new HashSet<>()))
                    features.add("recursion");
            }
        }

        private boolean isRecursive(IMethodBinding start,
                                    Set<IMethodBinding> visited) {

            if (!visited.add(start))
                return true;

            Set<IMethodBinding> calls = callGraph.get(start);

            if (calls == null) return false;

            for (IMethodBinding c : calls)
                if (isRecursive(c, visited))
                    return true;

            visited.remove(start);
            return false;
        }
    }

}
