package task19;

import java.io.*;
import java.nio.file.Path;

/** Удаление повторов в сериях. */
public class ConsecutiveDuplicateRemover {
    public void remove(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int previous = -1, value;
            while ((value = in.read()) != -1) {
                if (value != previous) out.write(value);
                previous = value;
            }
        }
    }
}
