package task27;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Заглавные латинские буквы. */
public class CharCaseConverter {
    public void process(Path input, Path output) throws IOException {
        long count = Data.count(input, 2);

        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long i = 0; i < count; i++) {
                char value = in.readChar();
                if (value >= 'a' && value <= 'z') value = (char)(value - 'a' + 'A');
                out.writeChar(value);
            }

        }
    }
}
