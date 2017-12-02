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
        char[] operations = {'+', '-'};
        StringBuilder javaString = new StringBuilder();
//        javaString.append(System.get) TODO: получить подпись автора кодом

        javaString.append("import interfaces.Calculator;\n");
        javaString.append("public class CalculatorImpl implements Calculator{ \n");
        javaString.append("    public Integer calculate(int one, int two, char operation){\n");
        for (char operation : operations) {
            for (int i = 0; i <= 20; i++) {
                for (int j = 0; j <= 20; j++) {
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
}
