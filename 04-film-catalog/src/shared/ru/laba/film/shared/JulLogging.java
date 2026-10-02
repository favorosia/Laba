package ru.laba.film.shared;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/** Инициализация настраиваемого JUL-логирования в отдельный файл каждой части. */
public final class JulLogging {
    private JulLogging() { }
    public static Logger configure(String role) {
        try {
            Files.createDirectories(Path.of("logs"));
            Path config = Path.of("config", role + "-logging.properties");
            String defaults = "handlers=java.util.logging.FileHandler, java.util.logging.ConsoleHandler\n"
                    + ".level=INFO\njava.util.logging.FileHandler.pattern=logs/" + role + "-%u-%g.log\n"
                    + "java.util.logging.FileHandler.limit=1048576\njava.util.logging.FileHandler.count=3\n"
                    + "java.util.logging.FileHandler.append=true\njava.util.logging.FileHandler.encoding=UTF-8\n"
                    + "java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter\n"
                    + "java.util.logging.ConsoleHandler.level=WARNING\n"
                    + "java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter\n";
            try (InputStream input = Files.exists(config) ? Files.newInputStream(config)
                    : new ByteArrayInputStream(defaults.getBytes(StandardCharsets.UTF_8))) {
                LogManager.getLogManager().readConfiguration(input);
            }
        } catch (Exception e) {
            System.err.println("Не удалось настроить JUL: " + e.getMessage());
        }
        return Logger.getLogger("ru.laba.film." + role);
    }
}
