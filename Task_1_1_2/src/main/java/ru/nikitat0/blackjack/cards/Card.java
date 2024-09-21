package ru.nikitat0.blackjack.cards;

/**
 * A playing card.
 */
public final class Card {
    /**
     * Suit of this card.
     */
    public final Suit suit;

    /**
     * Rank of this card.
     */
    public final Rank rank;

    /**
     * Constructs a card.
     *
     * @param suit suit
     * @param rank rank
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return rank.cardName(suit);
    }
}
