package task28;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Текущий максимум int. */
public class RunningMaximumWriter {
    public void process(Path input, Path output) throws IOException {
        long count = Data.count(input, 4);
        int max = Integer.MIN_VALUE;
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long i = 0; i < count; i++) {
                int value = in.readInt();
                max = Math.max(max, value); out.writeInt(max);
            }

        }
    }
}
