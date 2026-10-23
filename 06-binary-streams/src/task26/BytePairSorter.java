package task26;

import java.io.*;
import java.nio.file.Path;

/** Сортировка внутри пар. */
public class BytePairSorter {
    public void process(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int first;
            while ((first = in.read()) != -1) {
                int second = in.read();
                if (second == -1) { out.write(first); break; }
                out.write(Math.min(first, second));
                out.write(Math.max(first, second));
            }
        }
    }
}
