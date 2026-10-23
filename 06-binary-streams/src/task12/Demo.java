package task12;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task12");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n12. IndexMultiplier: Умножение int на индекс");
        Data.ints(input, 5, -2, 3, 7);
        new IndexMultiplier().process(input, output);
        Data.show(input, "int"); Data.show(output, "int");
        Data.expectInts(output, 0, -2, 6, 21);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
