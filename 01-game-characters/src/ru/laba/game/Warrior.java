package ru.laba.game;

/** Воин наносит усиленный удар ближнего боя. */
public final class Warrior extends Character {
    private int strength;

    public Warrior(String name, int health, int attack, int strength) {
        super(name, health, attack);
        setStrength(strength);
    }

    @Override public int calculateDamage() { return getAttack() + strength; }
    @Override public String getAttackType() { return "тяжёлый удар"; }
    @Override public String getClassName() { return "Воин"; }
    @Override public String getSpecialInfo() { return "сила: " + strength; }
    public int getStrength() { return strength; }

    public void setStrength(int strength) {
        if (strength < 0 || strength > 100) throw new IllegalArgumentException("Сила воина должна быть от 0 до 100.");
        this.strength = strength;
    }
}
