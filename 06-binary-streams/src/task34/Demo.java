package task34;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task34");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n34. IntEvenOddSplitter: Разделение int на чётные и нечётные");
        Data.ints(input, -3, -2, 0, 7, 8);
        new IntEvenOddSplitter().split(input, output, extra);
        Data.show(input, "int"); Data.show(output, "int"); Data.show(extra, "int");
        Data.expectInts(output, -2, 0, 8); Data.expectInts(extra, -3, 7);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
