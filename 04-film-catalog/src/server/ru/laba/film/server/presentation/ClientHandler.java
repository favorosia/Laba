package ru.laba.film.server.presentation;

import ru.laba.film.server.service.CatalogService;
import ru.laba.film.shared.MovieView;
import ru.laba.film.shared.WireProtocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Обслуживает одну TCP-сессию клиента. */
public final class ClientHandler implements Runnable {
    private final Socket socket;
    private final CatalogService catalog;
    private final Logger logger;
    private String username;

    public ClientHandler(Socket socket, CatalogService catalog, Logger logger) {
        this.socket = socket;
        this.catalog = catalog;
        this.logger = logger;
    }

    @Override public void run() {
        try (socket;
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            socket.setSoTimeout(10 * 60 * 1000);
            logger.info(() -> "Клиент подключился: " + socket.getRemoteSocketAddress());
            boolean connected = true;
            while (connected) {
                String request;
                try { request = WireProtocol.receive(input); }
                catch (IOException e) { break; }
                try {
                    connected = handle(request, output);
                } catch (IllegalArgumentException e) {
                    WireProtocol.fail(output, e.getMessage());
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Ошибка обработки запроса клиента", e);
                    WireProtocol.fail(output, "Ошибка чтения или сохранения данных.");
                } catch (RuntimeException e) {
                    logger.log(Level.WARNING, "Некорректный запрос клиента", e);
                    WireProtocol.fail(output, "Некорректный запрос.");
                }
            }
        } catch (IOException e) {
            logger.log(Level.FINE, "TCP-сессия завершилась", e);
        } finally {
            logger.info(() -> "Клиент отключился: " + socket.getRemoteSocketAddress());
        }
    }

    private boolean handle(String request, DataOutputStream output) throws IOException {
        String[] parts = request.split("\t", -1);
        if (parts.length == 0) throw new IllegalArgumentException("Пустая команда.");
        String command = parts[0].toUpperCase(Locale.ROOT);
        if (command.equals("QUIT")) { WireProtocol.respond(output, "Сеанс завершён.", List.of(), 0); return false; }
        if (command.equals("REGISTER") || command.equals("LOGIN")) {
            authenticate(command, parts, output);
            return true;
        }
        if (username == null) { WireProtocol.fail(output, "Сначала войдите или создайте учётную запись."); return true; }
        switch (command) {
            case "LIST", "SEARCH" -> {
                requireParts(parts, 3);
                int offset = Integer.parseInt(parts[1]);
                int limit = Integer.parseInt(parts[2]);
                String query = command.equals("SEARCH") ? decodeRequired(parts, 3) : "";
                if (command.equals("SEARCH")) requireParts(parts, 4);
                CatalogService.Page page = catalog.list(query, offset, limit, username);
                WireProtocol.respond(output, "Найдено фильмов: " + page.total(), page.movies(), page.total());
            }
            case "ADD" -> {
                requireParts(parts, 3);
                MovieView movie = catalog.addMovie(WireProtocol.decode(parts[1]), Integer.parseInt(parts[2]), username);
                logger.info(() -> "Пользователь " + username + " добавил фильм #" + movie.id());
                WireProtocol.respond(output, "Фильм добавлен.", List.of(movie), 1);
            }
            case "RATE" -> {
                requireParts(parts, 3);
                long id = Long.parseLong(parts[1]);
                catalog.rate(id, Integer.parseInt(parts[2]), username);
                logger.info(() -> "Пользователь " + username + " оценил фильм #" + id);
                WireProtocol.respond(output, "Ваша оценка сохранена.", List.of(catalog.getMovie(id, username)), 1);
            }
            case "DETAIL" -> {
                requireParts(parts, 2);
                WireProtocol.respond(output, "Данные фильма.", List.of(catalog.getMovie(Long.parseLong(parts[1]), username)), 1);
            }
            default -> throw new IllegalArgumentException("Неизвестная команда.");
        }
        return true;
    }

    private void authenticate(String command, String[] parts, DataOutputStream output) throws IOException {
        requireParts(parts, 3);
        String name = WireProtocol.decode(parts[1]);
        char[] password = WireProtocol.decode(parts[2]).toCharArray();
        try {
            if (command.equals("REGISTER")) {
                catalog.register(name, password);
                username = name.trim();
                logger.info(() -> "Зарегистрирован пользователь: " + username);
                WireProtocol.respond(output, "Учётная запись создана. Вы вошли как " + username + ".", List.of(), 0);
            } else {
                String authenticated = catalog.authenticate(name, password);
                if (authenticated == null) WireProtocol.fail(output, "Неверное имя пользователя или пароль.");
                else {
                    username = authenticated;
                    logger.info(() -> "Успешный вход пользователя: " + username);
                    WireProtocol.respond(output, "Вы вошли как " + username + ".", List.of(), 0);
                }
            }
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }

    private static String decodeRequired(String[] parts, int index) {
        if (parts.length <= index) throw new IllegalArgumentException("Не хватает параметров команды.");
        return WireProtocol.decode(parts[index]);
    }
    private static void requireParts(String[] parts, int minimum) {
        if (parts.length < minimum) throw new IllegalArgumentException("Не хватает параметров команды.");
    }
}
