package interfaces;

import java.io.File;
import java.io.IOException;

public interface CodeCompiler {
    Object compileMe(File newFilePath, String className, int counter) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException;
}
