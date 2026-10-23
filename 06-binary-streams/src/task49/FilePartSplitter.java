package task49;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

/** Разделение на N частей. */
public class FilePartSplitter {
    private final int parts;

    public FilePartSplitter(int parts) {
        if (parts <= 0) throw new IllegalArgumentException("N должно быть > 0");
        this.parts = parts;
    }

    public Path[] split(Path input, Path directory) throws IOException {
        Files.createDirectories(directory);
        long size = Files.size(input), base = size / parts, remainder = size % parts;
        Path[] result = new Path[parts];
        try (InputStream in = new FileInputStream(input.toFile())) {
            byte[] buffer = new byte[1024];
            for (int i = 0; i < parts; i++) {
                result[i] = directory.resolve(String.format("part-%03d.bin", i + 1));
                long remaining = base + (i < remainder ? 1 : 0);
                try (OutputStream out = new FileOutputStream(result[i].toFile())) {
                    while (remaining > 0) {
                        int length = in.read(buffer, 0, (int)Math.min(buffer.length, remaining));
                        if (length == -1) throw new EOFException("Исходный файл неожиданно закончился");
                        out.write(buffer, 0, length); remaining -= length;
                    }
                }
            }
        }
        return result;
    }
}
