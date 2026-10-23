package task42;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task42");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n42. ByteRangeExtractor: Извлечение диапазона");
        Data.bytes(input, 0, 1, 2, 3, 4);
        new ByteRangeExtractor(1, 3).extract(input, output);
        Data.show(input, "byte"); Data.show(output, "byte"); Data.expect(output, 1, 2, 3);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
