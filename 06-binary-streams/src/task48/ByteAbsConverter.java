package task48;

import java.io.*;
import java.nio.file.Path;

/** Модуль знакового byte. */
public class ByteAbsConverter {
    public void process(Path input, Path output) throws IOException {
        try (InputStream in = new FileInputStream(input.toFile());
             OutputStream out = new FileOutputStream(output.toFile())) {
            int value;
            while ((value = in.read()) != -1) out.write((byte)Math.abs((int)(byte)value));
        }
    }
}
