package task01;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task01");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n01. ChunkFileCopier: Копирование блоками");
        Data.bytes(input, 0, 10, 128, 255, 7);
        new ChunkFileCopier(2).copy(input, output);
        Data.show(input, "byte"); Data.show(output, "byte"); Data.expect(output, 0, 10, 128, 255, 7);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
