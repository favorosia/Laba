package ru.laba.appliances;

import java.io.Serial;
import java.io.Serializable;

/** Сохраняемое состояние приложения: инвентарь и настройки отображения. */
public final class AppData implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    private final Inventory inventory;
    private final int pageSize;
    private final Inventory.SortBy sortBy;

    public AppData(Inventory inventory, int pageSize, Inventory.SortBy sortBy) {
        if (inventory == null || sortBy == null) throw new IllegalArgumentException("Состояние приложения задано неверно.");
        if (pageSize < 1 || pageSize > 100) throw new IllegalArgumentException("Размер страницы должен быть от 1 до 100.");
        this.inventory = inventory;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
    }

    public Inventory inventory() { return inventory; }
    public int pageSize() { return pageSize; }
    public Inventory.SortBy sortBy() { return sortBy; }
}
