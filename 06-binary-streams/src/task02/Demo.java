package task02;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task02");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n02. ByteAlternatingMerger: Чередование блоков двух файлов");
        Data.bytes(input, 1, 2, 3, 4, 5); Data.bytes(second, 10, 20, 30);
        new ByteAlternatingMerger(2).merge(input, second, output);
        Data.show(input, "byte"); Data.show(second, "byte"); Data.show(output, "byte");
        Data.expect(output, 1, 2, 10, 20, 3, 4, 30, 5);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
