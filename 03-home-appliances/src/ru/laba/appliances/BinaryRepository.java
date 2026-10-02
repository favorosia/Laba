package ru.laba.appliances;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/** Загружает и сохраняет состояние программы в бинарном файле. */
public final class BinaryRepository {
    private final Path file;

    public BinaryRepository(Path file) { this.file = file; }

    public AppData load() throws IOException {
        if (!Files.exists(file)) return new AppData(new Inventory(), 10, Inventory.SortBy.NAME);
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(file))) {
            Object value = input.readObject();
            if (!(value instanceof AppData data)) throw new IOException("Файл данных имеет неизвестный формат.");
            if (data.inventory() == null || data.sortBy() == null
                    || data.pageSize() < 1 || data.pageSize() > 100) {
                throw new IOException("Файл данных содержит некорректные настройки.");
            }
            return data;
        } catch (ClassNotFoundException | RuntimeException e) {
            throw new IOException("Не удалось прочитать файл данных.", e);
        }
    }

    public void save(AppData data) throws IOException {
        Path absolute = file.toAbsolutePath();
        Path parent = absolute.getParent();
        Files.createDirectories(parent);
        Path temporary = Files.createTempFile(parent, "appliances-", ".tmp");
        try {
            try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(temporary))) {
                output.writeObject(data);
            }
            try {
                Files.move(temporary, absolute, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporary, absolute, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporary);
        }
    }
}
