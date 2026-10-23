package task20;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task20");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n20. RleEncoder: Сжатие серий RLE");
        Data.bytes(input, 5, 5, 5, 255, 255, 7);
        RleEncoder codec = new RleEncoder(); codec.encode(input, output);
        try (DataInputStream in = new DataInputStream(new FileInputStream(output.toFile()))) {
            for (long i = 0; i < Data.count(output, 5); i++) {
                System.out.println("Значение=" + in.readUnsignedByte() + ", повторов=" + in.readInt());
            }
        }
        codec.decode(output, extra); Data.show(input, "byte"); Data.show(extra, "byte");
        Data.expect(extra, 5, 5, 5, 255, 255, 7);
        Data.expect(output, 5, 0, 0, 0, 3, 255, 0, 0, 0, 2, 7, 0, 0, 0, 1);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
