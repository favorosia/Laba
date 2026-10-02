package ru.laba.attendance;

import java.time.LocalDate;
import java.util.Locale;

/** Одна запись журнала посещаемости. */
public record AttendanceRecord(String student, LocalDate date, Status status) {
    public AttendanceRecord {
        if (student == null || student.isBlank()) throw new IllegalArgumentException("Имя студента не должно быть пустым.");
        student = student.trim();
        if (date == null || status == null) throw new IllegalArgumentException("Дата и статус обязательны.");
    }
    public enum Status {
        PRESENT, ABSENT;
        public static Status fromFileValue(String value) {
            return switch (value.trim().toLowerCase(Locale.ROOT)) {
                case "present" -> PRESENT;
                case "absent" -> ABSENT;
                default -> throw new IllegalArgumentException("Статус должен быть present или absent.");
            };
        }
    }
}
