package task22;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

/** Массив int с длиной. */
public class IntArrayWriter {
    public void write(int[] values, Path output) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            out.writeInt(values.length);
            for (int value : values) out.writeInt(value);
        }
    }

    public int[] read(Path input) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()))) {
            int length = in.readInt();
            if (length < 0 || Files.size(input) != 4L + 4L * length) throw new IOException("Неверная длина массива");
            int[] values = new int[length];
            for (int i = 0; i < length; i++) values[i] = in.readInt();
            return values;
        }
    }
}
