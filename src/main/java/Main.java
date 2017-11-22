import interfaces.Calculator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

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
        String newFilePath = codeWriter.writeClass();
        try {
            LOG.debug("Создаем экземпляр класса");
            JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();
            systemJavaCompiler.run(null,null,null,"D:\\fanto\\IdeaProjects\\WildSelfRecorder\\src\\main\\java\\selfWriters\\CalculatorImpl.java");
            Class c = Class.forName(newFilePath);

            Class[] interfaces = c.getInterfaces();
            for (Class anInterface : interfaces) {
                if (anInterface.getName().equals(Calculator.class.getName())) {
                    Calculator calculator = (Calculator) c.newInstance();
                    System.out.println(calculator.calculate(3, 4, '+'));
                }
            }
        } catch (ClassNotFoundException e) {
            LOG.error("Не найден класс по пути \"" + newFilePath + "\"");
            e.printStackTrace();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


        System.out.println();
    }
}
