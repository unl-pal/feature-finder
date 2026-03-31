package edu.unl.pal.featurefinder;

import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayType;
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
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SwitchExpression;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.JavaCore;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Main {
    public static ASTParser getParser(String source, String[] sourcePath, String[] classPath, File file) {
        @SuppressWarnings("deprecation")
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(source.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        Map<String, String> options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, "1.8");
        parser.setCompilerOptions(options);

        parser.setResolveBindings(true);
        parser.setBindingsRecovery(true);
        parser.setStatementsRecovery(true);
        parser.setUnitName(file.getPath());
        parser.setEnvironment(classPath, sourcePath, new String[] { "UTF-8", "UTF-8" }, true);

        return parser;
    }

    public static void main(String[] args) throws Exception {
        String inputSource = ".";

        String[] classPath = {
            Paths.get("build", "classes", "java", "main").toString()
        };

        String[] sourcePath = {
            Paths.get(inputSource).toString(),
            Paths.get("src", "main", "java").toString()
        };

        String[] featureOrder = {
            "loops",
            "nested loops",
            "conditionals",
            "switch statements",
            "recursion",
            "exception-based control flow",
            "arrays",
            "collections",
            "maps",
            "recursive data structures",
            "integers",
            "floats",
            "boolean",
            "enumerations",
            "strings",
            "inheritance",
            "interfaces",
            "anonymous classes",
            "abstract classes",
            "polymorphism",
            "generics",
            "raw types",
            "object casting",
            "primitive casting",
            "instance methods",
            "static methods",
            "threads",
            "synchronization",
            "interleavings",
            "lambdas",
            "Network and File I/O",
            "GUI",
            "RE Pattern Syntax",
            "streams",
            "reflection",
            "Math"
        };

        Path root = Paths.get(inputSource);

        List<Path> files = new ArrayList<>();

        System.out.println("Scanning for Java source files in '" + root + "'");
        Files.walk(root)
            .filter(p -> p.toString().endsWith(".java"))
            .filter(p -> p.toFile().isFile())
            .forEach(files::add);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("features.csv"))) {
            /* write header */
            writer.write("filename");
            for (String f : featureOrder)
                writer.write("," + f);
            writer.newLine();

            /* process each file */
            for (Path file : files) {
                System.out.println("Analyzing " + file);
                String source = new String(
                    Files.readAllBytes(file),
                    StandardCharsets.UTF_8
                );

                ASTParser parser = getParser(source, sourcePath, classPath, file.toFile());

                CompilationUnit cu = (CompilationUnit) parser.createAST(null);

                Set<String> foundFeatures = new HashSet<>();

                FeatureVisitor visitor = new FeatureVisitor(foundFeatures);
                cu.accept(visitor);
                visitor.finalizeAnalysis();

                /* heuristic interleavings */
                if (foundFeatures.contains("threads") && foundFeatures.contains("synchronization"))
                    foundFeatures.add("interleavings");

                /* write row */
                writer.write(file.toString());

                for (String feature : featureOrder) {
                    if (foundFeatures.contains(feature))
                        writer.write(",YES");
                    else
                        writer.write(",NO");
                }

                writer.newLine();
            }
        }

        System.out.println("Analysis complete. Results written to features.csv");
    }

    static class FeatureVisitor extends ASTVisitor {
        private final Set<String> features;

        /* ---------- STATE ---------- */

        private int loopDepth = 0;

        private final Stack<IMethodBinding> currentMethodStack = new Stack<>();
        private final Stack<ITypeBinding> currentClassStack = new Stack<>();

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

        private boolean isSubtypeOf(ITypeBinding t, String qname) {
            while (t != null) {
                t = t.getErasure();
                if (qname.equals(t.getQualifiedName()))
                    return true;

                for (ITypeBinding iface : t.getInterfaces())
                    if (isSubtypeOf(iface, qname))
                        return true;

                t = t.getSuperclass();
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
            
			String q = t.getQualifiedName();
            
            /* primitive wrappers */
            switch (q) {
                case "java.lang.Byte":
                case "java.lang.Short":
                case "java.lang.Integer":
                case "java.lang.Long":
                case "java.lang.Character":
                    features.add("integers");
                    break;

                case "java.lang.Float":
                case "java.lang.Double":
                    features.add("floats");
                    break;

                case "java.lang.Boolean":
                    features.add("boolean");
                    break;
            }

            if ("java.lang.String".equals(q))
                features.add("strings");
        }

        private void detectCollections(ITypeBinding t) {
            if (t == null) return;

            if (isSubtypeOf(t, "java.util.Map"))
                features.add("maps");
            else if (isSubtypeOf(t, "java.util.Collection"))
                features.add("collections");
        }

        private void detectEnum(ITypeBinding t) {
            if (t == null) return;

            if (t.isEnum())
                features.add("enumerations");
        }

        private void detectArray(ITypeBinding t) {
            if (t == null) return;

            if (t.isArray())
                features.add("arrays");
        }

        private void detectIO(ITypeBinding t) {
            if (t == null) return;

            String q = t.getQualifiedName();

            if (q.startsWith("java.io.")
                    || q.startsWith("java.nio.")
                    || q.startsWith("java.net."))
                features.add("Network and File I/O");
        }

        private void detectGUI(ITypeBinding t) {
            if (t == null) return;

            String q = t.getQualifiedName();

            if (q.startsWith("javax.swing.")
                    || q.startsWith("java.awt.")
                    || q.startsWith("javafx."))
                features.add("GUI");
        }

        private void detectSynchronization(ITypeBinding t) {
            if (t == null) return;

            String q = t.getQualifiedName();

            if (q.startsWith("java.util.concurrent.locks.") || q.startsWith("java.util.concurrent.atomic."))
                features.add("synchronization");
        }

        private void detectStreams(IMethodBinding m) {
            if (m == null) return;

            detectStreams(m.getDeclaringClass());
        }

        private void detectStreams(ITypeBinding t) {
            if (t == null) return;

            if (t.getQualifiedName().startsWith("java.util.stream."))
                features.add("streams");
        }

        private void detectRegex(IMethodBinding m) {
            if (m == null) return;

            detectRegex(m.getDeclaringClass());
        }

        private void detectRegex(ITypeBinding t) {
            if (t == null) return;

            if (t.getQualifiedName().startsWith("java.util.regex."))
                features.add("RE Pattern Syntax");
        }

        private void detectThreading(ITypeBinding t) {
            if (t == null) return;

            if (isSubtypeOf(t, "java.lang.Thread") || isSubtypeOf(t, "java.lang.Runnable"))
                features.add("threads");
        }

        private static final Set<String> CLASS_REFLECTION_METHODS = Set.of(
            "forName",
            "newInstance",
            "getMethod",
            "getDeclaredMethod",
            "getMethods",
            "getDeclaredMethods",
            "getField",
            "getDeclaredField",
            "getFields",
            "getDeclaredFields",
            "getConstructor",
            "getDeclaredConstructor",
            "getConstructors",
            "getDeclaredConstructors",
            "getAnnotation",
            "getAnnotations"
        );

        private void detectReflection(IMethodBinding m) {
            if (m == null) return;

            ITypeBinding t = m.getDeclaringClass();
            if (t == null) return;

            if (isSubtypeOf(t, "java.lang.Class") && CLASS_REFLECTION_METHODS.contains(m.getName()))
                features.add("reflection");
            else if (isSubtypeOf(t, "java.lang.ClassLoader") && m.getName().equals("loadClass"))
                features.add("reflection");
            else
                detectReflection(t);
        }

        private void detectReflection(ITypeBinding t) {
            if (t == null) return;

            if (t.getQualifiedName().startsWith("java.lang.reflect."))
                features.add("reflection");
        }

        private void detectMath(IMethodBinding m) {
            if (m == null) return;

            ITypeBinding t = m.getDeclaringClass();
            if (t == null) return;

            if ("java.lang.Math".equals(t.getQualifiedName()))
                features.add("Math");
        }

        private void detectTypeFeatures(IMethodBinding m) {
            if (m == null) return;

            detectTypeFeatures(m.getReturnType());
            for (ITypeBinding t : m.getTypeArguments())
                detectTypeFeatures(t);
            for (ITypeBinding t : m.getParameterTypes())
                detectTypeFeatures(t);

            detectMath(m);
            detectRegex(m);
            detectReflection(m);
            detectStreams(m);
        }

        private void detectTypeFeatures(ITypeBinding t) {
            if (t == null) return;

            detectPrimitive(t);
            detectCollections(t);
            detectEnum(t);
            detectArray(t);
            detectIO(t);
            detectGUI(t);
            detectSynchronization(t);
            detectRegex(t);
            detectReflection(t);
            detectStreams(t);
            detectThreading(t);
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
            IMethodBinding methodBinding = node.resolveBinding();

            if (Modifier.isSynchronized(node.getModifiers()))
                features.add("synchronization");

            if (methodBinding != null) {
                currentMethodStack.push(methodBinding);

                if (!"main".equals(methodBinding.getName())) {
                    detectTypeFeatures(methodBinding);
                    if (Modifier.isStatic(methodBinding.getModifiers()))
                        features.add("static methods");
                    else if (!methodBinding.isConstructor())
                        features.add("instance methods");
                }

                /* detect overriding */
                ITypeBinding superType = methodBinding.getDeclaringClass().getSuperclass();

                while (superType != null) {
                    for (IMethodBinding m : superType.getDeclaredMethods())
                        if (methodBinding.overrides(m))
                            overriddenMethods.add(methodBinding.getMethodDeclaration());

                    superType = superType.getSuperclass();
                }
            }

            return true;
        }

        @Override
        public void endVisit(MethodDeclaration node) {
            if (!currentMethodStack.isEmpty()) {
                currentMethodStack.pop();
            }
        }

        /* -------------------------------------------------- */
        /*                    INVOCATIONS                     */
        /* -------------------------------------------------- */
        @Override
        public boolean visit(MethodInvocation node) {
            IMethodBinding target = node.resolveMethodBinding();
            if (target == null) return true;

            detectTypeFeatures(target);

            /* build call graph */
            if (!currentMethodStack.isEmpty()) {
                callGraph
                    .computeIfAbsent(currentMethodStack.peek().getMethodDeclaration(),
                            k -> new HashSet<>())
                    .add(target.getMethodDeclaration());
            }

            /* polymorphism detection */
            Expression expr = node.getExpression();

            if (expr != null) {
                ITypeBinding declaredType = expr.resolveTypeBinding();

                if (declaredType != null) {

                    IMethodBinding decl = target.getMethodDeclaration();

                    /* method must belong to the declared type hierarchy */
                    ITypeBinding owner = decl.getDeclaringClass();

                    if (owner != null && declaredType.isAssignmentCompatible(owner)) {

                        /* ensure the receiver is NOT typed as the concrete class */
                        if (!declaredType.equals(owner)) {

                            /* check that this method participates in overriding */
                            if (overriddenMethods.contains(decl))
                                features.add("polymorphism");
                        }
                    }
                }
            }
            
            /* subtype polymorphism through parameter passing */
            List<?> args = node.arguments();
            ITypeBinding[] params = target.getParameterTypes();

            for (int i = 0; i < args.size() && i < params.length; i++) {

                Expression arg = (Expression) args.get(i);

                ITypeBinding argType = arg.resolveTypeBinding();
                ITypeBinding paramType = params[i];

                if (argType != null && paramType != null) {

                    /* argument is a subtype of parameter */
                    if (!argType.equals(paramType) && argType.isSubTypeCompatible(paramType)) {
                        features.add("polymorphism");
                        break;
                    }
                }
            }

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

        @Override
        public boolean visit(CatchClause node) {
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
            ITypeBinding currentClass = node.resolveBinding();
            currentClassStack.push(currentClass);

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
        public void endVisit(TypeDeclaration node) {
            if (!currentClassStack.isEmpty()) {
                currentClassStack.pop();
            }
        }

        @Override
        public boolean visit(FieldDeclaration node) {
            if (currentClassStack.isEmpty()) return true;

            ITypeBinding fieldType = node.getType().resolveBinding();

            if (fieldType != null && fieldType.equals(currentClassStack.peek()))
                features.add("recursive data structures");

            return true;
        }

        /* -------------------------------------------------- */
        /*                   TYPES                            */
        /* -------------------------------------------------- */
        @Override
        public boolean visit(VariableDeclarationFragment node) {
            if (node.resolveBinding() != null) {
                ITypeBinding type = node.resolveBinding().getType();

                detectTypeFeatures(type);

                if (type != null && type.isRawType())
                    features.add("raw types");
            }

            return true;
        }
        @Override
        public boolean visit(ClassInstanceCreation node) {
            detectTypeFeatures(node.resolveTypeBinding());

            return true;
        }
        
        @Override
        public boolean visit(StringLiteral node) {
            features.add("strings");
            return true;
        }
        
        @Override
        public boolean visit(ArrayType node) {
            /* check if this array type belongs to a method parameter */
            if (node.getParent() instanceof SingleVariableDeclaration) {

                SingleVariableDeclaration param = (SingleVariableDeclaration) node.getParent();

                if (param.getParent() instanceof MethodDeclaration) {

                    MethodDeclaration method = (MethodDeclaration) param.getParent();

                    if ("main".equals(method.getName().getIdentifier()))
                        return true; // skip String[] args
                }
            }
            features.add("arrays");
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
            if (b == null) return true;

            if (b.isRawType())
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
                if (isRecursive(m, new HashSet<>())) {
                    features.add("recursion");
                    break;
                }
            }
        }

        private boolean isRecursive(IMethodBinding start, Set<IMethodBinding> visited) {
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
