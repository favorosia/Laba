import common.Data;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

/** Проверки, которые не нужны для сдачи или запуска одного варианта. */
public class EdgeCases {
    private static int checks;
    private static Path dir;
    private static Path a, b, out, other;

    @FunctionalInterface
    private interface Action { void run() throws Exception; }

    private static void fails(Class < ? extends Throwable > expected, Action action) throws Exception {
        try { action.run(); }
        catch (Throwable error) {
            if (!expected.isInstance(error)) throw new AssertionError("Не тот тип ошибки", error);
            checks++;
            return;
        }
        throw new AssertionError("Ожидалась ошибка " + expected.getSimpleName());
    }

    private static void bytes(int... expected) throws IOException {
        Data.expect(out, expected); checks++;
    }
    private static void ints(int... expected) throws IOException {
        Data.expectInts(out, expected); checks++;
    }
    private static void doubles(double... expected) throws IOException {
        Data.expectDoubles(out, expected); checks++;
    }
    private static void require(boolean value) {
        if (!value) throw new AssertionError("Проверка не пройдена");
        checks++;
    }

    public static void main(String[] args) throws Exception {
        dir = Path.of("data", "edge-cases"); Files.createDirectories(dir);
        a = dir.resolve("a.bin"); b = dir.resolve("b.bin");
        out = dir.resolve("out.bin"); other = dir.resolve("other.bin");
        emptyInputs(); parameters(); boundaries(); malformed(); patterns();
        System.out.println("Граничные проверки: " + checks + " OK");
    }

    private static void emptyInputs() throws Exception {
        Data.bytes(a); Data.bytes(b);
        new task01.ChunkFileCopier(3).copy(a, out); bytes();
        new task02.ByteAlternatingMerger(3).merge(a, b, out); bytes();
        new task03.PositionSplitter().split(a, out, other); bytes(); Data.expect(other);
        new task04.ByteFilter(new byte[]{0}).filter(a, out); bytes();
        new task05.ByteReplacer((byte) 0, (byte) 1).replace(a, out); bytes();
        new task06.ByteHalver().process(a, out); bytes();
        new task07.MultiKeyXorCipher(new byte[]{1}).transform(a, out); bytes();
        new task08.BitInverter().process(a, out); bytes();
        new task09.PositiveIntFilter().process(a, out); bytes();
        fails(IOException.class, () -> new task10.DoubleAverageCalculator().process(a, out));
        new task11.ByteDuplicator().process(a, out); bytes();
        new task12.IndexMultiplier().process(a, out); bytes();
        fails(IOException.class, () -> new task13.ByteMinMaxFinder().find(a, out));
        new task14.ZeroByteCounter().count(a, out); ints(0);
        new task15.EvenIndexByteRemover().remove(a, out); bytes();
        new task16.ByteReverser().reverse(a, out); bytes();
        new task17.BytePairSwapper().process(a, out); bytes();
        new task18.ByteSorter().sort(a, out); bytes();
        new task19.ConsecutiveDuplicateRemover().remove(a, out); bytes();
        new task20.RleEncoder().encode(a, out); bytes();
        new task20.RleEncoder().decode(a, out); bytes();
        new task21.AdjacentByteDifferencer().process(a, out); bytes();
        task22.IntArrayWriter iw = new task22.IntArrayWriter(); iw.write(new int[0], out);
        require(iw.read(out).length == 0); ints(0);
        new task23.IntArraySumReader().process(a, out); ints(0);
        fails(IOException.class, () -> new task24.DoubleMinMaxReader().process(a, out));
        new task25.DoubleRangeFilter(0, 1).process(a, out); bytes();
        new task26.BytePairSorter().process(a, out); bytes();
        new task27.CharCaseConverter().process(a, out); bytes();
        new task28.RunningMaximumWriter().process(a, out); bytes();
        new task29.BooleanArrayWriter().write(new boolean[0], out);
        require(new task29.BooleanArrayReader().read(out).length == 0); ints(0);
        new task30.BooleanStatsReader().process(a, out); ints(0, 0);
        new task31.ShortToIntConverter().process(a, out); bytes();
        new task32.ByteCircularShifter(3).shift(a, out); bytes();
        new task33.SortedIntMerger().merge(a, b, out); bytes();
        new task34.IntEvenOddSplitter().split(a, out, other); bytes(); Data.expect(other);
        task35.MatrixWriter mw = new task35.MatrixWriter(); mw.write(new int[0][0], out);
        require(mw.read(out).length == 0); ints(0, 0);
        new task36.EvenByteFilter().filter(a, out); bytes();
        new task37.BitwiseAndMerger().merge(a, b, out); bytes();
        new task38.BitwiseOrMerger().merge(a, b, out); bytes();
        new task39.BinaryDiffWriter().write(a, b, out); bytes();
        new task40.BinaryFileInserter(0).insert(a, b, out); bytes();
        fails(IOException.class, () -> new task41.ByteRangeRemover(0, 0).remove(a, out));
        fails(IOException.class, () -> new task42.ByteRangeExtractor(0, 0).extract(a, out));
        new task43.BytePatternCounter(new byte[]{1}).count(a, out); ints(0);
        new task44.LongestRunFinder().find(a, out); bytes(0, 0, 0, 0, 0);
        task45.StringArrayBinaryWriter sw = new task45.StringArrayBinaryWriter(); sw.write(new String[0], out);
        require(sw.read(out).length == 0); ints(0);
        new task46.NthByteRemover(3).remove(a, out); bytes();
        new task47.CaesarByteCipher(-5).encrypt(a, out); bytes();
        new task47.CaesarByteCipher(-5).decrypt(a, out); bytes();
        new task48.ByteAbsConverter().process(a, out); bytes();
        Path[] parts = new task49.FilePartSplitter(3).split(a, dir.resolve("empty-parts"));
        require(parts.length == 3);
        for (Path part : parts) { Data.expect(part); checks++; }
        new task50.NormalizedHistogramWriter().write(a, out);
        try (DataInputStream in = new DataInputStream(new FileInputStream(out.toFile()))) {
            for (int i = 0; i < 256; i++) require(in.readFloat() == 0.0f);
            require(in.read() ==  -1);
        }
    }

