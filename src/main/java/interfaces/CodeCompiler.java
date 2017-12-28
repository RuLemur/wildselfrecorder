package interfaces;

import java.io.File;
import java.io.IOException;

public interface CodeCompiler {
    Object compileMe(File newFilePath, String className) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException;

    void createNext(int counter);
}
