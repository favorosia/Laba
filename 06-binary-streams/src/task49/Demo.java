package task49;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task49");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n49. FilePartSplitter: Разделение на N частей");
        Data.bytes(input, 1, 2, 3, 4, 5, 6, 7);
        Path[] parts = new FilePartSplitter(3).split(input, dir.resolve("parts"));
        for (Path part : parts) Data.show(part, "byte");
        Data.expect(parts[0], 1, 2, 3); Data.expect(parts[1], 4, 5); Data.expect(parts[2], 6, 7);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
