package task47;

import java.io.*;
import java.nio.file.Path;

/** Шифр Цезаря по модулю 256. */
public class CaesarByteCipher {
    private final int shift;

    public CaesarByteCipher(int shift) { this.shift = Math.floorMod(shift, 256); }
    public void encrypt(Path input, Path output) throws IOException { transform(input, output, shift); }
    public void decrypt(Path input, Path output) throws IOException { transform(input, output, -shift); }
    private void transform(Path input, Path output, int offset) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int value;
            while ((value = in.read()) != -1) out.write((value + offset) & 255);
        }
    }
}
