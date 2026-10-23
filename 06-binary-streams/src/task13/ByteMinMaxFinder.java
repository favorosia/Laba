package task13;

import java.io.*;
import java.nio.file.Path;

/** Минимальный и максимальный байт. */
public class ByteMinMaxFinder {
    public void find(Path input, Path output) throws IOException {
        int min = 255, max = 0;
        boolean found = false;
        try (InputStream in = new FileInputStream(input.toFile())) {
            int value;
            while ((value = in.read()) != -1) {
                min = Math.min(min, value); max = Math.max(max, value); found = true;
            }
        }
        if (!found) throw new IOException("В пустом файле нет минимума и максимума");
        try (OutputStream out = new FileOutputStream(output.toFile())) { out.write(min); out.write(max); }
    }
}
