package task16;

import java.io.*;
import java.nio.file.Path;

/** Обратный порядок байтов. */
public class ByteReverser {
    public void reverse(Path input, Path output) throws IOException {
        byte[] data;
        try (InputStream in = new FileInputStream(input.toFile())) { data = in.readNBytes(1025); }
        if (data.length > 1024) throw new IOException("Допускается не больше 1024 байтов");
        try (OutputStream out = new FileOutputStream(output.toFile())) {
            for (int i = data.length - 1; i >= 0; i--) out.write(data[i]);
        }
    }
}
