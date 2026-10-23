package task36;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task36");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n36. EvenByteFilter: Фильтрация чётных значений байтов");
        Data.bytes(input, 0, 1, 128, 255, 4);
        new EvenByteFilter().filter(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 0, 128, 4);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
