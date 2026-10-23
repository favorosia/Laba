package task27;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import common.Data;

public class Demo {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("data", "task27");
        Files.createDirectories(dir);
        Path input = dir.resolve("input.bin"), second = dir.resolve("second.bin");
        Path output = dir.resolve("output.bin"), extra = dir.resolve("extra.bin");
        System.out.println("\n27. CharCaseConverter: Заглавные латинские буквы");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(input.toFile()))) {
            for (char value : new char[]{'a', 'Z', 'я', '1', 'z'}) out.writeChar(value);
        }
        new CharCaseConverter().process(input, output);
        Data.show(input, "char"); Data.show(output, "char");
        Data.expect(output, 0, 65, 0, 90, 4, 79, 0, 49, 0, 90);
        System.out.println("Проверка: OK. Файлы: " + dir.toAbsolutePath());
    }
}
