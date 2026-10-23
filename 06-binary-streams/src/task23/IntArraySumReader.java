package task23;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Сумма последовательности int. */
public class IntArraySumReader {
    public void process(Path input, Path output) throws IOException {
        long count = Data.count(input, 4);
        int sum = 0;
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long i = 0; i < count; i++) {
                int value = in.readInt();
                sum += value;
            }
            out.writeInt(sum);
        }
    }
}
