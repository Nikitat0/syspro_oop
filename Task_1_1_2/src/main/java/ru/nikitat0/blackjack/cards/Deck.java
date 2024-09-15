package ru.nikitat0.blackjack.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A 52-card deck.
 */
public final class Deck {
    private static final List<Card> DECK_52;

    static {
        List<Card> cards = new ArrayList<Card>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        DECK_52 = Collections.unmodifiableList(cards);
    }

    private final List<Card> cards;

    /**
     * Creates new (unshuffled) deck.
     */
    public Deck() {
        this.cards = new ArrayList<Card>(DECK_52);
    }

    /**
     * Shuffles this deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Picks a card from this deck.
     * 
     * @return card lying on top
     *
     * @throws NoSuchElementException if deck is empty
     */
    public Card pick() {
        if (cards.isEmpty()) {
            throw new NoSuchElementException("deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }
}
