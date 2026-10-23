package task35;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

import java.util.Arrays;
public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task35");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n35. MatrixWriter: Матрица int");
        int[][] values = {{1, -2, 3}, {4, 5, 6}};
        MatrixWriter storage = new MatrixWriter(); storage.write(values, output);
        int[][] restored = storage.read(output);
        for (int[] row : restored) System.out.println(Arrays.toString(row));
        if (!Arrays.deepEquals(values, restored)) throw new AssertionError("Матрица изменилась");
        Data.expectInts(output, 2, 3, 1, -2, 3, 4, 5, 6);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
