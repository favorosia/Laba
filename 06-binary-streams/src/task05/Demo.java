package task05;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task05");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n05. ByteReplacer: Замена значения байта");
        Data.bytes(input, 255, 1, 255, 0);
        new ByteReplacer((byte)255, (byte)7).replace(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 7, 1, 7, 0);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
