package task24;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task24");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n24. DoubleMinMaxReader: Минимум и максимум double");
        Data.doubles(input, 2.5, -7.0, 10.0, 0.0);
        new DoubleMinMaxReader().process(input, output);
        Data.show(input, "double"); Data.show(output, "double");
        Data.expectDoubles(output, -7.0, 10.0);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
