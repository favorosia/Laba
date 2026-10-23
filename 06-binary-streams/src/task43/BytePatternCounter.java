package task43;

import java.io.*;
import java.nio.file.Path;

/** Непересекающиеся вхождения образца. */
public class BytePatternCounter {
    private final byte[] pattern;

    public BytePatternCounter(byte[] pattern) {
        if (pattern.length == 0) throw new IllegalArgumentException("Образец не должен быть пустым");
        this.pattern = pattern.clone();
    }

    public void count(Path input, Path output) throws IOException {
        byte[] window = new byte[pattern.length];
        int used = 0, count = 0;
        try (InputStream in = new FileInputStream(input.toFile())) {
            int value;
            while ((value = in.read()) != -1) {
                window[used++] = (byte)value;
                if (used < pattern.length) continue;
                boolean matches = true;
                for (int i = 0; i < pattern.length; i++) {
                    if (window[i] != pattern[i]) { matches = false; break; }
                }
                if (matches) { count = Math.addExact(count, 1); used = 0; }
                else {
                    for (int i = 1; i < used; i++) window[i - 1] = window[i];
                    used--;
                }
            }
        }
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) { out.writeInt(count); }
    }
}
