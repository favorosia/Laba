package task22;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

import java.util.Arrays;
public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task22");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n22. IntArrayWriter: Массив int с длиной");
        int[] values = {5, -1, 0, 200}; IntArrayWriter storage = new IntArrayWriter();
        storage.write(values, output); int[] restored = storage.read(output);
        System.out.println(Arrays.toString(restored));
        if (!Arrays.equals(values, restored)) throw new AssertionError("Массив изменился");
        Data.expectInts(output, 4, 5, -1, 0, 200);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
