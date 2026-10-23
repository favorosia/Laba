package task15;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task15");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n15. EvenIndexByteRemover: Удаление чётных позиций");
        Data.bytes(input, 10, 20, 30, 40, 50);
        new EvenIndexByteRemover().remove(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 20, 40);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
