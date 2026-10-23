package task06;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task06");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n06. ByteHalver: Беззнаковое деление байта на два");
        Data.bytes(input, 0, 1, 127, 128, 255);
        new ByteHalver().process(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 0, 0, 63, 64, 127);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
