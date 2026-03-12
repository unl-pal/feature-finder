package com.example.jdtparser;

import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

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
        cu.accept(new SimpleVisitor(foundFeatures));
        // Heuristic: if both threads and synchronization are present, flag possible interleavings
        if (foundFeatures.contains("threads") && foundFeatures.contains("synchronization")) {
            foundFeatures.add("interleavings");
        }
        System.out.println("Features found: " + foundFeatures);
    }

    static class SimpleVisitor extends ASTVisitor {
        private final java.util.Set<String> foundFeatures;
        private int loopDepth = 0;
        // Track the current method name for recursion detection
        private String currentMethod = null;

        public SimpleVisitor(java.util.Set<String> foundFeatures) {
            this.foundFeatures = foundFeatures;
        }

        // Detect generics in variable, field, parameter declarations, and instantiations
        private boolean isGenericType(org.eclipse.jdt.core.dom.Type type) {
            return type instanceof ParameterizedType;
        }

        private boolean isFileIOType(String typeName) {
            return typeName.equals("File") || typeName.equals("FileReader") ||
                typeName.equals("FileWriter") || typeName.equals("BufferedReader") ||
                typeName.equals("BufferedWriter") || typeName.equals("InputStream") ||
                typeName.equals("OutputStream") || typeName.equals("FileInputStream") ||
                typeName.equals("FileOutputStream") || typeName.equals("RandomAccessFile") ||
                typeName.equals("PrintWriter") || typeName.equals("FileChannel") ||
                typeName.equals("Path") || typeName.equals("Files") ||
                typeName.equals("DirectoryStream") || typeName.equals("SeekableByteChannel");
        }

        private boolean isThreadType(String typeName) {
            return typeName.equals("Thread") || typeName.equals("Runnable") ||
                typeName.equals("Callable") || typeName.equals("Future") ||
                typeName.equals("Executor") || typeName.equals("ExecutorService") ||
                typeName.equals("ScheduledExecutorService") || typeName.equals("ForkJoinPool") ||
                typeName.equals("ThreadPoolExecutor") || typeName.equals("CompletionService") ||
                typeName.equals("FutureTask") || typeName.equals("CountDownLatch") ||
                typeName.equals("CyclicBarrier") || typeName.equals("Semaphore") ||
                typeName.equals("BlockingQueue") || typeName.equals("SynchronousQueue") ||
                typeName.equals("LinkedBlockingQueue") || typeName.equals("ArrayBlockingQueue");
        }

        // Detect use of java.util.concurrent.locks types in variable, field, and parameter declarations
        private boolean isLockType(String typeName) {
            return typeName.startsWith("Lock") || typeName.startsWith("ReentrantLock") ||
                typeName.startsWith("ReadWriteLock") || typeName.startsWith("StampedLock") ||
                typeName.startsWith("Condition") || typeName.startsWith("Semaphore") ||
                typeName.startsWith("CountDownLatch") || typeName.startsWith("CyclicBarrier");
        }

        private boolean isInterfaceType(String typeName) {
            // Heuristic: common Java interfaces, can be extended for custom interfaces
            return typeName.equals("Runnable") || typeName.equals("Serializable") ||
                typeName.equals("Cloneable") || typeName.equals("Comparable") ||
                typeName.equals("Iterable") || typeName.equals("List") ||
                typeName.equals("Set") || typeName.equals("Map") ||
                typeName.equals("Queue") || typeName.equals("Collection") ||
                typeName.endsWith("Listener") || typeName.endsWith("able") ||
                typeName.endsWith("er");
        }

        private boolean isMapType(String typeName) {
            return typeName.startsWith("Map") || typeName.startsWith("HashMap") ||
                typeName.startsWith("TreeMap") || typeName.startsWith("LinkedHashMap") ||
                typeName.startsWith("ConcurrentHashMap") || typeName.startsWith("WeakHashMap") ||
                typeName.startsWith("IdentityHashMap") || typeName.startsWith("CustomMap");
        }

        private boolean isCollectionType(String typeName) {
            return typeName.startsWith("List") || typeName.startsWith("Set") || typeName.startsWith("Queue") ||
                typeName.startsWith("Deque") || typeName.startsWith("Collection") ||
                typeName.startsWith("ArrayList") || typeName.startsWith("LinkedList") ||
                typeName.startsWith("HashSet") || typeName.startsWith("TreeSet") ||
                typeName.startsWith("PriorityQueue") || typeName.startsWith("Vector") ||
                typeName.startsWith("Stack") || typeName.startsWith("CopyOnWriteArrayList") ||
                typeName.startsWith("CopyOnWriteArraySet") || typeName.startsWith("ConcurrentLinkedQueue") ||
                typeName.startsWith("ConcurrentLinkedDeque") || typeName.startsWith("CustomCollection");
        }

        private boolean isRegexType(String typeName) {
            return typeName.equals("Pattern") || typeName.equals("Matcher") ||
                typeName.equals("PatternSyntaxException") || typeName.equals("MatchResult");
        }

        private boolean isGUIType(String typeName) {
            return typeName.startsWith("J") || // Swing components: JFrame, JButton, etc.
                typeName.startsWith("AWT") || // AWT components (rare, but for completeness)
                typeName.equals("Component") || typeName.equals("Container") ||
                typeName.equals("Window") || typeName.equals("Panel") ||
                typeName.equals("Applet") || typeName.equals("Dialog") ||
                typeName.equals("Canvas") || typeName.equals("Graphics") ||
                typeName.equals("Scene") || typeName.equals("Stage") || // JavaFX
                typeName.equals("Node") || typeName.equals("Control") ||
                typeName.equals("Application");
        }

        private boolean isRawType(org.eclipse.jdt.core.dom.Type type) {
            // Only SimpleType (not ParameterizedType) and matches known generic types
            if (type instanceof SimpleType) {
                String name = ((SimpleType) type).getName().getFullyQualifiedName();
                // List of common generic types
                if (name.equals("List") || name.equals("Set") || name.equals("Map") || name.equals("Queue") ||
                    name.equals("Collection") || name.equals("Optional") || name.equals("Iterator") ||
                    name.equals("Comparable") || name.equals("Future") || name.equals("Callable") ||
                    name.equals("Supplier") || name.equals("Consumer") || name.equals("Function")) {
                    return true;
                }
            }
            return false;
        }

        private void checkCollectionOrMapType(String typeName) {
            if (isCollectionType(typeName)) {
                foundFeatures.add("collections");
            }
            if (isMapType(typeName)) {
                foundFeatures.add("maps");
            }
        }

        @Override
        public boolean visit(CastExpression node) {
            foundFeatures.add("object casting");
            return super.visit(node);
        }

        @Override
        public boolean visit(TryStatement node) {
            foundFeatures.add("exception-based control flow");
            return super.visit(node);
        }

        @Override
        public boolean visit(CatchClause node) {
            foundFeatures.add("exception-based control flow");
            return super.visit(node);
        }

        @Override
        public boolean visit(ThrowStatement node) {
            foundFeatures.add("exception-based control flow");
            return super.visit(node);
        }

        @Override
        public boolean visit(SynchronizedStatement node) {
            foundFeatures.add("synchronization");
            return super.visit(node);
        }

        @Override
        public boolean visit(MethodDeclaration node) {
            if (node.modifiers().toString().contains("static")) {
                foundFeatures.add("static methods");
            } else {
                foundFeatures.add("instance methods");
            }
            if (node.typeParameters() != null && !node.typeParameters().isEmpty()) {
                foundFeatures.add("generics");
            }

            // Set current method name for recursion detection
            currentMethod = node.getName().getIdentifier();
            boolean result = super.visit(node);
            currentMethod = null;
            return result;
        }

        @Override
        public boolean visit(MethodInvocation node) {
            // Heuristic: if the method is called via a type name, assume it's a static method
            if (node.getExpression() != null && node.getExpression().toString().matches("[A-Z][A-Za-z0-9_]*")) {
                foundFeatures.add("static methods");
            } else {
                foundFeatures.add("instance methods");
            }
            // Detect direct recursion
            if (currentMethod != null && node.getName().getIdentifier().equals(currentMethod)) {
                foundFeatures.add("recursion");
            }
            return super.visit(node);
        }

        @Override
        public boolean visit(TypeDeclaration node) {
            if (node.isInterface()) {
                foundFeatures.add("interfaces");
            } else {
                foundFeatures.add("class");
            }
            String className = node.getName().getIdentifier();
            // Check for self-referential fields
            for (FieldDeclaration field : node.getFields()) {
                String typeName = field.getType().toString();
                if (typeName.equals(className)) {
                    foundFeatures.add("recursive data structures");
                    break;
                }
            }
            // Check for inheritance (extends or implements)
            if (node.getSuperclassType() != null ||
                (node.superInterfaceTypes() != null && !node.superInterfaceTypes().isEmpty())) {
                foundFeatures.add("inheritance");
            }
            // Check for implements (interfaces)
            if (node.superInterfaceTypes() != null && !node.superInterfaceTypes().isEmpty()) {
                foundFeatures.add("interfaces");
            }
            // Check for generics in class declaration
            if (node.typeParameters() != null && !node.typeParameters().isEmpty()) {
                foundFeatures.add("generics");
            }
            return super.visit(node);
        }

        @Override
        public boolean visit(VariableDeclarationStatement node) {
            checkCollectionOrMapType(node.getType().toString());
            if (isInterfaceType(node.getType().toString())) {
                foundFeatures.add("interfaces");
            }
            if (isLockType(node.getType().toString())) {
                foundFeatures.add("synchronization");
            }
            if (isThreadType(node.getType().toString())) {
                foundFeatures.add("threads");
            }
            if (isRawType(node.getType())) {
                foundFeatures.add("raw types");
            }
            if (isFileIOType(node.getType().toString())) {
                foundFeatures.add("File I/O");
            }
            if (isGenericType(node.getType())) {
                foundFeatures.add("generics");
            }
            if (isRegexType(node.getType().toString())) {
                foundFeatures.add("RE Pattern Syntax");
            }
            if (isGUIType(node.getType().toString())) {
                foundFeatures.add("GUI");
            }
            return super.visit(node);
        }

        @Override
        public boolean visit(SingleVariableDeclaration node) {
            checkCollectionOrMapType(node.getType().toString());
            if (isInterfaceType(node.getType().toString())) {
                foundFeatures.add("interfaces");
            }
            if (isLockType(node.getType().toString())) {
                foundFeatures.add("synchronization");
            }
            if (isThreadType(node.getType().toString())) {
                foundFeatures.add("threads");
            }
            if (isRawType(node.getType())) {
                foundFeatures.add("raw types");
            }
            if (isFileIOType(node.getType().toString())) {
                foundFeatures.add("File I/O");
            }
            if (isGenericType(node.getType())) {
                foundFeatures.add("generics");
            }
            if (isRegexType(node.getType().toString())) {
                foundFeatures.add("RE Pattern Syntax");
            }
            if (isGUIType(node.getType().toString())) {
                foundFeatures.add("GUI");
            }
            return super.visit(node);
        }

        @Override
        public boolean visit(FieldDeclaration node) {
            checkCollectionOrMapType(node.getType().toString());
            if (isInterfaceType(node.getType().toString())) {
                foundFeatures.add("interfaces");
            }
            if (isLockType(node.getType().toString())) {
                foundFeatures.add("synchronization");
            }
            if (isThreadType(node.getType().toString())) {
                foundFeatures.add("threads");
            }
            if (isRawType(node.getType())) {
                foundFeatures.add("raw types");
            }
            if (isFileIOType(node.getType().toString())) {
                foundFeatures.add("File I/O");
            }
            if (isGenericType(node.getType())) {
                foundFeatures.add("generics");
            }
            if (isRegexType(node.getType().toString())) {
                foundFeatures.add("RE Pattern Syntax");
            }
            if (isGUIType(node.getType().toString())) {
                foundFeatures.add("GUI");
            }
            return super.visit(node);
        }

        @Override
        public boolean visit(ClassInstanceCreation node) {
            checkCollectionOrMapType(node.getType().toString());
            if (isThreadType(node.getType().toString())) {
                foundFeatures.add("threads");
            }
            if (node.getType() instanceof SimpleType && isRawType(node.getType())) {
                foundFeatures.add("raw types");
            }
            if (isFileIOType(node.getType().toString())) {
                foundFeatures.add("File I/O");
            }
            if (isGenericType(node.getType())) {
                foundFeatures.add("generics");
            }
            if (isRegexType(node.getType().toString())) {
                foundFeatures.add("RE Pattern Syntax");
            }
            if (isGUIType(node.getType().toString())) {
                foundFeatures.add("GUI");
            }
            return super.visit(node);
        }

        @Override
        public boolean visit(ArrayType node) {
            foundFeatures.add("arrays");
            return super.visit(node);
        }

        @Override
        public boolean visit(ArrayCreation node) {
            foundFeatures.add("arrays");
            return super.visit(node);
        }

        @Override
        public boolean visit(ForStatement node) {
            foundFeatures.add("loops");
            if (loopDepth > 0) foundFeatures.add("nested loops");
            loopDepth++;
            boolean result = super.visit(node);
            loopDepth--;
            return result;
        }

        @Override
        public boolean visit(WhileStatement node) {
            foundFeatures.add("loops");
            if (loopDepth > 0) foundFeatures.add("nested loops");
            loopDepth++;
            boolean result = super.visit(node);
            loopDepth--;
            return result;
        }

        @Override
        public boolean visit(DoStatement node) {
            foundFeatures.add("loops");
            if (loopDepth > 0) foundFeatures.add("nested loops");
            loopDepth++;
            boolean result = super.visit(node);
            loopDepth--;
            return result;
        }
    }
}
