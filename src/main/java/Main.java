import interfaces.Calculator;
import interfaces.CodeCompiler;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import sun.rmi.runtime.Log;

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
        LOG.info("Начало работы рекурсии");
        LOG.debug("Создаем первый класс с id:0");
        CodeWriter codeWriter = new CodeWriter();
        String className = CodeWriter.CODEWRITER + "0";
        File newFile = codeWriter.writeClass(className);

        try {
            LOG.info("Запускаем компиляцию класса \"" + className + "\"");
            Object compiledClass = compileClass(newFile, className);
            if(compiledClass instanceof CodeCompiler){
                LOG.debug("Запрашиваем создание класса с id: 1");
                ((CodeCompiler) compiledClass).createNext(1);
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOG.error("Ошибка компиляции файла \"" + className + "\"");
            e.printStackTrace();
        }
        LOG.info("Удаляем созданные файлы (" + className + ")");
        newFile.delete();
        new File(newFile.getAbsoluteFile().toString().replace(".java", ".class")).delete();
    }

    public static Object compileClass(File newFilePath, String className) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        LOG.info("Начало работы Создателя класса " + className);
        //Создаем Java Compiler
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // This sets up the class path that the compiler will use.
        // I've added the .jar file that contains the DoStuff interface within in it...
        LOG.debug("Прописываем параметры для класса " + className);
        List<String> optionList = new ArrayList<>();
        optionList.add("-cp");
        optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler.jar");

        LOG.debug("Создаем объект компиляции " + className);
        Iterable<? extends JavaFileObject> compilationUnit = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(newFilePath));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, optionList, null, compilationUnit);

        LOG.debug("Производим загрузку класса " + className);
        Object obj = null;
        if (task.call()) {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("src/main/java").toURI().toURL()});
            LOG.info("Закончили создание класса " + className);
            Class<?> loadedClass = classLoader.loadClass(className);
            LOG.debug("Создаем инстанс нового класса " + className);
            obj = loadedClass.newInstance();
        } else {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                LOG.warn("Error on line %d in %s%n" + diagnostic.getLineNumber() + diagnostic.getSource().toUri());
            }
        }
        fileManager.close();
        return obj;

    }
}
