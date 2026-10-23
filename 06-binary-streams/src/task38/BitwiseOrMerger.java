package task38;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

/** Побитовое OR. */
public class BitwiseOrMerger {
    public void merge(Path first, Path second, Path output) throws IOException {
        if (Files.size(first) != Files.size(second)) throw new IOException("Размеры должны совпадать");
        try (InputStream a = new FileInputStream(first.toFile());
             InputStream b = new FileInputStream(second.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int x;
            while ((x = a.read()) != -1) out.write(x | b.read());
        }
    }
}
