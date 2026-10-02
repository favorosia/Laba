package ru.laba.film.server.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/** Фильм вместе с оценками пользователей. */
public final class Movie implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    private final long id;
    private final String title;
    private final int year;
    private final Map<String, Integer> ratings = new LinkedHashMap<>();
    public Movie(long id, String title, int year) { this.id = id; this.title = title; this.year = year; }
    public long id() { return id; }
    public String title() { return title; }
    public int year() { return year; }
    public Map<String, Integer> ratings() { return Map.copyOf(ratings); }
    public void setRating(String username, int rating) { ratings.put(username, rating); }
    public double averageRating() { return ratings.values().stream().mapToInt(Integer::intValue).average().orElse(0.0); }
}
