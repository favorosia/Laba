package task03;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task03");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n03. PositionSplitter: Разделение по чётности позиции");
        Data.bytes(input, 10, 20, 30, 40, 50);
        new PositionSplitter().split(input, output, extra);
        Data.show(input, "byte"); Data.show(output, "byte"); Data.show(extra, "byte");
        Data.expect(output, 10, 30, 50); Data.expect(extra, 20, 40);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
