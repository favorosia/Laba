package ru.laba.films.service;

import ru.laba.films.data.ActorRepository;
import ru.laba.films.data.MovieRepository;
import ru.laba.films.domain.Actor;
import ru.laba.films.domain.FilmRole;
import ru.laba.films.domain.Movie;

import java.sql.SQLException;
import java.time.Year;
import java.util.List;

/** Бизнес-правила каталога; консольный слой не обращается к SQL напрямую. */
public final class FilmCatalogService {
    private final MovieRepository movies;
    private final ActorRepository actors;

    public FilmCatalogService(MovieRepository movies, ActorRepository actors) {
        this.movies = movies;
        this.actors = actors;
    }

    public List<Movie> movies(int offset, int limit) throws SQLException {
        checkPage(offset, limit);
        return movies.findPage(limit, offset);
    }
    public int movieCount() throws SQLException { return movies.count(); }
    public Movie addMovie(String title, int year) throws SQLException {
        return movies.add(required(title, "Название фильма", 200), checkYear(year));
    }
    public void deleteMovie(long id) throws SQLException {
        if (!movies.delete(checkId(id))) throw new IllegalArgumentException("Фильм с таким номером не найден.");
    }
    public void changeMovieYear(long id, int year) throws SQLException {
        if (!movies.updateYear(checkId(id), checkYear(year))) throw new IllegalArgumentException("Фильм с таким номером не найден.");
    }
    public List<Actor> actors() throws SQLException { return actors.findAll(); }
    public Actor addActor(String name, int birthYear) throws SQLException {
        if (birthYear < 1850 || birthYear > Year.now().getValue()) {
            throw new IllegalArgumentException("Год рождения актёра должен быть от 1850 до текущего года.");
        }
        return actors.add(required(name, "ФИО актёра", 180), birthYear);
    }
    public void deleteActor(long id) throws SQLException {
        if (!actors.delete(checkId(id))) throw new IllegalArgumentException("Актёр с таким номером не найден.");
    }
    public List<Actor> actorsWithoutFilms() throws SQLException { return actors.findWithoutFilms(); }
    public void assignRole(long actorId, long movieId, String role) throws SQLException {
        String name = required(role, "Название роли", 120);
        if (!actors.assignRole(checkId(actorId), checkId(movieId), name)) {
            throw new IllegalArgumentException("Такое назначение роли уже существует.");
        }
    }
    public void removeRole(long actorId, long movieId, String role) throws SQLException {
        if (!actors.removeRole(checkId(actorId), checkId(movieId), required(role, "Название роли", 120))) {
            throw new IllegalArgumentException("Такое назначение роли не найдено.");
        }
    }
    public List<Movie> filmsForActor(long actorId) throws SQLException { return movies.findByActor(checkId(actorId)); }
    public int roleCount(long actorId) throws SQLException { return actors.countRoles(checkId(actorId)); }
    public List<FilmRole> rolesForActor(long actorId) throws SQLException { return actors.findRoles(checkId(actorId)); }

    private static long checkId(long id) {
        if (id < 1) throw new IllegalArgumentException("Номер должен быть положительным.");
        return id;
    }
    private static int checkYear(int year) {
        if (year < 1888 || year > Year.now().getValue() + 3) {
            throw new IllegalArgumentException("Год выпуска должен быть от 1888 до " + (Year.now().getValue() + 3) + ".");
        }
        return year;
    }
    private static String required(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " не должно быть пустым.");
        String clean = value.trim();
        if (clean.length() > maxLength) throw new IllegalArgumentException(field + " не должно превышать " + maxLength + " символов.");
        return clean;
    }
    private static void checkPage(int offset, int limit) {
        if (offset < 0 || limit < 1 || limit > 100) throw new IllegalArgumentException("Страница: строки 1–100, смещение неотрицательное.");
    }
}
