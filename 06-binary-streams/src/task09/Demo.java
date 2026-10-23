package task09;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task09");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n09. PositiveIntFilter: Фильтрация отрицательных int");
        Data.ints(input, -5, 0, 7, -1, 12);
        new PositiveIntFilter().process(input, output);
        Data.show(input, "int"); Data.show(output, "int");
        Data.expectInts(output, 0, 7, 12);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
