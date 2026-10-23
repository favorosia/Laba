package task25;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task25");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n25. DoubleRangeFilter: Фильтрация double по диапазону");
        Data.doubles(input, -2.0, -1.0, 0.5, 2.0, 3.0);
        new DoubleRangeFilter(-1.0, 2.0).process(input, output);
        Data.show(input, "double"); Data.show(output, "double");
        Data.expectDoubles(output, -1.0, 0.5, 2.0);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
