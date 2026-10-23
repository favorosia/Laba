package task12;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Умножение int на индекс. */
public class IndexMultiplier {
    public void process(Path input, Path output) throws IOException {
        long count = Data.count(input, 4);

        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long i = 0; i < count; i++) {
                int value = in.readInt();
                out.writeInt(value * (int)i);
            }

        }
    }
}
