package task48;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task48");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n48. ByteAbsConverter: Модуль знакового byte");
        Data.bytes(input, -5, -128, 0, 127);
        new ByteAbsConverter().process(input, output);
        Data.show(input, "signed"); Data.show(output, "signed");
        Data.expect(output, 5, -128, 0, 127);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
