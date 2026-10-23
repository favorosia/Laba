package task46;

import java.io.*;
import java.nio.file.Path;

/** Удаление каждого N-го байта. */
public class NthByteRemover {
    private final int step;

    public NthByteRemover(int step) {
        if (step <= 0) throw new IllegalArgumentException("N должно быть > 0");
        this.step = step;
    }

    public void remove(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int position = 0, value;
            while ((value = in.read()) != -1) {
                position++;
                if (position == step) position = 0;
                else out.write(value);
            }
        }
    }
}
