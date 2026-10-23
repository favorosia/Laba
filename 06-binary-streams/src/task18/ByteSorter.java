package task18;

import java.io.*;
import java.nio.file.Path;

/** Сортировка байтов. */
public class ByteSorter {
    public void sort(Path input, Path output) throws IOException {
        int[] counts = new int[256];
        int length = 0;
        try (InputStream in = new FileInputStream(input.toFile())) {
            int value;
            while ((value = in.read()) != -1) {
                if (++length > 1024) throw new IOException("Допускается не больше 1024 байтов");
                counts[value]++;
            }
        }
        try (OutputStream out = new FileOutputStream(output.toFile())) {
            for (int value = 0; value < 256; value++) {
                for (int i = 0; i < counts[value]; i++) out.write(value);
            }
        }
    }
}
