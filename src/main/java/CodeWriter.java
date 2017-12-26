import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by RuLemur on 22.11.2017 in 22:03.
 * WildSelfRecorder
 */
public class CodeWriter {
    public static final String CALCULATOR = "CodeCompilerImpl";

    private static Logger LOG = Logger.getLogger(CodeWriter.class);

    public File writeClass(String className) {
        LOG.info("Начинаем писать класс CodeCompiler");
        String path = "src/main/java";

        File classFolder = new File(path);
        classFolder.mkdir();

        File fileNewClass = new File(classFolder.toString() + File.separator + className + ".java");
        try (FileWriter fileWriter = new FileWriter(fileNewClass)) {
            switch (className) {
                case CALCULATOR: {
                    fileWriter.write(getCompilerCode(0));
                    break;
                }
                default: {
                    LOG.error("Не найдена информация для создания класса \"" + className + "\"");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNewClass;
    }

    public String getCalculatorCode() {
        char[] operations = {'+', '-'};
        StringBuilder javaString = new StringBuilder();

        javaString.append("import interfaces.Calculator;\n");
        javaString.append("public class CalculatorImpl implements Calculator { \n");
        javaString.append("    public Integer calculateSum(int one, int two) {\n");

        for (char operation : operations) {
            for (int i = 0; i <= 40; i++) { //1835
                for (int j = 0; j <= 40; j++) {
                    javaString.append("        if (one == " + i + " && two == " + j + ") {\n ");
                    javaString.append("           return " + (i + j) + ";\n");
                    javaString.append("        }\n");
                }
            }
        }
        javaString.append("        return calculateSum1(one, two);\n");
        javaString.append("    }\n");

        javaString.append("    public Integer calculateSum1(int one, int two) {\n");

        for (char operation : operations) {
            for (int i = 41; i <= 80; i++) { //1835
                for (int j = 41; j <= 80; j++) {
                    javaString.append("        if (one == " + i + " && two == " + j + ") {\n ");
                    javaString.append("           return " + (i + j) + ";\n");
                    javaString.append("        }\n");
                }
            }
        }
        javaString.append("        return null;\n");
        javaString.append("    }\n");

        javaString.append("}\n");
        return javaString.toString();
    }

    /**
     * генератор кода более сложный, но в метод влезает больше операций
     *
     * @return
     */
    public String getCalculatorCodeVsCase() {
        char[] operations = {'+', '-'};
        StringBuilder javaString = new StringBuilder();
//        javaString.append(System.get) TODO: получить подпись автора кодом

        javaString.append("import interfaces.Calculator;\n");
        javaString.append("public class CalculatorImpl implements Calculator { \n");
        javaString.append("    public Integer calculateSum(int one, int two) {\n");
        for (char operation : operations) {
            javaString.append("        switch (one) {\n");
            for (int i = 0; i <= 50; i++) { //1835
                javaString.append("            case " + i + ": {\n");
                javaString.append("                switch (two) {\n");
                for (int j = 0; j <= 50; j++) {
                    javaString.append("                    case " + j + ":\n");
                    javaString.append("                        return " + (j + i) + ";\n");
                }
                javaString.append("             }}\n");
            }
            javaString.append("          }\n");
        }
        javaString.append("        return null;\n");
        javaString.append("    }\n");

        javaString.append("}\n");
        return javaString.toString();
    }

    public String getCompilerCode(int counter) {
        StringBuilder javaString = new StringBuilder();

        javaString.append("import interfaces.CodeCompiler;\n");
        javaString.append("public class CodeCompilerImpl" + (counter + 1) + " implements CodeCompiler { \n");
        javaString.append("    private static Logger LOG = Logger.getLogger(CalculatorImpl" + (counter + 1) + ".class);\n");
        javaString.append("    public Object compileMe(String className, int counter) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException{\n");

        javaString.append("        LOG.debug(\"Создаем экземпляр класса\");\n\n");
        //Создаем Java Compiler
        javaString.append("        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();\n");
        javaString.append("        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();\n");
        javaString.append("        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);\n");
        // This sets up the class path that the compiler will use.
        // I've added the .jar file that contains the DoStuff interface within in it...
        javaString.append("        List<String> optionList = new ArrayList<>();\n\n");
        javaString.append("        optionList.add(\"-cp\");\n");
        javaString.append("        optionList.add(System.getProperty(\"java.class.path\") + \";dist/InlineCompiler.jar\");\n\n");

        javaString.append("        Iterable<? extends JavaFileObject> compilationUnit = " +
                "fileManager.getJavaFileObjectsFromFiles(Arrays.asList(newFilePath));\n");
        javaString.append("        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, optionList, null, compilationUnit);\n\n");

        javaString.append("         Object obj = null;\n");
        javaString.append("         if (task.call()) {\n");
        javaString.append("            URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(\"src/main/java\").toURI().toURL()});\n\n");
        javaString.append("            Class<?> loadedClass = classLoader.loadClass(CalculatorImpl" + (counter) + ");\n");
        javaString.append("            Object obj = loadedClass.newInstance();\n");
        javaString.append("        } else {\n");
        javaString.append("            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {\n");
        javaString.append("                LOG.warn(\"Error on line %d in %s%n\" + diagnostic.getLineNumber() + diagnostic.getSource().toUri());\n");
        javaString.append("            }\n");
        javaString.append("        }\n");
        javaString.append("        fileManager.close();\n");
        javaString.append("        return obj;\n");

        javaString.append("    }\n");

        javaString.append("}\n");
        return javaString.toString();
    }
}
