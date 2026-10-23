package task07;

import java.io.*;
import java.nio.file.Path;

/** XOR с циклическим ключом. */
public class MultiKeyXorCipher {
    private final byte[] key;

    public MultiKeyXorCipher(byte[] key) {
        if (key.length == 0) throw new IllegalArgumentException("Ключ не должен быть пустым");
        this.key = key.clone();
    }

    public void transform(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int index = 0, value;
            while ((value = in.read()) != -1) {
                out.write(value ^ (key[index] & 255));
                index = (index + 1) % key.length;
            }
        }
    }
}
