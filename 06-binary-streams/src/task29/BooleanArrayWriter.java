package task29;

import java.io.*;
import java.nio.file.Path;

/** Массив boolean с длиной. */
public class BooleanArrayWriter {
    public void write(boolean[] values, Path output) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            out.writeInt(values.length);
            for (boolean value : values) out.writeBoolean(value);
        }
    }
}
