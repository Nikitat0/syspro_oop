package ru.nikitat0.blackjack.cards;

/**
 * A card rank.
 */
public enum Rank {
    ACE(11, "Туз"),
    TWO(2, "Двойка"),
    THREE(3, "Тройка"),
    FOUR(4, "Четвёрка"),
    FIVE(5, "Пятёрка"),
    SIX(6, "Шестёрка"),
    SEVEN(7, "Семёрка"),
    EIGHT(8, "Восмёрка"),
    NINE(9, "Девятка"),
    TEN(10, "Десятка"),
    JACK(10, "Валет", NameFormat.MASCULINE),
    QUEEN(10, "Дама", NameFormat.FEMENINE),
    KING(10, "Король", NameFormat.MASCULINE);

    /**
     * How many points card with this rank gives by itself.
     */
    public final int points;

    private final String name;
    private final NameFormat fmt;

    Rank(int points, String name) {
        this.points = points;
        this.name = name;
        this.fmt = NameFormat.MAIN;
    }

    Rank(int points, String name, NameFormat fmt) {
        this.points = points;
        this.name = name;
        this.fmt = fmt;
    }

    /**
     * @return name of card with this rank and given suit
     */
    public String cardName(Suit suit) {
        if (fmt == NameFormat.MAIN) {
            return String.format("%s %s", this, suit);
        } else {
            boolean isMasculine = fmt == NameFormat.MASCULINE;
            String adjective = isMasculine ? suit.masculineAdjective : suit.femenineAdjective;
            return String.format("%s %s", adjective, this.toString().toLowerCase());
        }
    }

    private static enum NameFormat {
        MAIN,
        MASCULINE,
        FEMENINE;
    }

    @Override
    public String toString() {
        return name;
    }
}
