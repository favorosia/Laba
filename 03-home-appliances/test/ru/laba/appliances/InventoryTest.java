package ru.laba.appliances;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class InventoryTest {
    @Test
    void renamingCategoryUpdatesAssignedAppliances() {
        Inventory inventory = new Inventory();
        inventory.addCategory("Кухня");
        inventory.add(new Appliance("Чайник", "Кухня", "Brand", "K1", LocalDate.parse("2025-01-10"), 24, ""));

        inventory.renameCategory("Кухня", "Малая техника");

        assertEquals("Малая техника", inventory.all().get(0).category());
        assertEquals("Малая техника", inventory.categories().get(0));
    }

    @Test
    void searchFindsByBrandAndSortOrdersByName() {
        Inventory inventory = new Inventory();
        inventory.addCategory("Дом");
        inventory.add(new Appliance("Пылесос", "Дом", "Zeta", "Z1", LocalDate.parse("2024-01-01"), 12, ""));
        inventory.add(new Appliance("Блендер", "Дом", "Alpha", "A1", LocalDate.parse("2025-01-01"), 6, ""));

        assertEquals("Пылесос", inventory.search("zeta").get(0).name());
        assertEquals("Блендер", inventory.sorted(Inventory.SortBy.NAME).get(0).name());
    }

    @Test
    void rejectsUnknownCategoryAndInvalidWarranty() {
        Inventory inventory = new Inventory();
        assertThrows(IllegalArgumentException.class, () -> inventory.add(
                new Appliance("Тостер", "Неизвестно", "Acme", "T1", LocalDate.now(), 1, "")));
        assertThrows(IllegalArgumentException.class, () ->
                new Appliance("Тостер", "Дом", "Acme", "T1", LocalDate.now(), -1, ""));
    }
}
