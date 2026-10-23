package task25;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Фильтрация double по диапазону. */
public class DoubleRangeFilter {
    private final double min, max;

    public DoubleRangeFilter(double min, double max) {
        if (Double.isNaN(min) || Double.isNaN(max) || min > max) throw new IllegalArgumentException("Неверный диапазон");
        this.min = min; this.max = max;
    }

    public void process(Path input, Path output) throws IOException {
        long count = Data.count(input, 8);

        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            for (long i = 0; i < count; i++) {
                double value = in.readDouble();
                if (value >= min && value <= max) out.writeDouble(value);
            }

        }
    }
}
