package task01;

import java.io.*;
import java.nio.file.Path;

/** Копирование блоками. */
public class ChunkFileCopier {
    private final int blockSize;

    public ChunkFileCopier(int blockSize) {
        if (blockSize <= 0) throw new IllegalArgumentException("Размер блока должен быть > 0");
        this.blockSize = blockSize;
    }

    public void copy(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            byte[] buffer = new byte[blockSize];
            int length;
            while ((length = in.read(buffer)) != -1) out.write(buffer, 0, length);
        }
    }
}
