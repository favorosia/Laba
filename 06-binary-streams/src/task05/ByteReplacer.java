package task05;

import java.io.*;
import java.nio.file.Path;

/** Замена значения байта. */
public class ByteReplacer {
    private final int oldValue, newValue;

    public ByteReplacer(byte oldValue, byte newValue) {
        this.oldValue = oldValue & 255; this.newValue = newValue & 255;
    }

    public void replace(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int value;
            while ((value = in.read()) != -1) out.write(value == oldValue ? newValue : value);
        }
    }
}
