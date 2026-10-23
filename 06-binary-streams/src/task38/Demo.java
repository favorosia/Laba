package task38;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task38");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n38. BitwiseOrMerger: Побитовое OR");
        Data.bytes(input, 240, 15, 0); Data.bytes(second, 15, 3, 255);
        new BitwiseOrMerger().merge(input, second, output);
        Data.show(input, "byte"); Data.show(second, "byte"); Data.show(output, "byte");
        Data.expect(output, 255, 15, 255);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
