package task10;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Среднее арифметическое double. */
public class DoubleAverageCalculator {
    public void process(Path input, Path output) throws IOException {
        long count = Data.count(input, 8);
        if (count == 0) throw new IOException("Нельзя найти среднее пустого файла");
        double sum = 0;
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long i = 0; i < count; i++) {
                double value = in.readDouble();
                sum += value;
            }
            out.writeDouble(sum / count);
        }
    }
}
