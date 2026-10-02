package ru.laba.film.server.presentation;

import ru.laba.film.server.data.CatalogRepository;
import ru.laba.film.server.service.CatalogService;
import ru.laba.film.shared.JulLogging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Запускает многопоточный TCP-сервер каталога фильмов. */
public final class ServerMain {
    private ServerMain() { }
    public static void main(String[] args) {
        Logger logger = JulLogging.configure("server");
        int port;
        try {
            String configuredPort = args.length > 0 ? args[0]
                    : Integer.toString(Integer.getInteger("film.port", 5050));
            port = parsePort(configuredPort);
        } catch (IllegalArgumentException e) {
            logger.severe(e.getMessage());
            System.err.println("Сервер не запущен: " + e.getMessage());
            return;
        }
        Path dataFile = Path.of(System.getProperty("film.data", "data/film-catalog.bin"));
        try {
            CatalogService catalog = new CatalogService(new CatalogRepository(dataFile));
            try (ServerSocket server = new ServerSocket(port)) {
                ExecutorService workers = Executors.newFixedThreadPool(Math.max(4,
                        Runtime.getRuntime().availableProcessors() * 2));
                AtomicBoolean closed = new AtomicBoolean();
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (closed.compareAndSet(false, true)) {
                        workers.shutdownNow();
                        try { server.close(); } catch (IOException ignored) { }
                        logger.info("Сервер остановлен.");
                    }
                }, "film-server-shutdown"));
                logger.info(() -> "Каталог фильмов слушает TCP-порт " + port + "; хранилище: " + dataFile.toAbsolutePath());
                System.out.println("Сервер каталога запущен на TCP-порту " + port + ". Для остановки нажмите Ctrl+C.");
                while (!server.isClosed()) {
                    try {
                        Socket socket = server.accept();
                        workers.execute(new ClientHandler(socket, catalog, logger));
                    } catch (IOException e) {
                        if (!server.isClosed()) logger.log(Level.SEVERE, "Не удалось принять TCP-соединение", e);
                    }
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Сервер не запущен: " + e.getMessage(), e);
            System.err.println("Сервер не запущен: " + e.getMessage());
        }
    }

    private static int parsePort(String value) {
        try {
            int port = Integer.parseInt(value);
            if (port < 1 || port > 65535) throw new NumberFormatException();
            return port;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Порт должен быть целым числом от 1 до 65535.");
        }
    }
}
