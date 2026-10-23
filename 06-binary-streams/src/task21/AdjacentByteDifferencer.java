package task21;

import java.io.*;
import java.nio.file.Path;

/** Абсолютные разности соседних байтов. */
public class AdjacentByteDifferencer {
    public void process(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int previous = in.read();
            if (previous == -1) return;
            int current;
            while ((current = in.read()) != -1) {
                // Выбрана абсолютная разность: см. противоречие формулы в README.
                out.write((byte)Math.abs(current - previous));
                previous = current;
            }
        }
    }
}
