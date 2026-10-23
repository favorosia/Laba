package task26;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task26");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n26. BytePairSorter: Сортировка внутри пар");
        Data.bytes(input, 50, 10, 2, 255, 7);
        new BytePairSorter().process(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 10, 50, 2, 255, 7);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
