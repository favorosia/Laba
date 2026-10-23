package task33;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task33");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n33. SortedIntMerger: Слияние отсортированных int");
        Data.ints(input, -5, 2, 2, 100); Data.ints(second, -7, 2, 9);
        new SortedIntMerger().merge(input, second, output);
        Data.show(input, "int"); Data.show(second, "int"); Data.show(output, "int");
        Data.expectInts(output, -7, -5, 2, 2, 2, 9, 100);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
