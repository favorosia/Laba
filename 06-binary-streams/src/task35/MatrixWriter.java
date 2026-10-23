package task35;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

/** Матрица int. */
public class MatrixWriter {
    public void write(int[][] matrix, Path output) throws IOException {
        int rows = matrix.length, columns = rows == 0 ? 0 : matrix[0].length;
        for (int[] row : matrix) if (row.length != columns) throw new IllegalArgumentException("Матрица должна быть прямоугольной");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output.toFile()))) {
            out.writeInt(rows); out.writeInt(columns);
            for (int[] row : matrix) for (int value : row) out.writeInt(value);
        }
    }

    public int[][] read(Path input) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(input.toFile()))) {
            int rows = in.readInt(), columns = in.readInt();
            long size = Files.size(input);
            if (rows < 0 || columns < 0 || size < 8 || (size - 8) % 4 != 0 || (long)rows * columns != (size - 8) / 4) {
                throw new IOException("Неверные размеры матрицы");
            }
            int[][] matrix = new int[rows][columns];
            for (int i = 0; i < rows; i++) for (int j = 0; j < columns; j++) matrix[i][j] = in.readInt();
            return matrix;
        }
    }
}
