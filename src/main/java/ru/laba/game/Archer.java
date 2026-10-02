package ru.laba.game;

/** Стрелок наносит урон точным выстрелом. */
public final class Archer extends Character {
    private int precision;

    public Archer(String name, int health, int attack, int precision) {
        super(name, health, attack);
        setPrecision(precision);
    }

    @Override public int calculateDamage() { return getAttack() + precision / 2; }
    @Override public String getAttackType() { return "точный выстрел"; }
    @Override public String getClassName() { return "Стрелок"; }
    @Override public String getSpecialInfo() { return "точность: " + precision; }
    public int getPrecision() { return precision; }

    public void setPrecision(int precision) {
        if (precision < 0 || precision > 100) throw new IllegalArgumentException("Точность стрелка должна быть от 0 до 100.");
        this.precision = precision;
    }
}
