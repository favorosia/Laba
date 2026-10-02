package ru.laba.film.shared;

/** Данные о фильме, передаваемые от сервера клиенту. */
public record MovieView(long id, String title, int year, double averageRating, int ratingCount, int myRating) { }