    private static void parameters() throws Exception {
        fails(IllegalArgumentException.class, () -> new task01.ChunkFileCopier(0));
        fails(IllegalArgumentException.class, () -> new task02.ByteAlternatingMerger(-1));
        fails(IllegalArgumentException.class, () -> new task07.MultiKeyXorCipher(new byte[0]));
        fails(IllegalArgumentException.class, () -> new task25.DoubleRangeFilter(2, 1));
        fails(IllegalArgumentException.class, () -> new task25.DoubleRangeFilter(Double.NaN, 1));
        fails(IllegalArgumentException.class, () -> new task32.ByteCircularShifter(-1));
        fails(IllegalArgumentException.class, () -> new task40.BinaryFileInserter(-1));
        fails(IllegalArgumentException.class, () -> new task41.ByteRangeRemover(3, 2));
        fails(IllegalArgumentException.class, () -> new task42.ByteRangeExtractor(-1, 0));
        fails(IllegalArgumentException.class, () -> new task43.BytePatternCounter(new byte[0]));
        fails(IllegalArgumentException.class, () -> new task46.NthByteRemover(0));
        fails(IllegalArgumentException.class, () -> new task49.FilePartSplitter(0));
        fails(IllegalArgumentException.class, () -> new task35.MatrixWriter().write(new int[][]{{1}, {2, 3}}, out));
    }

