package ru.nikitat0.blackjack.cards;

/**
 * A card suit.
 */
public enum Suit {
    HEARTS("Черви", "Червонный", "Червонная"),
    DIAMONDS("Бубны", "Бубновый", "Бубновая"),
    CLUBS("Трефы", "Трефовый", "Трефовая"),
    SPADES("Пики", "Пиковый", "Пиковая");

    private final String name;

    /**
     * Adjective used in name of queen card.
     */
    public final String masculineAdjective;

    /**
     * Adjective used in name of jack or king card.
     */
    public final String femenineAdjective;

    Suit(String name, String masculine, String femenine) {
        this.name = name;
        this.masculineAdjective = masculine;
        this.femenineAdjective = femenine;
    }

    @Override
    public String toString() {
        return name;
    }
}
