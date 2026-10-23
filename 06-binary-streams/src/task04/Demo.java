package task04;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task04");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n04. ByteFilter: Удаление запрещённых байтов");
        Data.bytes(input, 1, 255, 2, 0, 3, 255);
        new ByteFilter(new byte[]{0, (byte)255}).filter(input, output);
        Data.show(input, "byte"); Data.show(output, "byte");
        Data.expect(output, 1, 2, 3);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
