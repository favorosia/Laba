package task41;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

/** Удаление диапазона. */
public class ByteRangeRemover {
    private final long start, end;

    public ByteRangeRemover(long start, long end) {
        if (start < 0 || end < start) throw new IllegalArgumentException("Неверный диапазон");
        this.start = start; this.end = end;
    }

    public void remove(Path input, Path output) throws IOException {
        if (end >= Files.size(input)) throw new IOException("Диапазон за концом файла");
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            long position = 0;
            int value;
            while ((value = in.read()) != -1) {
                if (position < start || position > end) out.write(value);
                position++;
            }
        }
    }
}
