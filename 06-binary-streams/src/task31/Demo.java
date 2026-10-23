package task31;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task31");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n31. ShortToIntConverter: Расширение short до int");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(input.toFile()))) {
            for (short value : new short[]{(short) - 32768, (short) - 1, (short)0, (short)32767}) out.writeShort(value);
        }
        new ShortToIntConverter().process(input, output);
        Data.show(input, "short"); Data.show(output, "int");
        Data.expectInts(output, -32768, -1, 0, 32767);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
