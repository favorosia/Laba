package ru.laba.appliances;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/** Описание одной единицы бытовой техники. */
public final class Appliance implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String category;
    private final String brand;
    private final String model;
    private final LocalDate purchaseDate;
    private final int warrantyMonths;
    private final String note;

    public Appliance(String name, String category, String brand, String model,
                     LocalDate purchaseDate, int warrantyMonths, String note) {
        this(UUID.randomUUID().toString(), name, category, brand, model, purchaseDate, warrantyMonths, note);
    }

    private Appliance(String id, String name, String category, String brand, String model,
                      LocalDate purchaseDate, int warrantyMonths, String note) {
        this.id = id;
        this.name = required(name, "Название");
        this.category = required(category, "Категория");
        this.brand = required(brand, "Бренд");
        this.model = required(model, "Модель");
        if (purchaseDate == null) throw new IllegalArgumentException("Дата покупки обязательна.");
        if (warrantyMonths < 0 || warrantyMonths > 600) throw new IllegalArgumentException("Гарантия должна быть от 0 до 600 месяцев.");
        this.purchaseDate = purchaseDate;
        this.warrantyMonths = warrantyMonths;
        this.note = note == null ? "" : note.trim();
    }

    private static String required(String value, String label) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(label + " не должно быть пустым.");
        return value.trim();
    }

    public Appliance edit(String name, String category, String brand, String model,
                          LocalDate purchaseDate, int warrantyMonths, String note) {
        return new Appliance(id, name, category, brand, model, purchaseDate, warrantyMonths, note);
    }

    public Appliance withCategory(String newCategory) {
        return new Appliance(id, name, newCategory, brand, model, purchaseDate, warrantyMonths, note);
    }

    public String id() { return id; }
    public String name() { return name; }
    public String category() { return category; }
    public String brand() { return brand; }
    public String model() { return model; }
    public LocalDate purchaseDate() { return purchaseDate; }
    public int warrantyMonths() { return warrantyMonths; }
    public String note() { return note; }
}
