package task37;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task37");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n37. BitwiseAndMerger: Побитовое AND");
        Data.bytes(input, 240, 15, 0); Data.bytes(second, 15, 3, 255);
        new BitwiseAndMerger().merge(input, second, output);
        Data.show(input, "byte"); Data.show(second, "byte"); Data.show(output, "byte");
        Data.expect(output, 0, 3, 0);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
