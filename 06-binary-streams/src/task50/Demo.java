package task50;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task50");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n50. NormalizedHistogramWriter: Нормированная гистограмма");
        Data.bytes(input, 0, 0, 1, 255);
        new NormalizedHistogramWriter().write(input, output); Data.show(input, "byte");
        try (DataInputStream in = new DataInputStream(new FileInputStream(output.toFile()))) {
            float sum = 0;
            for (int i = 0; i < 256; i++) {
                float value = in.readFloat(); sum += value;
                float expected = i == 0 ? 0.5f : (i == 1 || i == 255 ? 0.25f : 0.0f);
                if (value != expected) throw new AssertionError("Неверная частота " + i);
                if (value > 0) System.out.println("P[" + i + "] = " + value);
            }
            if (sum != 1.0f || in.read() != -1) throw new AssertionError("Неверная гистограмма");
        }
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
