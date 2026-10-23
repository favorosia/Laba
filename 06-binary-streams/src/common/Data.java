package common;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
/** Проверка формата и вспомогательные операции демонстраций. */
public final class Data {
    private Data() { }
    public static long count(Path p, int width) throws IOException {
        long size = Files.size(p);
        if (size % width != 0) throw new IOException("Неполная запись: размер не кратен " + width);
        return size / width;
    }

    public static void bytes(Path p, int... values) throws IOException {
        try (OutputStream out = new FileOutputStream(p.toFile())) {
            for (int v : values) out.write(v);
        }
    }

    public static void ints(Path p, int... values) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(p.toFile()))) {
            for (int v : values) out.writeInt(v);
        }
    }

    public static void doubles(Path p, double... values) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(p.toFile()))) {
            for (double v : values) out.writeDouble(v);
        }
    }

    public static void show(Path p, String type) throws IOException {
        System.out.print(p.getFileName() + " = [");
        int width = type.equals("double") ? 8 : (type.equals("int") || type.equals("float") ? 4 : (type.equals("char") || type.equals("short") ? 2 : 1));
        long count = count(p, width);
        try (DataInputStream in = new DataInputStream(new FileInputStream(p.toFile()))) {
            for (long i = 0; i < count; i++) {
                if (i > 0) System.out.print(", ");
                switch (type) {
                    case "int" : System.out.print(in.readInt()); break;
                    case "double" : System.out.print(in.readDouble()); break;
                    case "float" : System.out.print(in.readFloat()); break;
                    case "char" : System.out.print(in.readChar()); break;
                    case "short" : System.out.print(in.readShort()); break;
                    case "boolean" : System.out.print(in.readBoolean()); break;
                    case "signed" : System.out.print(in.readByte()); break;
                    default : System.out.print(in.readUnsignedByte());
                }
            }
        }
        System.out.println("]");
    }

    public static void expect(Path p, int... expected) throws IOException {
        try (InputStream in = new FileInputStream(p.toFile())) {
            for (int v : expected) if (in.read() != (v & 255)) throw new AssertionError("Неверный байт: " + p);
            if (in.read() != -1) throw new AssertionError("Лишние данные: " + p);
        }
    }

    public static void expectInts(Path p, int... expected) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(p.toFile()))) {
            for (int v : expected) if (in.readInt() != v) throw new AssertionError("Неверный int: " + p);
            if (in.read() != -1) throw new AssertionError("Лишние данные: " + p);
        }
    }

    public static void expectDoubles(Path p, double... expected) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(p.toFile()))) {
            for (double v : expected) if (Double.compare(in.readDouble(), v) != 0) throw new AssertionError("Неверный double: " + p);
            if (in.read() != -1) throw new AssertionError("Лишние данные: " + p);
        }
    }
}
