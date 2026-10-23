package task47;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task47");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n47. CaesarByteCipher: Шифр Цезаря по модулю 256");
        Data.bytes(input, 0, 10, 250, 255);
        CaesarByteCipher cipher = new CaesarByteCipher(10);
        cipher.encrypt(input, output); cipher.decrypt(output, extra);
        Data.show(input, "byte"); Data.show(output, "byte"); Data.show(extra, "byte");
        Data.expect(output, 10, 20, 4, 9); Data.expect(extra, 0, 10, 250, 255);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
