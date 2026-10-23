package task29;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

import java.util.Arrays;
public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task29");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n29. BooleanArrayWriter: Массив boolean с длиной");
        boolean[] values = {true, false, true, true};
        new BooleanArrayWriter().write(values, output);
        boolean[] restored = new BooleanArrayReader().read(output);
        System.out.println(Arrays.toString(restored));
        if (!Arrays.equals(values, restored)) throw new AssertionError("Массив изменился");
        Data.expect(output, 0, 0, 0, 4, 1, 0, 1, 1);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
