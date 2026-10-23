package task24;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Минимум и максимум double. */
public class DoubleMinMaxReader {
    public void process(Path input, Path output) throws IOException {
        long count = Data.count(input, 8);
        if (count == 0) throw new IOException("Пустой файл");
        double min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long i = 0; i < count; i++) {
                double value = in.readDouble();
                min = Math.min(min, value); max = Math.max(max, value);
            }
            out.writeDouble(min); out.writeDouble(max);
        }
    }
}
