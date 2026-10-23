package task30;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Статистика true и false. */
public class BooleanStatsReader {
    public void process(Path input, Path output) throws IOException {
        long count = Data.count(input, 1);
        int trueCount = 0, falseCount = 0;
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long i = 0; i < count; i++) {
                boolean value = in.readBoolean();
                if (value) trueCount = Math.addExact(trueCount, 1); else falseCount = Math.addExact(falseCount, 1);
            }
            out.writeInt(trueCount); out.writeInt(falseCount);
        }
    }
}
