package ru.laba.films.presentation;

import ru.laba.films.config.DatabaseConfig;
import ru.laba.films.data.ActorRepository;
import ru.laba.films.data.ConnectionFactory;
import ru.laba.films.data.MovieRepository;
import ru.laba.films.domain.Actor;
import ru.laba.films.domain.FilmRole;
import ru.laba.films.domain.Movie;
import ru.laba.films.service.FilmCatalogService;

import java.io.Console;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;
import java.util.Scanner;

/** Консольный интерфейс трёхслойного приложения. */
public final class FilmDatabaseApplication {
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private FilmCatalogService service;
    private int pageSize = 10;

    public static void main(String[] args) { new FilmDatabaseApplication().run(); }

    private void run() {
        try {
            DatabaseConfig config = DatabaseConfig.fromEnvironment(readDatabasePassword());
            Class.forName("org.postgresql.Driver");
            ConnectionFactory connections = new ConnectionFactory(config);
            service = new FilmCatalogService(new MovieRepository(connections), new ActorRepository(connections));
            try (var connection = connections.open(); var statement = connection.createStatement()) {
                statement.execute("SELECT 1");
            }
            out.println("Подключено к PostgreSQL: " + config.url());
            menuLoop();
        } catch (EndOfInput e) {
            out.println("Ввод завершён.");
        } catch (ClassNotFoundException e) {
            out.println("Не найден драйвер PostgreSQL JDBC. Повторно запустите build.bat.");
        } catch (SQLException e) {
            out.println("Ошибка PostgreSQL: " + e.getMessage());
            out.println("Создайте базу и выполните db/schema.sql, затем db/seed.sql. См. README.md.");
        } catch (IllegalArgumentException e) {
            out.println("Ошибка настройки: " + e.getMessage());
        }
    }

    private String readDatabasePassword() {
        if (System.getenv("FILM_DB_PASSWORD") != null) return "";
        Console console = System.console();
        if (console != null) {
            char[] secret = console.readPassword("Пароль PostgreSQL (Enter, если пароль пустой): ");
            String value = secret == null ? "" : new String(secret);
            if (secret != null) java.util.Arrays.fill(secret, '\0');
            return value;
        }
        out.print("Пароль PostgreSQL (Enter, если пароль пустой): ");
        return scanner.hasNextLine() ? scanner.nextLine() : "";
    }

    private void menuLoop() {
        while (true) {
            printMenu();
            try {
                int choice = readInt("Ваш выбор: ", 0, 12);
                switch (choice) {
                    case 1 -> listMovies();
                    case 2 -> addMovie();
                    case 3 -> deleteMovie();
                    case 4 -> changeMovieYear();
                    case 5 -> listActors();
                    case 6 -> addActor();
                    case 7 -> deleteActor();
                    case 8 -> assignRole();
                    case 9 -> removeRole();
                    case 10 -> actorsWithoutFilms();
                    case 11 -> filmsForActor();
                    case 12 -> countRoles();
                    case 0 -> { out.println("До свидания!"); return; }
                    default -> throw new IllegalStateException("Неизвестная команда.");
                }
            } catch (EndOfInput e) {
                out.println("Ввод завершён."); return;
            } catch (SQLException | IllegalArgumentException e) {
                out.println("Не удалось выполнить действие: " + friendlyMessage(e));
            }
        }
    }

    private String friendlyMessage(Exception e) {
        if (e instanceof SQLException sql && "23503".equals(sql.getSQLState())) {
            return "Проверьте, что фильм и актёр существуют в базе.";
        }
        if (e instanceof SQLException sql && "23505".equals(sql.getSQLState())) {
            return "Такая запись уже есть в базе.";
        }
        return e.getMessage();
    }

    private void printMenu() {
        out.println("\n=== Фильмы, актёры и роли ===");
        out.println("1. Список фильмов         2. Добавить фильм");
        out.println("3. Удалить фильм          4. Изменить год фильма");
        out.println("5. Список актёров         6. Добавить актёра");
        out.println("7. Удалить актёра         8. Назначить роль");
        out.println("9. Удалить назначение     10. Актёры без фильмов");
        out.println("11. Фильмы выбранного актёра   12. Количество его ролей");
        out.println("0. Выход");
    }

    private void listMovies() throws SQLException {
        int total = service.movieCount();
        if (total == 0) { out.println("В каталоге пока нет фильмов."); return; }
        int offset = 0;
        do {
            List<Movie> rows = service.movies(offset, pageSize);
            out.printf("\nФильмы — строки %d–%d из %d%n", offset + 1, offset + rows.size(), total);
            rows.forEach(this::printMovie);
            offset += rows.size();
        } while (offset < total && confirm("Показать следующую страницу? (д/н): "));
    }

    private void addMovie() throws SQLException {
        String title = readText("Название фильма: ");
        int year = readInt("Год выпуска: ", 1888, Year.now().getValue() + 3);
        Movie movie = service.addMovie(title, year);
        out.println("Добавлен фильм:"); printMovie(movie);
    }

    private void deleteMovie() throws SQLException {
        listMovies();
        long id = readPositiveLong("Номер фильма: ");
        if (confirm("Удалить фильм и его роли? (д/н): ")) {
            service.deleteMovie(id); out.println("Фильм удалён.");
        }
    }

    private void changeMovieYear() throws SQLException {
        listMovies();
        long id = readPositiveLong("Номер фильма: ");
        int year = readInt("Новый год выпуска: ", 1888, Year.now().getValue() + 3);
        service.changeMovieYear(id, year); out.println("Год выпуска изменён.");
    }

    private void listActors() throws SQLException { showActorPages(service.actors(), "Актёры"); }

