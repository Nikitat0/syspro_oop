package ru.nikitat0.blackjack.game;

import ru.nikitat0.blackjack.cards.Deck;

/**
 * An object providing a way to obtain new decks.
 */
@FunctionalInterface
public interface DeckProvider {
    /**
     * Obtains new deck.
     * 
     * @return new deck
     */
    Deck obtainDeck();
}
