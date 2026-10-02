package ru.laba.film.client.presentation;

import ru.laba.film.client.data.TcpFilmConnection;
import ru.laba.film.client.service.FilmClientService;
import ru.laba.film.shared.JulLogging;
import ru.laba.film.shared.MovieView;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Консольный клиент каталога фильмов. */
public final class ClientMain {
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private final Logger logger = JulLogging.configure("client");
    private FilmClientService service;
    private int pageSize = 10;

    public static void main(String[] args) { new ClientMain().run(args); }

    private void run(String[] args) {
        try {
            String host = args.length > 0 ? args[0] : readText("Адрес сервера [127.0.0.1]: ", "127.0.0.1");
            int port = args.length > 1 ? parsePort(args[1]) : readInt("TCP-порт [5050]: ", 1, 65535, 5050);
            try (FilmClientService client = new FilmClientService(new TcpFilmConnection(host, port, logger))) {
                service = client;
                if (!login()) return;
                menuLoop();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Сетевая операция не выполнена", e);
            out.println("Не удалось связаться с сервером: " + e.getMessage());
            out.println("Проверьте, что сервер запущен и адрес с портом указаны верно.");
        } catch (IllegalArgumentException e) {
            out.println("Неверный номер порта: " + e.getMessage());
        } catch (EndOfInput e) {
            out.println("Ввод завершён.");
        }
    }

    private boolean login() throws IOException {
        while (true) {
            out.println("\n1. Войти   2. Создать учётную запись   0. Выход");
            int action = readInt("Выбор: ", 0, 2, -1);
            if (action == 0) return false;
            String username = readText("Имя пользователя: ", null);
            String password = readText("Пароль (не менее 8 символов): ", null);
            try {
                String message = service.authenticate(action == 2, username, password);
                logger.info(() -> "Пользователь авторизован: " + username);
                out.println(message);
                return true;
            } catch (IOException e) {
                out.println("Не удалось войти: " + e.getMessage());
            }
        }
    }

    private void menuLoop() throws IOException {
        while (true) {
            out.println("\n=== Каталог фильмов ===");
            out.println("1. Список фильмов      2. Найти по названию");
            out.println("3. Добавить фильм      4. Поставить оценку (1–5)");
            out.println("5. Средняя оценка      6. Строк на странице");
            out.println("0. Выйти из аккаунта");
            int choice = readInt("Выбор: ", 0, 6, -1);
            try {
                switch (choice) {
                    case 1 -> showPages(null);
                    case 2 -> showPages(readText("Часть или полное название: ", null));
                    case 3 -> addMovie();
                    case 4 -> rateMovie();
                    case 5 -> showAverage();
                    case 6 -> changePageSize();
                    case 0 -> { out.println("Вы вышли из каталога."); return; }
                    default -> throw new IllegalStateException("Неизвестный выбор.");
                }
            } catch (IOException | IllegalArgumentException e) {
                logger.log(Level.WARNING, "Команда не выполнена: " + e.getMessage());
                out.println("Не удалось выполнить действие: " + e.getMessage());
            }
        }
    }

    private void showPages(String query) throws IOException {
        int offset = 0;
        FilmClientService.Page page = service.list(query, offset, pageSize);
        out.println("Фильмов найдено: " + page.total());
        while (offset < page.total()) {
            for (MovieView movie : page.movies()) printMovie(movie);
            offset += page.movies().size();
            if (offset >= page.total() || !confirm("Показать следующую страницу? (д/н): ")) break;
            page = service.list(query, offset, pageSize);
        }
        if (page.total() == 0) out.println("Подходящих фильмов нет.");
    }

    private void addMovie() throws IOException {
        String title = readText("Название: ", null);
        int year = readInt("Год выпуска: ", 1888, Year.now().getValue() + 3, -1);
        MovieView movie = service.add(title, year);
        out.println("Добавлен фильм:");
        printMovie(movie);
    }

    private void rateMovie() throws IOException {
        long id = readLong("Номер фильма из списка: ");
        int rating = readInt("Оценка (1–5): ", 1, 5, -1);
        MovieView movie = service.rate(id, rating);
        out.println("Оценка сохранена. Новая средняя оценка:");
        printMovie(movie);
    }

    private void showAverage() throws IOException {
        long id = readLong("Номер фильма из списка: ");
        MovieView movie = service.get(id);
        if (movie.ratingCount() == 0) out.printf("У фильма «%s» пока нет оценок.%n", movie.title());
        else out.printf("Средняя оценка «%s» (%d оценок): %.2f из 5.%n",
                movie.title(), movie.ratingCount(), movie.averageRating());
        if (movie.myRating() > 0) out.println("Ваша оценка: " + movie.myRating());
    }

    private void changePageSize() {
        pageSize = readInt("Сколько фильмов показывать на странице (1–100): ", 1, 100, -1);
        out.println("Размер страницы установлен: " + pageSize + ".");
    }

    private void printMovie(MovieView movie) {
        String average = movie.ratingCount() == 0 ? "нет оценок"
                : String.format(java.util.Locale.ROOT, "%.2f/5 (%d)", movie.averageRating(), movie.ratingCount());
        out.printf("#%d  %s (%d) — %s", movie.id(), movie.title(), movie.year(), average);
        if (movie.myRating() > 0) out.printf("; ваша оценка: %d", movie.myRating());
        out.println();
    }

    private String readText(String prompt, String defaultValue) {
        while (true) {
            out.print(prompt);
            String input = readLine().trim();
            if (!input.isEmpty()) return input;
            if (defaultValue != null) return defaultValue;
            out.println("Значение не должно быть пустым.");
        }
    }

    private int readInt(String prompt, int min, int max, int defaultValue) {
        while (true) {
            out.print(prompt);
            String input = readLine().trim();
            if (input.isEmpty() && defaultValue >= 0) return defaultValue;
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { /* Попросить ввод повторно. */ }
            out.printf("Введите целое число от %d до %d.%n", min, max);
        }
    }

    private long readLong(String prompt) {
        while (true) {
            out.print(prompt);
            try {
                long id = Long.parseLong(readLine().trim());
                if (id > 0) return id;
            } catch (NumberFormatException ignored) { /* Попросить ввод повторно. */ }
            out.println("Введите положительный номер фильма.");
        }
    }

    private int parsePort(String value) {
        try {
            int port = Integer.parseInt(value);
            if (port >= 1 && port <= 65535) return port;
        } catch (NumberFormatException ignored) { /* Показать понятную ошибку ниже. */ }
        throw new IllegalArgumentException("укажите число от 1 до 65535.");
    }

    private boolean confirm(String prompt) {
        out.print(prompt);
        String answer = readLine().trim();
        return answer.equalsIgnoreCase("д") || answer.equalsIgnoreCase("да");
    }

    private String readLine() {
        if (!scanner.hasNextLine()) throw new EndOfInput();
        return scanner.nextLine();
    }

    private static final class EndOfInput extends RuntimeException { }
}
