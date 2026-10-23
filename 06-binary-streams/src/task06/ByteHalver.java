package task06;

import java.io.*;
import java.nio.file.Path;

/** Беззнаковое деление байта на два. */
public class ByteHalver {
    public void process(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int value;
            while ((value = in.read()) != -1) out.write(value / 2);
        }
    }
}
