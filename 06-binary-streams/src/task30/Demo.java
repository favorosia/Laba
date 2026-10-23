package task30;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task30");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n30. BooleanStatsReader: Статистика true и false");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(input.toFile()))) {
            for (boolean value : new boolean[]{true, false, true, false, true}) out.writeBoolean(value);
        }
        new BooleanStatsReader().process(input, output);
        Data.show(input, "boolean"); Data.show(output, "int");
        Data.expectInts(output, 3, 2);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
