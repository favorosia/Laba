package task28;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task28");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n28. RunningMaximumWriter: Текущий максимум int");
        Data.ints(input, -3, -5, 4, 2, 8);
        new RunningMaximumWriter().process(input, output);
        Data.show(input, "int"); Data.show(output, "int");
        Data.expectInts(output, -3, -3, 4, 4, 8);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
