package task17;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task17");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n17. BytePairSwapper: Перестановка соседних байтов");
        Data.bytes(input, 50, 10, 2, 255, 7);
        new BytePairSwapper().process(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 10, 50, 255, 2, 7);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
