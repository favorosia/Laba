package ru.laba.game;

import java.util.ArrayList;
import java.util.List;

/** Управляет коллекцией персонажей и ограничивает её размер. */
public final class CharacterManager {
    public static final int MAX_CHARACTERS = 10;
    private final List<Character> characters = new ArrayList<>();

    public boolean add(Character character) {
        if (characters.size() >= MAX_CHARACTERS) return false;
        characters.add(character);
        return true;
    }
    public Character get(int index) {
        if (index < 0 || index >= characters.size()) throw new IllegalArgumentException("Персонаж с таким номером не найден.");
        return characters.get(index);
    }
    public Character remove(int index) { get(index); return characters.remove(index); }
    public int size() { return characters.size(); }
    public List<Character> getAll() { return List.copyOf(characters); }
}
