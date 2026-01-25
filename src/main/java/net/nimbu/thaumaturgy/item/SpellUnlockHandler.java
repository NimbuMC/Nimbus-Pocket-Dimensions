package net.nimbu.thaumaturgy.item;

public interface SpellUnlockHandler {
    boolean getSpellUnlockFlags(int index);
    void setSpellUnlockFlags(int index, boolean value);
}
