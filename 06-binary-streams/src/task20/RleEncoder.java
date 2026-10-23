package task20;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Сжатие серий RLE. */
public class RleEncoder {
    public void encode(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            int previous = in.read();
            if (previous == -1) return;
            int count = 1, value;
            while ((value = in.read()) != -1) {
                if (value == previous && count < Integer.MAX_VALUE) count++;
                else {
                    out.writeByte(previous); out.writeInt(count);
                    previous = value; count = 1;
                }
            }
            out.writeByte(previous); out.writeInt(count);
        }
    }

    public void decode(Path input, Path output) throws IOException {
        long count = Data.count(input, 5);
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             OutputStream out = new FileOutputStream(output.toFile())) {
            for (long i = 0; i < count; i++) {
                int value = in.readUnsignedByte(), length = in.readInt();
                if (length <= 0) throw new IOException("Длина серии должна быть > 0");
                for (int j = 0; j < length; j++) out.write(value);
            }
        }
    }
}
