package task44;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task44");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n44. LongestRunFinder: Самая длинная серия");
        Data.bytes(input, 1, 1, 7, 7, 7, 2);
        new LongestRunFinder().find(input, output); Data.show(input, "byte");
        try (DataInputStream in = new DataInputStream(new FileInputStream(output.toFile()))) {
            System.out.println("Длина=" + in.readInt() + ", значение=" + in.readUnsignedByte());
        }
        Data.expect(output, 0, 0, 0, 3, 7);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
