package ru.laba.film.server.service;

import ru.laba.film.server.data.CatalogData;
import ru.laba.film.server.data.CatalogRepository;
import ru.laba.film.server.domain.Movie;
import ru.laba.film.server.domain.UserAccount;
import ru.laba.film.shared.MovieView;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** Бизнес-правила каталога фильмов и пользовательских оценок. */
public final class CatalogService {
    private static final int HASH_ITERATIONS = 120_000;
    private static final int HASH_BITS = 256;
    private static final SecureRandom RANDOM = new SecureRandom();
    private final CatalogRepository repository;
    private final CatalogData data;

    public CatalogService(CatalogRepository repository) throws IOException {
        this.repository = repository;
        this.data = repository.load();
    }

    public synchronized void register(String username, char[] password) throws IOException {
        String cleanName = validateUsername(username);
        String key = userKey(cleanName);
        if (data.users().containsKey(key)) throw new IllegalArgumentException("Это имя пользователя уже занято.");
        validatePassword(password);
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        data.users().put(key, new UserAccount(cleanName, salt, derive(password, salt)));
        persist();
    }

    public synchronized String authenticate(String username, char[] password) {
        String cleanName = validateUsername(username);
        if (password == null || password.length < 8 || password.length > 128) return null;
        UserAccount account = data.users().get(userKey(cleanName));
        if (account == null) return null;
        byte[] candidate = derive(password, account.salt());
        return account.hasPasswordHash(candidate) ? account.username() : null;
    }

    public synchronized MovieView addMovie(String title, int year, String username) throws IOException {
        String cleanTitle = validateTitle(title);
        validateYear(year);
        boolean duplicate = data.movies().stream().anyMatch(movie -> movie.year() == year
                && movie.title().equalsIgnoreCase(cleanTitle));
        if (duplicate) throw new IllegalArgumentException("Этот фильм с таким годом уже есть в каталоге.");
        Movie movie = new Movie(data.takeMovieId(), cleanTitle, year);
        data.movies().add(movie);
        persist();
        return toView(movie, username);
    }

    public synchronized void rate(long movieId, int rating, String username) throws IOException {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Оценка должна быть от 1 до 5.");
        Movie movie = findMovie(movieId);
        movie.setRating(userKey(username), rating);
        persist();
    }

    public synchronized MovieView getMovie(long movieId, String username) {
        return toView(findMovie(movieId), username);
    }

    public synchronized Page list(String query, int offset, int limit, String username) {
        if (offset < 0 || limit < 1 || limit > 100) throw new IllegalArgumentException("Некорректные параметры страницы.");
        String term = query == null ? "" : normalize(query);
        List<Movie> filtered = data.movies().stream()
                .filter(movie -> normalize(movie.title()).contains(term))
                .sorted(Comparator.comparing(Movie::title, String.CASE_INSENSITIVE_ORDER)
                        .thenComparingInt(Movie::year)).toList();
        if (offset >= filtered.size()) return new Page(List.of(), filtered.size());
        int end = (int) Math.min((long) offset + limit, filtered.size());
        List<MovieView> rows = filtered.subList(offset, end).stream().map(movie -> toView(movie, username)).toList();
        return new Page(rows, filtered.size());
    }

    public record Page(List<MovieView> movies, int total) { }

    private Movie findMovie(long id) {
        return data.movies().stream().filter(movie -> movie.id() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Фильм не найден."));
    }

    private MovieView toView(Movie movie, String username) {
        var ratings = movie.ratings();
        return new MovieView(movie.id(), movie.title(), movie.year(), movie.averageRating(), ratings.size(),
                ratings.getOrDefault(userKey(username), 0));
    }

    private void persist() throws IOException { repository.save(data); }

    private static String validateUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Имя пользователя обязательно.");
        String value = username.trim();
        if (!value.matches("[\\p{L}\\p{N}_.-]{3,32}")) {
            throw new IllegalArgumentException("Имя: 3–32 буквы, цифры, точка, дефис или подчёркивание.");
        }
        return value;
    }

    private static void validatePassword(char[] password) {
        if (password == null || password.length < 8 || password.length > 128) {
            throw new IllegalArgumentException("Пароль должен содержать от 8 до 128 символов.");
        }
    }

    private static String validateTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Название фильма не должно быть пустым.");
        String value = title.trim();
        if (value.length() > 160) throw new IllegalArgumentException("Название фильма не должно превышать 160 символов.");
        return value;
    }

    private static void validateYear(int year) {
        int currentYear = Year.now().getValue();
        if (year < 1888 || year > currentYear + 3) throw new IllegalArgumentException("Год должен быть от 1888 до " + (currentYear + 3) + ".");
    }

    private static byte[] derive(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, HASH_ITERATIONS, HASH_BITS);
        try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
        catch (GeneralSecurityException e) { throw new IllegalStateException("Алгоритм защиты пароля недоступен.", e); }
        finally { spec.clearPassword(); }
    }

    private static String userKey(String username) { return normalize(username); }
    private static String normalize(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFKC).toLowerCase(Locale.ROOT).trim();
    }
}
