package task14;

import java.io.*;
import java.nio.file.Path;

/** Количество нулевых байтов. */
public class ZeroByteCounter {
    public void count(Path input, Path output) throws IOException {
        int count = 0;
        try (InputStream in = new FileInputStream(input.toFile())) {
            int value;
            while ((value = in.read()) != -1) if (value == 0) count = Math.addExact(count, 1);
        }
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) { out.writeInt(count); }
    }
}
