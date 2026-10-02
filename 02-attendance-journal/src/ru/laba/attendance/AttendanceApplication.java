package ru.laba.attendance;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/** Консольное приложение журнала посещаемости. */
public final class AttendanceApplication {
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private final AttendanceService service = new AttendanceService();

    public static void main(String[] args) { new AttendanceApplication().run(args); }

    private void run(String[] args) {
        out.println("=== Журнал посещаемости ===");
        try {
            Path input = args.length > 0 ? Path.of(args[0]) : readPath("Путь к журналу [attendance.csv]: ", "attendance.csv");
            List<AttendanceRecord> records = service.readRecords(input);
            List<StudentStatistics> statistics = service.calculate(records);
            printStatistics(records, statistics);
            Path output = args.length > 1 ? Path.of(args[1]) : readPath("Файл отчёта [attendance-report.csv]: ", "attendance-report.csv");
            if (input.toAbsolutePath().normalize().equals(output.toAbsolutePath().normalize())) {
                throw new IllegalArgumentException("Файл отчёта должен отличаться от исходного журнала.");
            }
            AttendanceService.SortOrder order = args.length > 2 ? parseSortOrder(args[2]) : readSortOrder();
            int present = (int) records.stream().filter(r -> r.status() == AttendanceRecord.Status.PRESENT).count();
            int absent = records.size() - present;
            service.writeReport(output, statistics, order, present, absent);
            out.println("Отчёт сохранён: " + output.toAbsolutePath());
        } catch (Exception e) {
            out.println("Не удалось сформировать отчёт: " + e.getMessage());
        }
    }

    private void printStatistics(List<AttendanceRecord> records, List<StudentStatistics> statistics) {
        int present = (int) records.stream().filter(r -> r.status() == AttendanceRecord.Status.PRESENT).count();
        int absent = records.size() - present;
        out.printf("Всего записей: %d | присутствий: %d | пропусков: %d%n", records.size(), present, absent);
        out.println("\nПосещаемость по студентам:");
        if (statistics.isEmpty()) out.println("Записей нет.");
        for (StudentStatistics student : statistics) {
            out.printf("%-24s посещений: %d | пропусков: %d%n", student.student(), student.present(), student.absent());
        }
    }

    private Path readPath(String prompt, String defaultValue) {
        out.print(prompt);
        String value = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        return Path.of(value.isEmpty() ? defaultValue : value);
    }

    private AttendanceService.SortOrder readSortOrder() {
        while (true) {
            out.print("Сортировка отчёта (1 — по имени, 2 — по числу пропусков): ");
            String input = scanner.hasNextLine() ? scanner.nextLine().trim() : "1";
            try { return parseSortOrder(input); }
            catch (IllegalArgumentException e) { out.println(e.getMessage()); }
        }
    }

    private AttendanceService.SortOrder parseSortOrder(String value) {
        return switch (value.trim()) {
            case "1", "name" -> AttendanceService.SortOrder.NAME;
            case "2", "absences" -> AttendanceService.SortOrder.ABSENCES;
            default -> throw new IllegalArgumentException("Выберите 1 (имя) или 2 (пропуски).");
        };
    }
}
