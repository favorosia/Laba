package task15;

import java.io.*;
import java.nio.file.Path;

/** Удаление чётных позиций. */
public class EvenIndexByteRemover {
    public void remove(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            boolean odd = false;
            int value;
            while ((value = in.read()) != -1) {
                if (odd) out.write(value);
                odd = !odd;
            }
        }
    }
}
