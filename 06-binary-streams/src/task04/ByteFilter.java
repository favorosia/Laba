package task04;

import java.io.*;
import java.nio.file.Path;

/** Удаление запрещённых байтов. */
public class ByteFilter {
    private final byte[] forbidden;

    public ByteFilter(byte[] forbidden) { this.forbidden = forbidden.clone(); }

    public void filter(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int value;
            while ((value = in.read()) != -1) {
                boolean allowed = true;
                for (byte item : forbidden) {
                    if (value == (item & 255)) { allowed = false; break; }
                }
                if (allowed) out.write(value);
            }
        }
    }
}
