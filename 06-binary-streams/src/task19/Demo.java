package task19;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task19");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n19. ConsecutiveDuplicateRemover: Удаление повторов в сериях");
        Data.bytes(input, 1, 1, 2, 2, 2, 1, 255, 255);
        new ConsecutiveDuplicateRemover().remove(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 1, 2, 1, 255);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
