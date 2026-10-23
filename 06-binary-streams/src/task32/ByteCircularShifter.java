package task32;

import java.io.*;
import java.nio.file.Path;

/** Циклический сдвиг вправо. */
public class ByteCircularShifter {
    private final int shift;

    public ByteCircularShifter(int shift) {
        if (shift < 0) throw new IllegalArgumentException("N не должно быть отрицательным");
        this.shift = shift;
    }

    public void shift(Path input, Path output) throws IOException {
        byte[] data;
        try (InputStream in = new FileInputStream(input.toFile())) { data = in.readAllBytes(); }
        try (OutputStream out = new FileOutputStream(output.toFile())) {
            if (data.length == 0) return;
            int offset = shift % data.length, split = data.length - offset;
            out.write(data, split, offset); out.write(data, 0, split);
        }
    }
}
