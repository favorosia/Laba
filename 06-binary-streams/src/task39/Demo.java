package task39;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task39");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n39. BinaryDiffWriter: XOR двух файлов с остатком");
        Data.bytes(input, 255, 10, 20, 30); Data.bytes(second, 15, 3);
        new BinaryDiffWriter().write(input, second, output);
        Data.show(input, "byte"); Data.show(second, "byte"); Data.show(output, "byte");
        Data.expect(output, 240, 9, 20, 30);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
