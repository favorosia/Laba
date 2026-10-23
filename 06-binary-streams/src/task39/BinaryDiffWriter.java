package task39;

import java.io.*;
import java.nio.file.Path;

/** XOR двух файлов с остатком. */
public class BinaryDiffWriter {
    public void write(Path first, Path second, Path output) throws IOException {
        try (InputStream a = new FileInputStream(first.toFile());
             InputStream b = new FileInputStream(second.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            while (true) {
                int x = a.read(), y = b.read();
                if (x == -1 && y == -1) break;
                if (x == -1) out.write(y);
                else if (y == -1) out.write(x);
                else out.write(x ^ y);
            }
        }
    }
}
