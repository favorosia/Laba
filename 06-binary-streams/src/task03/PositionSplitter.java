package task03;

import java.io.*;
import java.nio.file.Path;

/** Разделение по чётности позиции. */
public class PositionSplitter {
    public void split(Path input, Path even, Path odd) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream a = new FileOutputStream(even.toFile());
             OutputStream b = new FileOutputStream(odd.toFile())) {
            boolean evenPosition = true;
            int value;
            while ((value = in.read()) != -1) {
                if (evenPosition) a.write(value); else b.write(value);
                evenPosition = !evenPosition;
            }
        }
    }
}
