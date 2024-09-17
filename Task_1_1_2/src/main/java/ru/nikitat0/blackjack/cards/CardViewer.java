package ru.nikitat0.blackjack.cards;

/**
 * An entity seeing the card.
 *
 * @param <T>
 */
public interface CardViewer<T> {
    /**
     * Called when viewer see a card with assigned points.
     * 
     * @param card
     * @param points
     * @return
     */
    T cardWithPoints(Card card, int points);

    /**
     * Called when viewer see a closed card.
     */
    T closedCard();
}
