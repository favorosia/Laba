package task32;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task32");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n32. ByteCircularShifter: Циклический сдвиг вправо");
        Data.bytes(input, 1, 2, 3, 4, 5);
        new ByteCircularShifter(7).shift(input, output);
        Data.show(input, "byte"); Data.show(output, "byte"); Data.expect(output, 4, 5, 1, 2, 3);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
