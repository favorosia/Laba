package task33;

import java.io.*;
import java.nio.file.Path;
import common.Data;

/** Слияние отсортированных int. */
public class SortedIntMerger {
    public void merge(Path first, Path second, Path output) throws IOException {
        long left = Data.count(first, 4), right = Data.count(second, 4);
        try (DataInputStream a = new DataInputStream(new FileInputStream(first.toFile()));
             DataInputStream b = new DataInputStream(new FileInputStream(second.toFile()));
             DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            int x = left > 0 ? a.readInt() : 0, y = right > 0 ? b.readInt() : 0;
            while (left > 0 || right > 0) {
                if (right == 0 || (left > 0 && x <= y)) {
                    out.writeInt(x); left--;
                    if (left > 0) x = a.readInt();
                } else {
                    out.writeInt(y); right--;
                    if (right > 0) y = b.readInt();
                }
            }
        }
    }
}
