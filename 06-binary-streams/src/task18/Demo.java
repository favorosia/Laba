package task18;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task18");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n18. ByteSorter: Сортировка байтов");
        Data.bytes(input, 255, 7, 0, 128, 7);
        new ByteSorter().sort(input, output);
        Data.show(input, "byte"); Data.show(output, "byte"); Data.expect(output, 0, 7, 7, 128, 255);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
