package task29;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
public class BooleanArrayReader {
    public boolean[] read(Path input) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()))) {
            int length = in.readInt();
            if (length < 0 || Files.size(input) != 4L + length) throw new IOException("Неверная длина массива");
            boolean[] values = new boolean[length];
            for (int i = 0; i < length; i++) values[i] = in.readBoolean();
            return values;
        }
    }
}
