package ru.laba.appliances;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Правила работы с техникой и её категориями. */
public final class Inventory implements Serializable {
    @Serial private static final long serialVersionUID = 1L;
    private final List<Appliance> appliances = new ArrayList<>();
    private final Set<String> categories = new LinkedHashSet<>();

    public void addCategory(String name) {
        String value = cleanCategory(name);
        if (findCategory(value) != null) throw new IllegalArgumentException("Такая категория уже существует.");
        categories.add(value);
    }

    public void renameCategory(String oldName, String newName) {
        String oldValue = findCategory(oldName);
        if (oldValue == null) throw new IllegalArgumentException("Категория не найдена.");
        String newValue = cleanCategory(newName);
        String duplicate = findCategory(newValue);
        if (duplicate != null && !duplicate.equals(oldValue)) throw new IllegalArgumentException("Такая категория уже существует.");
        if (oldValue.equals(newValue)) return;
        List<String> replacement = categories.stream().map(c -> c.equals(oldValue) ? newValue : c).toList();
        categories.clear();
        categories.addAll(replacement);
        for (int i = 0; i < appliances.size(); i++) {
            Appliance item = appliances.get(i);
            if (item.category().equals(oldValue)) appliances.set(i, item.withCategory(newValue));
        }
    }

    public void add(Appliance appliance) {
        if (appliance == null) throw new IllegalArgumentException("Техника не задана.");
        String category = findCategory(appliance.category());
        if (category == null) throw new IllegalArgumentException("Сначала добавьте категорию «" + appliance.category() + "».");
        if (appliances.stream().anyMatch(item -> item.id().equals(appliance.id()))) throw new IllegalArgumentException("Такая запись уже есть.");
        appliances.add(appliance.category().equals(category) ? appliance : appliance.withCategory(category));
    }

    public Appliance get(int index) {
        if (index < 0 || index >= appliances.size()) throw new IllegalArgumentException("Запись с таким номером не найдена.");
        return appliances.get(index);
    }

    public int indexOf(String id) {
        for (int i = 0; i < appliances.size(); i++) {
            if (appliances.get(i).id().equals(id)) return i;
        }
        return -1;
    }

    public void update(int index, Appliance updated) {
        get(index);
        if (!categories.contains(updated.category())) throw new IllegalArgumentException("Выберите существующую категорию.");
        String category = findCategory(updated.category());
        appliances.set(index, updated.category().equals(category) ? updated : updated.withCategory(category));
    }

    public Appliance remove(int index) { Appliance old = get(index); appliances.remove(index); return old; }

    public List<Appliance> all() { return List.copyOf(appliances); }
    public List<String> categories() { return List.copyOf(categories); }
    public int size() { return appliances.size(); }

    public List<Appliance> search(String query) {
        String term = query.toLowerCase(Locale.ROOT).trim();
        return appliances.stream().filter(a -> a.name().toLowerCase(Locale.ROOT).contains(term)
                || a.category().toLowerCase(Locale.ROOT).contains(term)
                || a.brand().toLowerCase(Locale.ROOT).contains(term)
                || a.model().toLowerCase(Locale.ROOT).contains(term)
                || a.note().toLowerCase(Locale.ROOT).contains(term)).toList();
    }

    public List<Appliance> sorted(SortBy sortBy) {
        Comparator<Appliance> comparator = switch (sortBy) {
            case NAME -> Comparator.comparing(Appliance::name, String.CASE_INSENSITIVE_ORDER);
            case CATEGORY -> Comparator.comparing(Appliance::category, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Appliance::name, String.CASE_INSENSITIVE_ORDER);
            case BRAND -> Comparator.comparing(Appliance::brand, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Appliance::name, String.CASE_INSENSITIVE_ORDER);
            case PURCHASE_DATE -> Comparator.comparing(Appliance::purchaseDate).thenComparing(Appliance::name);
        };
        return appliances.stream().sorted(comparator).toList();
    }

    public boolean hasCategory(String name) { return findCategory(name) != null; }

    private String findCategory(String name) {
        if (name == null) return null;
        return categories.stream().filter(c -> c.equalsIgnoreCase(name.trim())).findFirst().orElse(null);
    }

    private static String cleanCategory(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Название категории не должно быть пустым.");
        return name.trim();
    }

    public enum SortBy { NAME, CATEGORY, BRAND, PURCHASE_DATE }
}
