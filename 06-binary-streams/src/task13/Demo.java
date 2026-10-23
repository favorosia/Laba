package task13;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task13");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n13. ByteMinMaxFinder: Минимальный и максимальный байт");
        Data.bytes(input, 200, 7, 255, 10);
        new ByteMinMaxFinder().find(input, output);
        Data.show(input, "byte"); Data.show(output, "byte"); Data.expect(output, 7, 255);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
