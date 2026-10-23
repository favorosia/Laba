package task08;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task08");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n08. BitInverter: Побитовое отрицание");
        Data.bytes(input, 0, 6, 128, 255);
        new BitInverter().process(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 255, 249, 127, 0);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
