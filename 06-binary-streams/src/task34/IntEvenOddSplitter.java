package task34;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Разделение int на чётные и нечётные. */
public class IntEvenOddSplitter {
    public void split(Path input, Path even, Path odd) throws IOException {
        long count = Data.count(input, 4);
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream a = new DataOutputStream(new FileOutputStream(even.toFile()));
             DataOutputStream b = new DataOutputStream(new FileOutputStream(odd.toFile()))) {
            for (long i = 0; i < count; i++) {
                int value = in.readInt();
                if (value % 2 == 0) a.writeInt(value); else b.writeInt(value);
            }
        }
    }
}
