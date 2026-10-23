package task07;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task07");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n07. MultiKeyXorCipher: XOR с циклическим ключом");
        Data.bytes(input, 0, 10, 128, 255, 42);
        new MultiKeyXorCipher(new byte[]{6, 13}).transform(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 6, 7, 134, 242, 44);
        new MultiKeyXorCipher(new byte[]{6, 13}).transform(output, extra);
        Data.show(extra, "byte"); Data.expect(extra, 0, 10, 128, 255, 42);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
