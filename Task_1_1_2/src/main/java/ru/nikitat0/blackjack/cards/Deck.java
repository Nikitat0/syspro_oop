package ru.nikitat0.blackjack.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A playing deck.
 */
public interface Deck {
    /**
     * Picks a card.
     *
     * @return picked card
     */
    Card pick();

    /**
     * Returns true if there is no more cards.
     *
     * @return true if there is no more cards
     */
    boolean isEmpty();

    /**
     * A standart playing deck constisting of 52 cards.
     */
    public static class Deck52 implements Deck {
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

        private final LinkedList<Card> cards;

        /**
         * Constructs new unshuffled deck.
         */
        public Deck52() {
            cards = new LinkedList<>(DECK_52);
        }

        /**
         * Constructs new randomly shuffled deck.
         *
         * @param rand the source of randomness
         */
        public Deck52(Random rand) {
            this();
            shuffle(rand);
        }

        /**
         * Shuffles this deck.
         *
         * @param rand the source of randomness
         */
        public void shuffle(Random rand) {
            Collections.shuffle(cards, rand);
        }

        @Override
        public Card pick() {
            return cards.pop();
        }

        @Override
        public boolean isEmpty() {
            return cards.isEmpty();
        }
    }
}
