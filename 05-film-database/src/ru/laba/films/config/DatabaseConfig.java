package ru.laba.films.config;

/** Параметры подключения к PostgreSQL из переменных среды или приглашения. */
public record DatabaseConfig(String url, String username, String password) {
    public DatabaseConfig {
        if (url == null || !url.startsWith("jdbc:postgresql://")) {
            throw new IllegalArgumentException("Адрес должен начинаться с jdbc:postgresql://.");
        }
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Имя пользователя БД обязательно.");
        password = password == null ? "" : password;
    }

    public static DatabaseConfig fromEnvironment(String enteredPassword) {
        String url = System.getenv().getOrDefault("FILM_DB_URL", "jdbc:postgresql://localhost:5432/film_catalog");
        String username = System.getenv().getOrDefault("FILM_DB_USER", "postgres");
        String password = System.getenv("FILM_DB_PASSWORD");
        if (password == null) password = enteredPassword;
        return new DatabaseConfig(url, username, password);
    }
}
