package task36;

import java.io.*;
import java.nio.file.Path;

/** Фильтрация чётных значений байтов. */
public class EvenByteFilter {
    public void filter(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int value;
            while ((value = in.read()) != -1) if (value % 2 == 0) out.write(value);
        }
    }
}
