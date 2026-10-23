package task46;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task46");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n46. NthByteRemover: Удаление каждого N-го байта");
        Data.bytes(input, 1, 2, 3, 4, 5, 6, 7);
        new NthByteRemover(3).remove(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 1, 2, 4, 5, 7);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
