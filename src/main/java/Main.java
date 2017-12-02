import interfaces.Calculator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RuLemur on 22.11.2017 in 22:11.
 * WildSelfRecorder
 */
public class Main {
    private static Logger LOG = Logger.getLogger(Main.class);

    private static void initLogger() {
        PropertyConfigurator.configure("./src/main/resources/config/log4j.properties");
    }

    public static void main(String[] args) {
        initLogger();
        LOG.info("Начинаем работать");
        CodeWriter codeWriter = new CodeWriter();
        File newFile = codeWriter.writeClass();

        try {
            compileClass(newFile);
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOG.info("Ошибка");
            e.printStackTrace();
        }
        newFile.delete();
        new File(newFile.getAbsoluteFile().toString().replace(".java",".class")).delete();
        System.out.println();
    }

    public static void compileClass(File newFilePath) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        LOG.debug("Создаем экземпляр класса");
        //Создаем Java Compiler
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // This sets up the class path that the compiler will use.
        // I've added the .jar file that contains the DoStuff interface within in it...
        List<String> optionList = new ArrayList<String>();
        optionList.add("-cp");
        optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler.jar");

        Iterable<? extends JavaFileObject> compilationUnit
                = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(newFilePath));
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                null,
                optionList,
                null,
                compilationUnit);

        if (task.call()) {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{
                    new File("src/main/java").toURI().toURL()});

            Class<?> loadedClass = classLoader.loadClass("CalculatorImpl");
            Object obj = loadedClass.newInstance();
            if (obj instanceof Calculator) {
                Calculator calculator = (Calculator) obj;
                System.out.println(calculator.calculate(3, 4, '+'));
            }
        } else {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.out.format("Error on line %d in %s%n",
                        diagnostic.getLineNumber(),
                        diagnostic.getSource().toUri());
            }
        }
        fileManager.close();

    }
}
