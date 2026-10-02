package ru.laba.attendance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Читает журнал, считает статистику и сохраняет отчёт. */
public final class AttendanceService {
    public List<AttendanceRecord> readRecords(Path path) throws IOException {
        List<AttendanceRecord> records = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;
                if (lineNumber == 1 && line.trim().equalsIgnoreCase("student;date;status")) continue;
                try {
                    records.add(parseRecord(line));
                } catch (IllegalArgumentException e) {
                    errors.add("Строка " + lineNumber + ": " + e.getMessage());
                }
            }
        }
        if (!errors.isEmpty()) throw new IOException("В файле найдены некорректные записи:\n- " + String.join("\n- ", errors));
        return List.copyOf(records);
    }

    private AttendanceRecord parseRecord(String line) {
        String[] fields = line.split(";", -1);
        if (fields.length != 3) throw new IllegalArgumentException("ожидается формат student;date;status.");
        try {
            return new AttendanceRecord(fields[0], LocalDate.parse(fields[1].trim()),
                    AttendanceRecord.Status.fromFileValue(fields[2]));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("дата должна быть в формате yyyy-MM-dd.");
        }
    }

    public List<StudentStatistics> calculate(List<AttendanceRecord> records) {
        Map<String, int[]> totals = new LinkedHashMap<>();
        for (AttendanceRecord record : records) {
            int[] counts = totals.computeIfAbsent(record.student(), ignored -> new int[2]);
            counts[record.status() == AttendanceRecord.Status.PRESENT ? 0 : 1]++;
        }
        List<StudentStatistics> result = new ArrayList<>();
        totals.forEach((student, counts) -> result.add(new StudentStatistics(student, counts[0], counts[1])));
        return result;
    }

    public void writeReport(Path path, List<StudentStatistics> statistics, SortOrder order,
                            int totalPresent, int totalAbsent) throws IOException {
        Comparator<StudentStatistics> comparator = order == SortOrder.NAME
                ? Comparator.comparing(StudentStatistics::student, String.CASE_INSENSITIVE_ORDER)
                : Comparator.comparingInt(StudentStatistics::absent)
                        .thenComparing(StudentStatistics::student, String.CASE_INSENSITIVE_ORDER);
        List<StudentStatistics> sorted = statistics.stream().sorted(comparator).toList();
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("student;present;absent;total");
            writer.newLine();
            for (StudentStatistics student : sorted) {
                writer.write(student.student() + ";" + student.present() + ";" + student.absent() + ";" + student.total());
                writer.newLine();
            }
            writer.write("TOTAL;" + totalPresent + ";" + totalAbsent + ";" + (totalPresent + totalAbsent));
            writer.newLine();
        }
    }

    public enum SortOrder { NAME, ABSENCES }
}
