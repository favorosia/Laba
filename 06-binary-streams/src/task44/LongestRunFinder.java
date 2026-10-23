package task44;

import java.io.*;
import java.nio.file.Path;

/** Самая длинная серия. */
public class LongestRunFinder {
    public void find(Path input, Path output) throws IOException {
        int previous = -1, length = 0, bestLength = 0, bestValue = 0;
        try (InputStream in = new FileInputStream(input.toFile())) {
            int value;
            while ((value = in.read()) != -1) {
                length = value == previous ? Math.addExact(length, 1) : 1;
                previous = value;
                if (length > bestLength) { bestLength = length; bestValue = value; }
            }
        }
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            out.writeInt(bestLength); out.writeByte(bestValue);
        }
    }
}
