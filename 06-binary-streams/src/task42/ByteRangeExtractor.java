package task42;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

/** Извлечение диапазона. */
public class ByteRangeExtractor {
    private final long start, end;

    public ByteRangeExtractor(long start, long end) {
        if (start < 0 || end < start) throw new IllegalArgumentException("Неверный диапазон");
        this.start = start; this.end = end;
    }

    public void extract(Path input, Path output) throws IOException {
        if (end >= Files.size(input)) throw new IOException("Диапазон за концом файла");
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            long position = 0;
            int value;
            while ((value = in.read()) != -1) {
                if (position >= start && position <= end) out.write(value);
                position++;
            }
        }
    }
}
