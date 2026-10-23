package task08;

import java.io.*;
import java.nio.file.Path;

/** Побитовое отрицание. */
public class BitInverter {
    public void process(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int value;
            while ((value = in.read()) != -1) out.write(~value);
        }
    }
}
