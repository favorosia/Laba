package ru.laba.films.domain;

/** Сведения о фильме и роли актёра в нём. */
public record FilmRole(Movie movie, String roleName) { }
