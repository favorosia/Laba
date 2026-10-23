package task40;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task40");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n40. BinaryFileInserter: Вставка файла по позиции");
        Data.bytes(input, 1, 2, 3); Data.bytes(second, 8, 9);
        new BinaryFileInserter(1).insert(input, second, output);
        Data.show(input, "byte"); Data.show(second, "byte"); Data.show(output, "byte");
        Data.expect(output, 1, 8, 9, 2, 3);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
