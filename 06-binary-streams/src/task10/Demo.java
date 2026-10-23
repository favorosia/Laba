package task10;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task10");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n10. DoubleAverageCalculator: Среднее арифметическое double");
        Data.doubles(input, 1.5, 2.5, 8.0);
        new DoubleAverageCalculator().process(input, output);
        Data.show(input, "double"); Data.show(output, "double");
        Data.expectDoubles(output, 4.0);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
