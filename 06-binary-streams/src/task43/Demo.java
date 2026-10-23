package task43;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task43");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n43. BytePatternCounter: Непересекающиеся вхождения образца");
        Data.bytes(input, 1, 1, 1, 1, 1);
        new BytePatternCounter(new byte[]{1, 1}).count(input, output);
        Data.show(input, "byte"); Data.show(output, "int"); Data.expectInts(output, 2);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
