package task50;

import java.io.*;
import java.nio.file.Path;

/** Нормированная гистограмма. */
public class NormalizedHistogramWriter {
    public void write(Path input, Path output) throws IOException {
        long[] counts = new long[256];
        long total = 0;
        try (InputStream in = new FileInputStream(input.toFile())) {
            int value;
            while ((value = in.read()) != -1) { counts[value]++; total++; }
        }
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long count : counts) out.writeFloat(total == 0 ? 0.0f : (float)((double)count / total));
        }
    }
}
