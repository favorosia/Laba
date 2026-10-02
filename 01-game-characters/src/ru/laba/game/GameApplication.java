package ru.laba.game;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Консольный интерфейс управления игровыми персонажами. */
public final class GameApplication {
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private final CharacterManager manager = new CharacterManager();

    public static void main(String[] args) { new GameApplication().run(); }

    private void run() {
        out.println("=== Игровые персонажи ===");
        while (true) {
            printMenu();
            int choice = readInt("Ваш выбор: ", 0, 6);
            try {
                switch (choice) {
                    case 1 -> addCharacter();
                    case 2 -> editCharacter();
                    case 3 -> removeCharacter();
                    case 4 -> showCharacters();
                    case 5 -> performAttack();
                    case 6 -> showSummary();
                    case 0 -> { out.println("До свидания!"); return; }
                    default -> throw new IllegalStateException("Неизвестный пункт меню.");
                }
            } catch (IllegalArgumentException e) {
                out.println("Ошибка: " + e.getMessage());
            }
            out.println();
        }
    }

    private void printMenu() {
        out.println("\n1. Добавить персонажа\n2. Изменить персонажа\n3. Удалить персонажа"
                + "\n4. Показать всех персонажей\n5. Провести атаку\n6. Общая информация\n0. Выход");
    }

    private void addCharacter() {
        if (manager.size() >= CharacterManager.MAX_CHARACTERS) {
            out.println("Достигнут лимит: не более 10 персонажей."); return;
        }
        int type = readInt("Тип (1 — воин, 2 — стрелок): ", 1, 2);
        String name = readText("Имя: ");
        int health = readInt("Здоровье (1–1000): ", 1, 1000);
        int attack = readInt("Сила атаки (1–200): ", 1, 200);
        Character character = type == 1
                ? new Warrior(name, health, attack, readInt("Сила воина (0–100): ", 0, 100))
                : new Archer(name, health, attack, readInt("Точность стрелка (0–100): ", 0, 100));
        manager.add(character);
        out.println("Персонаж добавлен.");
    }

    private void editCharacter() {
        showCharacters();
        if (manager.size() == 0) return;
        Character character = manager.get(readInt("Номер персонажа: ", 1, manager.size()) - 1);
        out.println("Нажмите Enter, чтобы оставить текущее значение.");
        String name = readText("Имя [" + character.getName() + "]: ", true);
        if (!name.isEmpty()) character.setName(name);
        Integer health = readOptionalInt("Здоровье [" + character.getHealth() + "] (1–1000): ", 1, 1000);
        if (health != null) character.setHealth(health);
        Integer attack = readOptionalInt("Сила атаки [" + character.getAttack() + "] (1–200): ", 1, 200);
        if (attack != null) character.setAttack(attack);
        if (character instanceof Warrior warrior) {
            Integer value = readOptionalInt("Сила [" + warrior.getStrength() + "] (0–100): ", 0, 100);
            if (value != null) warrior.setStrength(value);
        } else if (character instanceof Archer archer) {
            Integer value = readOptionalInt("Точность [" + archer.getPrecision() + "] (0–100): ", 0, 100);
            if (value != null) archer.setPrecision(value);
        }
        out.println("Данные персонажа обновлены.");
    }

    private void removeCharacter() {
        showCharacters();
        if (manager.size() == 0) return;
        Character removed = manager.remove(readInt("Номер персонажа: ", 1, manager.size()) - 1);
        out.println("Персонаж «" + removed.getName() + "» удалён.");
    }

    private void showCharacters() {
        if (manager.size() == 0) { out.println("Список персонажей пуст."); return; }
        int i = 1;
        for (Character character : manager.getAll()) out.println(i++ + ". " + character);
    }

    private void performAttack() {
        if (manager.size() < 2) { out.println("Для атаки нужны как минимум два персонажа."); return; }
        showCharacters();
        Character attacker = manager.get(readInt("Номер атакующего: ", 1, manager.size()) - 1);
        Character target = manager.get(readInt("Номер цели: ", 1, manager.size()) - 1);
        if (attacker == target) { out.println("Нельзя атаковать самого себя."); return; }
        if (!attacker.isAlive()) { out.println("Павший персонаж не может атаковать."); return; }
        if (!target.isAlive()) { out.println("Цель уже повержена."); return; }
        int damage = attacker.calculateDamage();
        target.receiveDamage(damage);
        out.printf("%s «%s» применяет: %s. Урон: %d.%n", attacker.getClassName(), attacker.getName(), attacker.getAttackType(), damage);
        out.printf("У «%s» осталось здоровья: %d.%n", target.getName(), target.getHealth());
        if (!target.isAlive()) out.println("Персонаж «" + target.getName() + "» повержен!");
    }

    private void showSummary() {
        out.printf("Персонажей: %d из %d.%n", manager.size(), CharacterManager.MAX_CHARACTERS);
        long alive = manager.getAll().stream().filter(Character::isAlive).count();
        out.printf("В строю: %d; повержено: %d.%n", alive, manager.size() - alive);
        showCharacters();
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            out.print(prompt);
            String input = readLine();
            try {
                int value = Integer.parseInt(input.trim());
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { /* Повторный запрос при неверном вводе. */ }
            out.printf("Введите целое число от %d до %d.%n", min, max);
        }
    }

    private Integer readOptionalInt(String prompt, int min, int max) {
        while (true) {
            out.print(prompt);
            String input = readLine().trim();
            if (input.isEmpty()) return null;
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { /* Повторный запрос при неверном вводе. */ }
            out.printf("Введите целое число от %d до %d или нажмите Enter.%n", min, max);
        }
    }

    private String readText(String prompt) { return readText(prompt, false); }
    private String readText(String prompt, boolean allowEmpty) {
        while (true) {
            out.print(prompt);
            String value = readLine().trim();
            if (!value.isEmpty() || allowEmpty) return value;
            out.println("Имя не должно быть пустым.");
        }
    }

    private String readLine() {
        if (scanner.hasNextLine()) return scanner.nextLine();
        out.println("\nВвод завершён.");
        System.exit(0);
        return "";
    }
}
