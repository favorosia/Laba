package task45;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.Arrays;
public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task45");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n45. StringArrayBinaryWriter: Массив строк writeUTF/readUTF");
        String[] values = {"Привет", "Java", "", "a\u0000b"};
        StringArrayBinaryWriter storage = new StringArrayBinaryWriter(); storage.write(values, output);
        String[] restored = storage.read(output); System.out.println(Arrays.toString(restored));
        if (!Arrays.equals(values, restored)) throw new AssertionError("Строки изменились");
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
