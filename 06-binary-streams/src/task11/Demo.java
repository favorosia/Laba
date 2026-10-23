package task11;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task11");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n11. ByteDuplicator: Дублирование байтов");
        Data.bytes(input, 10, 20, 255);
        new ByteDuplicator().process(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 10, 10, 20, 20, 255, 255);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
