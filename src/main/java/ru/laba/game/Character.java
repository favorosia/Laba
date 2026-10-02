package ru.laba.game;

public abstract class Character {
    private String name;
    private int health;
    private int attack;

    protected Character(String name, int health, int attack) {
        setName(name);
        setHealth(health);
        setAttack(attack);
    }

    public abstract int calculateDamage();
    public abstract String getAttackType();
    public abstract String getClassName();
    public abstract String getSpecialInfo();

    public final void receiveDamage(int damage) {
        health = Math.max(0, health - Math.max(0, damage));
    }

    public final boolean isAlive() { return health > 0; }
    public final String getName() { return name; }
    public final int getHealth() { return health; }
    public final int getAttack() { return attack; }

    public final void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Имя не должно быть пустым.");
        this.name = name.trim();
    }

    public final void setHealth(int health) {
        if (health < 1 || health > 1000) throw new IllegalArgumentException("Здоровье должно быть от 1 до 1000.");
        this.health = health;
    }

    public final void setAttack(int attack) {
        if (attack < 1 || attack > 200) throw new IllegalArgumentException("Сила атаки должна быть от 1 до 200.");
        this.attack = attack;
    }

    @Override
    public final String toString() {
        return String.format("%s «%s» | здоровье: %d | сила атаки: %d | %s",
                getClassName(), name, health, attack, getSpecialInfo());
    }
}
