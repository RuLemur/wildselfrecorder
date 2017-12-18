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
     * @return
     */
    public String getNewCodeVsCase() {
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

        javaString.append("    public Integer calculateSum2(int one, int two) {\n");
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
}
