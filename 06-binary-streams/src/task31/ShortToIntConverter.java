package task31;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Расширение short до int. */
public class ShortToIntConverter {
    public void process(Path input, Path output) throws IOException {
        long count = Data.count(input, 2);

        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long i = 0; i < count; i++) {
                short value = in.readShort();
                out.writeInt(value);
            }

        }
    }
}
