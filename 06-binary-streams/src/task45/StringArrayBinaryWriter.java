package task45;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

/** Массив строк writeUTF/readUTF. */
public class StringArrayBinaryWriter {
    public void write(String[] values, Path output) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            out.writeInt(values.length);
            for (String value : values) out.writeUTF(value);
        }
    }

    public String[] read(Path input) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()))) {
            int length = in.readInt();
            if (length < 0 || length > (Files.size(input) - 4) / 2) throw new IOException("Неверное число строк");
            String[] values = new String[length];
            for (int i = 0; i < length; i++) values[i] = in.readUTF();
            if (in.read() != -1) throw new IOException("Лишние данные после массива");
            return values;
        }
    }
}
