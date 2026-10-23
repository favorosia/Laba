package task23;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task23");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n23. IntArraySumReader: Сумма последовательности int");
        Data.ints(input, 5, -3, 9);
        new IntArraySumReader().process(input, output);
        Data.show(input, "int"); Data.show(output, "int");
        Data.expectInts(output, 11);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
