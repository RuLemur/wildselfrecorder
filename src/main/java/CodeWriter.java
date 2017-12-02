import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by RuLemur on 22.11.2017 in 22:03.
 * WildSelfRecorder
 */
public class CodeWriter {
    private static Logger LOG = Logger.getLogger(CodeWriter.class);

    public File writeClass() {
        LOG.info("Начинаем писать класс Calculator");
        String className = "CalculatorImpl";
        String path = "src/main/java";
//        String packageName = "selfWriters";
        char[] operations = {'+', '-'};

        File classFolder = new File(path);
        classFolder.mkdir();


//        StringBuilder javaString = new StringBuilder();
//        javaString.append("package " + packageName + ";\n\n");
//        javaString.append(System.get) TODO: получить подпись автора кодом


//        String exitFile = "selfWriters." + className;
        File fileNewClass = new File(classFolder.toString() + File.separator + className + ".java");
        try (FileWriter fileWriter = new FileWriter(fileNewClass)) {

            fileWriter.write(getNewCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNewClass;
    }

    public String getNewCode() {
        StringBuilder javaString = new StringBuilder();
//        javaString.append("package " + this.getClass().getCanonicalName() + ";\n\n");
//        javaString.append(System.get) TODO: получить подпись автора кодом

        javaString.append("import interfaces.Calculator;\n");
        javaString.append(" public class CalculatorImpl implements Calculator{ \n");
        javaString.append("     public String calculate(int one, int two, char operation){\n");
        javaString.append("         return \"Hi! i'm worked\";");
        javaString.append("     }");
        javaString.append(" }");
        return javaString.toString();
    }
}