    private void addActor() throws SQLException {
        String name = readText("ФИО актёра: ");
        int year = readInt("Год рождения: ", 1850, Year.now().getValue());
        Actor actor = service.addActor(name, year);
        out.printf("Добавлен актёр #%d: %s (%d).%n", actor.id(), actor.fullName(), actor.birthYear());
    }

    private void deleteActor() throws SQLException {
        showActorPages(service.actors(), "Актёры");
        long id = readPositiveLong("Номер актёра: ");
        if (confirm("Удалить актёра и все назначения ролей? (д/н): ")) {
            service.deleteActor(id); out.println("Актёр удалён.");
        }
    }

    private void assignRole() throws SQLException {
        listMovies();
        showActorPages(service.actors(), "Актёры");
        long movieId = readPositiveLong("Номер фильма: ");
        long actorId = readPositiveLong("Номер актёра: ");
        String role = readText("Имя персонажа / роль: ");
        service.assignRole(actorId, movieId, role);
        out.println("Роль назначена.");
    }

    private void removeRole() throws SQLException {
        showActorPages(service.actors(), "Актёры");
        long actorId = readPositiveLong("Номер актёра: ");
        List<FilmRole> roles = service.rolesForActor(actorId);
        if (roles.isEmpty()) { out.println("У этого актёра нет назначенных ролей."); return; }
        int shown = showRolePages(roles);
        int number = readInt("Номер назначения для удаления: ", 1, shown);
        FilmRole selected = roles.get(number - 1);
        service.removeRole(actorId, selected.movie().id(), selected.roleName());
        out.println("Назначение роли удалено.");
    }

    private void actorsWithoutFilms() throws SQLException {
        showActorPages(service.actorsWithoutFilms(), "Актёры без фильмов");
    }

    private void filmsForActor() throws SQLException {
        showActorPages(service.actors(), "Выберите актёра");
        long id = readPositiveLong("Номер актёра: ");
        List<Movie> movies = service.filmsForActor(id);
        if (movies.isEmpty()) out.println("У этого актёра пока нет назначенных фильмов.");
        else showMoviePages(movies, "Фильмы актёра");
    }

    private void countRoles() throws SQLException {
        showActorPages(service.actors(), "Выберите актёра");
        long id = readPositiveLong("Номер актёра: ");
        out.println("Количество ролей актёра: " + service.roleCount(id));
    }

    private void showActorPages(List<Actor> actors, String title) {
        if (actors.isEmpty()) { out.println(title + ": список пуст."); return; }
        for (int start = 0; start < actors.size(); start += pageSize) {
            int end = Math.min(start + pageSize, actors.size());
            out.printf("\n%s — строки %d–%d из %d%n", title, start + 1, end, actors.size());
            for (int i = start; i < end; i++) {
                Actor actor = actors.get(i);
                out.printf("#%d  %s, год рождения: %d%n", actor.id(), actor.fullName(), actor.birthYear());
            }
            if (end < actors.size() && !confirm("Показать следующую страницу? (д/н): ")) break;
        }
    }

    private void showMoviePages(List<Movie> movies, String title) {
        for (int start = 0; start < movies.size(); start += pageSize) {
            int end = Math.min(start + pageSize, movies.size());
            out.printf("\n%s — строки %d–%d из %d%n", title, start + 1, end, movies.size());
            for (int i = start; i < end; i++) printMovie(movies.get(i));
            if (end < movies.size() && !confirm("Показать следующую страницу? (д/н): ")) break;
        }
    }

    private int showRolePages(List<FilmRole> roles) {
        int shown = 0;
        for (int start = 0; start < roles.size(); start += pageSize) {
            int end = Math.min(start + pageSize, roles.size());
            out.printf("\nРоли — строки %d–%d из %d%n", start + 1, end, roles.size());
            for (int i = start; i < end; i++) {
                FilmRole role = roles.get(i);
                out.printf("#%d  %s (%d) — %s%n", i + 1, role.movie().title(), role.movie().releaseYear(), role.roleName());
            }
            shown = end;
            if (end < roles.size() && !confirm("Показать следующую страницу? (д/н): ")) break;
        }
        return shown;
    }

    private void printMovie(Movie movie) {
        out.printf("#%d  %s (%d)%n", movie.id(), movie.title(), movie.releaseYear());
    }

    private boolean confirm(String prompt) {
        out.print(prompt);
        String answer = readLine().trim();
        return answer.equalsIgnoreCase("д") || answer.equalsIgnoreCase("да");
    }

    private String readText(String prompt) {
        while (true) {
            out.print(prompt);
            String value = readLine().trim();
            if (!value.isEmpty()) return value;
            out.println("Поле не должно быть пустым.");
        }
    }

    private int readInt(String prompt, int minimum, int maximum) {
        while (true) {
            out.print(prompt);
            try {
                int value = Integer.parseInt(readLine().trim());
                if (value >= minimum && value <= maximum) return value;
            } catch (NumberFormatException ignored) { /* Повторно запросить ввод. */ }
            out.printf("Введите целое число от %d до %d.%n", minimum, maximum);
        }
    }

    private long readPositiveLong(String prompt) {
        while (true) {
            out.print(prompt);
            try {
                long value = Long.parseLong(readLine().trim());
                if (value > 0) return value;
            } catch (NumberFormatException ignored) { /* Повторно запросить ввод. */ }
            out.println("Введите положительный номер из списка.");
        }
    }

    private String readLine() {
        if (!scanner.hasNextLine()) throw new EndOfInput();
        return scanner.nextLine();
    }

    private static final class EndOfInput extends RuntimeException { }
}
