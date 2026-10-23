package task02;

import java.io.*;
import java.nio.file.Path;

/** Чередование блоков двух файлов. */
public class ByteAlternatingMerger {
    private final int blockSize;

    public ByteAlternatingMerger(int blockSize) {
        if (blockSize <= 0) throw new IllegalArgumentException("N должно быть > 0");
        this.blockSize = blockSize;
    }

    public void merge(Path first, Path second, Path output) throws IOException {
        try (InputStream a = new FileInputStream(first.toFile());
             InputStream b = new FileInputStream(second.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            byte[] buffer = new byte[blockSize];
            int left, right;
            do {
                // readNBytes собирает полный блок, если ещё не достигнут EOF.
                left = a.readNBytes(buffer, 0, buffer.length); out.write(buffer, 0, left);
                right = b.readNBytes(buffer, 0, buffer.length); out.write(buffer, 0, right);
            } while (left > 0 || right > 0);
        }
    }
}
