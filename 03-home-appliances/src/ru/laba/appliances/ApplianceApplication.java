package ru.laba.appliances;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/** Консольное приложение учёта домашней бытовой техники. */
public final class ApplianceApplication {
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private final BinaryRepository repository = new BinaryRepository(Path.of("data", "home-appliances.bin"));
    private final TextExchange exchange = new TextExchange();
    private Inventory inventory;
    private int pageSize;
    private Inventory.SortBy sortBy;

    public static void main(String[] args) { new ApplianceApplication().run(); }

    private void run() {
        try {
            AppData saved = repository.load();
            inventory = saved.inventory();
            pageSize = saved.pageSize();
            sortBy = saved.sortBy();
            out.printf("Загружено записей: %d. Порядок: %s, строк на странице: %d.%n",
                    inventory.size(), sortLabel(sortBy), pageSize);
        } catch (IOException | RuntimeException e) {
            out.println("Не удалось загрузить файл данных: " + e.getMessage());
            out.println("Завершите программу и проверьте файл data/home-appliances.bin.");
            return;
        }

        while (true) {
            printMenu();
            int choice;
            try { choice = readInt("Ваш выбор: ", 0, 10); }
            catch (EndOfInput e) { saveAndExit(); return; }
            try {
                switch (choice) {
                    case 1 -> showPage(inventory.sorted(sortBy), "Список техники");
                    case 2 -> addAppliance();
                    case 3 -> editAppliance();
                    case 4 -> removeAppliance();
                    case 5 -> search();
                    case 6 -> manageCategories();
                    case 7 -> importData();
                    case 8 -> exportData();
                    case 9 -> changePageSize();
                    case 10 -> changeSortOrder();
                    case 0 -> { saveAndExit(); return; }
                    default -> throw new IllegalStateException("Неизвестная команда.");
                }
            } catch (EndOfInput e) {
                saveAndExit(); return;
            } catch (IllegalArgumentException | IOException e) {
                out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        out.println("\n=== Учёт бытовой техники ===");
        out.println("1. Показать технику       2. Добавить технику");
        out.println("3. Изменить запись        4. Удалить запись");
        out.println("5. Поиск                  6. Категории");
        out.println("7. Импорт из текста       8. Экспорт в текст");
        out.println("9. Строк на странице      10. Порядок сортировки");
        out.println("0. Сохранить и выйти");
    }

    private void addAppliance() throws IOException {
        if (inventory.categories().isEmpty()) {
            out.println("Сначала создайте категорию в меню «Категории»."); return;
        }
        Appliance item = readAppliance(null);
        inventory.add(item);
        save();
        out.println("Техника добавлена и сохранена.");
    }

    private Appliance readAppliance(Appliance old) {
        String name = readEditableText("Название", old == null ? null : old.name());
        showCategories();
        String category = readEditableText("Категория", old == null ? null : old.category());
        if (!inventory.hasCategory(category)) throw new IllegalArgumentException("Выберите категорию из списка.");
        String brand = readEditableText("Бренд", old == null ? null : old.brand());
        String model = readEditableText("Модель", old == null ? null : old.model());
        LocalDate date = readEditableDate(old);
        int warranty = readEditableInt(old);
        String note = readEditableNote(old);
        return old == null
                ? new Appliance(name, category, brand, model, date, warranty, note)
                : old.edit(name, category, brand, model, date, warranty, note);
    }

    private String readEditableText(String label, String current) {
        if (current == null) return readText(label + ": ");
        out.print(label + " [" + current + "] (Enter — оставить): ");
        String value = readLine().trim();
        return value.isEmpty() ? current : value;
    }

    private LocalDate readEditableDate(Appliance old) {
        if (old == null) return readDate("Дата покупки yyyy-MM-dd: ");
        out.print("Дата покупки yyyy-MM-dd [" + old.purchaseDate() + "] (Enter — оставить): ");
        String value = readLine().trim();
        if (value.isEmpty()) return old.purchaseDate();
        try { return LocalDate.parse(value); }
        catch (DateTimeParseException e) { throw new IllegalArgumentException("Введите дату в формате yyyy-MM-dd."); }
    }

    private int readEditableInt(Appliance old) {
        if (old == null) return readInt("Гарантия в месяцах (0–600): ", 0, 600);
        out.print("Гарантия в месяцах (0–600) [" + old.warrantyMonths() + "] (Enter — оставить): ");
        String value = readLine().trim();
        if (value.isEmpty()) return old.warrantyMonths();
        try {
            int result = Integer.parseInt(value);
            if (result >= 0 && result <= 600) return result;
        } catch (NumberFormatException ignored) { /* Ниже выводится подсказка. */ }
        throw new IllegalArgumentException("Гарантия должна быть целым числом от 0 до 600.");
    }

    private String readEditableNote(Appliance old) {
        if (old == null) return readText("Заметка (можно оставить пустой): ", true);
        out.print("Заметка [" + old.note() + "] (Enter — оставить, - — очистить): ");
        String value = readLine().trim();
        if (value.isEmpty()) return old.note();
        return value.equals("-") ? "" : value;
    }

    private void editAppliance() throws IOException {
        if (inventory.size() == 0) { out.println("Список пуст."); return; }
        List<Appliance> sorted = inventory.sorted(sortBy);
        showPage(sorted, "Выберите запись для изменения");
        int visibleNumber = readInt("Номер записи из списка: ", 1, sorted.size());
        Appliance selected = sorted.get(visibleNumber - 1);
        int actualIndex = inventory.indexOf(selected.id());
        inventory.update(actualIndex, readAppliance(selected));
        save();
        out.println("Запись изменена и сохранена.");
    }

    private void removeAppliance() throws IOException {
        if (inventory.size() == 0) { out.println("Список пуст."); return; }
        List<Appliance> sorted = inventory.sorted(sortBy);
        showPage(sorted, "Выберите запись для удаления");
        int visibleNumber = readInt("Номер записи из списка: ", 1, sorted.size());
        Appliance selected = sorted.get(visibleNumber - 1);
        if (!confirm("Удалить «" + selected.name() + "»? (д/н): ")) return;
        inventory.remove(inventory.indexOf(selected.id()));
        save();
        out.println("Запись удалена и сохранена.");
    }

    private void search() {
        String query = readText("Поиск по названию, категории, бренду, модели или заметке: ");
        List<Appliance> found = inventory.search(query).stream()
                .sorted(inventoryComparator()).toList();
        out.println("Найдено записей: " + found.size());
        showPage(found, "Результаты поиска");
    }

    private java.util.Comparator<Appliance> inventoryComparator() {
        return switch (sortBy) {
            case NAME -> java.util.Comparator.comparing(Appliance::name, String.CASE_INSENSITIVE_ORDER);
            case CATEGORY -> java.util.Comparator.comparing(Appliance::category, String.CASE_INSENSITIVE_ORDER);
            case BRAND -> java.util.Comparator.comparing(Appliance::brand, String.CASE_INSENSITIVE_ORDER);
            case PURCHASE_DATE -> java.util.Comparator.comparing(Appliance::purchaseDate);
        };
    }

    private void manageCategories() throws IOException {
        showCategories();
        out.println("1. Добавить категорию   2. Переименовать категорию   0. Назад");
        int choice = readInt("Ваш выбор: ", 0, 2);
        if (choice == 1) {
            inventory.addCategory(readText("Название новой категории: "));
            save();
            out.println("Категория добавлена и сохранена.");
        } else if (choice == 2) {
            String oldName = readText("Какую категорию переименовать: ");
            String newName = readText("Новое название: ");
            inventory.renameCategory(oldName, newName);
            save();
            out.println("Категория и связанные записи обновлены.");
        }
    }

    private void showCategories() {
        List<String> categories = inventory.categories();
        if (categories.isEmpty()) out.println("Категорий пока нет.");
        else {
            out.println("Категории:");
            for (int i = 0; i < categories.size(); i++) out.printf("  %d. %s%n", i + 1, categories.get(i));
        }
    }

    private void importData() throws IOException {
        Path input = Path.of(readText("Путь к текстовому файлу импорта: "));
        exchange.importFrom(input, inventory);
        save();
        out.println("Импорт завершён. Всего записей: " + inventory.size());
    }

    private void exportData() throws IOException {
        Path output = Path.of(readText("Путь к текстовому файлу экспорта: "));
        if (output.toAbsolutePath().normalize().equals(Path.of("data", "home-appliances.bin").toAbsolutePath().normalize())) {
            throw new IllegalArgumentException("Нельзя перезаписывать бинарный файл данных.");
        }
        exchange.exportTo(output, inventory);
        out.println("Экспортировано категорий: " + inventory.categories().size()
                + ", записей: " + inventory.size() + " → " + output.toAbsolutePath());
    }

    private void changePageSize() throws IOException {
        pageSize = readInt("Сколько строк показывать на странице (1–100): ", 1, 100);
        save();
        out.println("Настройка сохранена.");
    }

    private void changeSortOrder() throws IOException {
        out.println("1. По названию   2. По категории   3. По бренду   4. По дате покупки");
        int choice = readInt("Порядок сортировки: ", 1, 4);
        sortBy = switch (choice) {
            case 1 -> Inventory.SortBy.NAME;
            case 2 -> Inventory.SortBy.CATEGORY;
            case 3 -> Inventory.SortBy.BRAND;
            case 4 -> Inventory.SortBy.PURCHASE_DATE;
            default -> throw new IllegalStateException("Неизвестный порядок сортировки.");
        };
        save();
        out.println("Сортировка сохранена: " + sortLabel(sortBy));
    }

    private void showPage(List<Appliance> items, String heading) {
        if (items.isEmpty()) { out.println(heading + ": записей нет."); return; }
        int pageCount = (items.size() + pageSize - 1) / pageSize;
        int page = 0;
        while (page < pageCount) {
            int start = page * pageSize;
            int end = Math.min(start + pageSize, items.size());
            out.printf("\n%s — страница %d из %d (записи %d–%d из %d)%n",
                    heading, page + 1, pageCount, start + 1, end, items.size());
            for (int i = start; i < end; i++) printItem(i + 1, items.get(i));
            if (page + 1 >= pageCount) break;
            if (!confirm("Показать следующую страницу? (д/н): ")) break;
            page++;
        }
    }

    private void printItem(int number, Appliance a) {
        out.printf("%d. %s | %s | %s %s | покупка: %s | гарантия: %d мес.%n",
                number, a.name(), a.category(), a.brand(), a.model(), a.purchaseDate(), a.warrantyMonths());
        if (!a.note().isBlank()) out.println("   Заметка: " + a.note());
    }

    private String sortLabel(Inventory.SortBy value) {
        return switch (value) {
            case NAME -> "по названию";
            case CATEGORY -> "по категории";
            case BRAND -> "по бренду";
            case PURCHASE_DATE -> "по дате покупки";
        };
    }

    private void save() throws IOException { repository.save(new AppData(inventory, pageSize, sortBy)); }

    private void saveAndExit() {
        try { save(); out.println("Данные сохранены. До свидания!"); }
        catch (IOException e) { out.println("Не удалось сохранить данные: " + e.getMessage()); }
    }

    private String readText(String prompt) { return readText(prompt, false); }
    private String readText(String prompt, boolean allowEmpty) {
        while (true) {
            out.print(prompt);
            String value = readLine().trim();
            if (allowEmpty || !value.isEmpty()) return value;
            out.println("Поле не должно быть пустым.");
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            String value = readText(prompt);
            try { return LocalDate.parse(value); }
            catch (DateTimeParseException e) { out.println("Введите дату в формате yyyy-MM-dd."); }
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            out.print(prompt);
            try {
                int value = Integer.parseInt(readLine().trim());
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { /* Повторный запрос. */ }
            out.printf("Введите целое число от %d до %d.%n", min, max);
        }
    }

    private boolean confirm(String prompt) {
        out.print(prompt);
        String value = readLine().trim();
        return value.equalsIgnoreCase("д") || value.equalsIgnoreCase("да")
                || value.equalsIgnoreCase("y") || value.equalsIgnoreCase("yes");
    }

    private String readLine() {
        if (!scanner.hasNextLine()) throw new EndOfInput();
        return scanner.nextLine();
    }

    private static final class EndOfInput extends RuntimeException { }
}
