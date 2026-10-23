package task14;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task14");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n14. ZeroByteCounter: Количество нулевых байтов");
        Data.bytes(input, 0, 1, 0, 255, 0);
        new ZeroByteCounter().count(input, output);
        Data.show(input, "byte"); Data.show(output, "int"); Data.expectInts(output, 3);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
