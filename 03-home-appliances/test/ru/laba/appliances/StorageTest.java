package ru.laba.appliances;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class StorageTest {
    @TempDir Path temporaryDirectory;

    @Test
    void binaryRepositoryRestoresInventoryAndDisplaySettings() throws Exception {
        Inventory inventory = new Inventory();
        inventory.addCategory("Гостиная");
        inventory.add(new Appliance("Телевизор", "Гостиная", "View", "V55",
                LocalDate.parse("2025-03-02"), 36, "На стене"));
        BinaryRepository repository = new BinaryRepository(temporaryDirectory.resolve("inventory.bin"));

        repository.save(new AppData(inventory, 7, Inventory.SortBy.BRAND));
        AppData loaded = repository.load();

        assertEquals(1, loaded.inventory().size());
        assertEquals("Телевизор", loaded.inventory().all().get(0).name());
        assertEquals(7, loaded.pageSize());
        assertEquals(Inventory.SortBy.BRAND, loaded.sortBy());
    }

    @Test
    void textExportCanBeImportedIncludingQuotedSeparators() throws Exception {
        Inventory original = new Inventory();
        original.addCategory("Кухня; малая техника");
        original.add(new Appliance("Кофеварка", "Кухня; малая техника", "A\"B", "C-1",
                LocalDate.parse("2025-05-06"), 24, "Верхняя; полка"));
        Path file = temporaryDirectory.resolve("export.txt");
        TextExchange exchange = new TextExchange();

        exchange.exportTo(file, original);
        Inventory restored = new Inventory();
        exchange.importFrom(file, restored);

        assertEquals(1, restored.size());
        assertEquals("Кухня; малая техника", restored.all().get(0).category());
        assertEquals("A\"B", restored.all().get(0).brand());
        assertEquals("Верхняя; полка", restored.all().get(0).note());
        assertTrue(restored.hasCategory("Кухня; малая техника"));
    }
}