    private static void boundaries() throws Exception {
        int[] all = new int[256];
        for (int i = 0; i < 256; i++) all[i] = i;
        Data.bytes(a, all);
        for (int block : new int[]{1, 3, 256, 1000}) {
            new task01.ChunkFileCopier(block).copy(a, out); bytes(all);
        }
        new task08.BitInverter().process(a, out);
        new task08.BitInverter().process(out, other); Data.expect(other, all); checks++;
        new task07.MultiKeyXorCipher(new byte[]{0, -1, 9}).transform(a, out);
        new task07.MultiKeyXorCipher(new byte[]{0, -1, 9}).transform(out, other);
        Data.expect(other, all); checks++;
        for (int shift : new int[]{0, 1, 255, 256, -1, Integer.MIN_VALUE, Integer.MAX_VALUE}) {
            task47.CaesarByteCipher cipher = new task47.CaesarByteCipher(shift);
            cipher.encrypt(a, out); cipher.decrypt(out, other); Data.expect(other, all); checks++;
        }
        new task06.ByteHalver().process(a, out);
        int[] halved = new int[256]; for (int i = 0; i < 256; i++) halved[i] = i / 2;
        bytes(halved);
        new task48.ByteAbsConverter().process(a, out);
        int[] abs = new int[256]; for (int i = 0; i < 256; i++) abs[i] = (byte) Math.abs((int) (byte) i);
        bytes(abs);
        new task50.NormalizedHistogramWriter().write(a, out);
        try (DataInputStream in = new DataInputStream(new FileInputStream(out.toFile()))) {
            for (int i = 0; i < 256; i++) require(in.readFloat() == 1.0f / 256);
            require(in.read() ==  -1);
        }
        Data.bytes(a, 1, 2, 3); Data.bytes(b);
        new task02.ByteAlternatingMerger(2).merge(a, b, out); bytes(1, 2, 3);
        new task02.ByteAlternatingMerger(2).merge(b, a, out); bytes(1, 2, 3);
        new task39.BinaryDiffWriter().write(a, b, out); bytes(1, 2, 3);
        new task39.BinaryDiffWriter().write(b, a, out); bytes(1, 2, 3);
        new task04.ByteFilter(new byte[0]).filter(a, out); bytes(1, 2, 3);
        new task04.ByteFilter(new byte[]{1, 2, 3}).filter(a, out); bytes();
        new task46.NthByteRemover(1).remove(a, out); bytes();
        new task46.NthByteRemover(100).remove(a, out); bytes(1, 2, 3);
        new task32.ByteCircularShifter(0).shift(a, out); bytes(1, 2, 3);
        new task32.ByteCircularShifter(3).shift(a, out); bytes(1, 2, 3);
        new task40.BinaryFileInserter(0).insert(a, a, out); bytes(1, 2, 3, 1, 2, 3);
        new task40.BinaryFileInserter(3).insert(a, a, out); bytes(1, 2, 3, 1, 2, 3);
        new task41.ByteRangeRemover(0, 2).remove(a, out); bytes();
        new task42.ByteRangeExtractor(0, 2).extract(a, out); bytes(1, 2, 3);
        Path[] parts = new task49.FilePartSplitter(5).split(a, dir.resolve("many-parts"));
        require(parts.length == 5);
        Data.expect(parts[0], 1); Data.expect(parts[1], 2); Data.expect(parts[2], 3);
        Data.expect(parts[3]); Data.expect(parts[4]); checks += 5;
        new task20.RleEncoder().encode(a, out); new task20.RleEncoder().decode(out, other);
        Data.expect(other, 1, 2, 3); checks++;
        Data.bytes(a, 255);
        new task17.BytePairSwapper().process(a, out); bytes(255);
        new task26.BytePairSorter().process(a, out); bytes(255);
        new task21.AdjacentByteDifferencer().process(a, out); bytes();
        new task13.ByteMinMaxFinder().find(a, out); bytes(255, 255);
        new task44.LongestRunFinder().find(a, out); bytes(0, 0, 0, 1, 255);
        Data.bytes(a, 7, 7, 8, 8); new task44.LongestRunFinder().find(a, out); bytes(0, 0, 0, 2, 7);
        Data.ints(a, Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE); Data.ints(b);
        new task33.SortedIntMerger().merge(a, b, out); ints(Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE);
        new task33.SortedIntMerger().merge(b, a, out); ints(Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE);
        Data.doubles(a, -1, 0, 1);
        new task25.DoubleRangeFilter(0, 0).process(a, out); doubles(0);
        Data.doubles(a, 5);
        new task10.DoubleAverageCalculator().process(a, out); doubles(5);
        new task24.DoubleMinMaxReader().process(a, out); doubles(5, 5);
        Data.bytes(a, new int[1024]); new task16.ByteReverser().reverse(a, out); require(Files.size(out) == 1024);
        new task18.ByteSorter().sort(a, out); require(Files.size(out) == 1024);
        Data.bytes(a, new int[1025]);
        fails(IOException.class, () -> new task16.ByteReverser().reverse(a, out));
        fails(IOException.class, () -> new task18.ByteSorter().sort(a, out));
        // Проверяем восстановление всех символов, включая кириллицу и NUL.
        String[] strings = {"", "я", "a\u0000b", "🌸"};
        task45.StringArrayBinaryWriter sw = new task45.StringArrayBinaryWriter(); sw.write(strings, out);
        require(Arrays.equals(strings, sw.read(out)));
    }

