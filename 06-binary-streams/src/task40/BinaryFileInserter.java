package task40;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

/** Вставка файла по позиции. */
public class BinaryFileInserter {
    private final long position;

    public BinaryFileInserter(long position) {
        if (position < 0) throw new IllegalArgumentException("Позиция должна быть >= 0");
        this.position = position;
    }

    public void insert(Path input, Path inserted, Path output) throws IOException {
        if (position > Files.size(input)) throw new IOException("Позиция за концом файла");
        try (InputStream in = new FileInputStream(input.toFile());
             InputStream part = new FileInputStream(inserted.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            for (long i = 0; i < position; i++) out.write(in.read());
            int value;
            while ((value = part.read()) != -1) out.write(value);
            while ((value = in.read()) != -1) out.write(value);
        }
    }
}
