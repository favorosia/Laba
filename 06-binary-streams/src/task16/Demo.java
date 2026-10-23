package task16;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task16");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n16. ByteReverser: Обратный порядок байтов");
        Data.bytes(input, 1, 2, 128, 255, 5);
        new ByteReverser().reverse(input, output);
        Data.show(input, "byte"); Data.show(output, "byte"); Data.expect(output, 5, 255, 128, 2, 1);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