    private static void malformed() throws Exception {
        Data.bytes(a, 1); Data.bytes(b);
        fails(IOException.class, () -> new task09.PositiveIntFilter().process(a, out));
        fails(IOException.class, () -> new task10.DoubleAverageCalculator().process(a, out));
        fails(IOException.class, () -> new task12.IndexMultiplier().process(a, out));
        fails(IOException.class, () -> new task23.IntArraySumReader().process(a, out));
        fails(IOException.class, () -> new task24.DoubleMinMaxReader().process(a, out));
        fails(IOException.class, () -> new task25.DoubleRangeFilter(0, 1).process(a, out));
        fails(IOException.class, () -> new task27.CharCaseConverter().process(a, out));
        fails(IOException.class, () -> new task28.RunningMaximumWriter().process(a, out));
        fails(IOException.class, () -> new task31.ShortToIntConverter().process(a, out));
        fails(IOException.class, () -> new task33.SortedIntMerger().merge(a, b, out));
        fails(IOException.class, () -> new task34.IntEvenOddSplitter().split(a, out, other));
        fails(IOException.class, () -> new task20.RleEncoder().decode(a, out));
        fails(IOException.class, () -> new task37.BitwiseAndMerger().merge(a, b, out));
        fails(IOException.class, () -> new task38.BitwiseOrMerger().merge(a, b, out));
        fails(IOException.class, () -> new task40.BinaryFileInserter(2).insert(a, b, out));
        fails(IOException.class, () -> new task41.ByteRangeRemover(0, 1).remove(a, out));
        fails(IOException.class, () -> new task42.ByteRangeExtractor(0, 1).extract(a, out));
        Data.ints(a, -1);
        fails(IOException.class, () -> new task22.IntArrayWriter().read(a));
        fails(IOException.class, () -> new task29.BooleanArrayReader().read(a));
        fails(IOException.class, () -> new task45.StringArrayBinaryWriter().read(a));
        Data.ints(a, 2, 1);
        fails(IOException.class, () -> new task22.IntArrayWriter().read(a));
        Data.ints(a, 2, 3, 1);
        fails(IOException.class, () -> new task35.MatrixWriter().read(a));
        Data.bytes(a, 7, 0, 0, 0, 0);
        fails(IOException.class, () -> new task20.RleEncoder().decode(a, out));
    }

    private static void patterns() throws Exception {
        // Самоперекрывающийся префикс: сброс индекса при неудаче потерял бы совпадение.
        Data.bytes(a, 1, 1, 1, 2, 1, 1, 2);
        new task43.BytePatternCounter(new byte[]{1, 1, 2}).count(a, out); ints(2);
        Data.bytes(a, 255, 255, 255);
        new task43.BytePatternCounter(new byte[]{-1, -1}).count(a, out); ints(1);
        new task43.BytePatternCounter(new byte[]{-1}).count(a, out); ints(3);
        new task43.BytePatternCounter(new byte[]{1, 2, 3, 4}).count(a, out); ints(0);
        Random random = new Random(42);
        for (int sample = 0; sample < 100; sample++) {
            int[] values = new int[random.nextInt(50)];
            byte[] pattern = new byte[1 + random.nextInt(5)];
            for (int i = 0; i < values.length; i++) values[i] = random.nextInt(3);
            for (int i = 0; i < pattern.length; i++) pattern[i] = (byte) random.nextInt(3);
            int expected = 0, position = 0;
            // Эталон проверяет исходный массив, а решение работает с потоком и окном.
            while (position + pattern.length <= values.length) {
                boolean same = true;
                for (int j = 0; j < pattern.length; j++) if (values[position + j] != pattern[j]) same = false;
                if (same) { expected++; position += pattern.length; } else position++;
            }
            Data.bytes(a, values); new task43.BytePatternCounter(pattern).count(a, out); ints(expected);
        }
    }
}
