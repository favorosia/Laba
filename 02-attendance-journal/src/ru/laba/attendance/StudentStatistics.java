package ru.laba.attendance;

/** Итог посещаемости одного студента. */
public record StudentStatistics(String student, int present, int absent) {
    public int total() { return present + absent; }
}
