package ru.nikitat0.blackjack.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A set of cards.
 */
public final class CardSet {
    private final ArrayList<Card> cards;

    /**
     * Constructs an empty set of cards.
     */
    public CardSet() {
        cards = new ArrayList<>();
    }

    /**
     * Constructs a set of two given cards.
     * 
     * @param a first card
     * @param b second card
     */
    public CardSet(Card a, Card b) {
        cards = new ArrayList<>();
        cards.add(a);
        cards.add(b);
    }

    /**
     * Adds card to this set.
     *
     * @param card card to add
     */
    public void add(Card card) {
        cards.add(card);
    }

    /**
     * Returns this set as list.
     *
     * @return unmodifaible list representations of card set.
     */
    public List<Card> asList() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Counts points of this set of card by blackjack rules.
     *
     * @return number of points
     */
    public int points() {
        int points = 0;
        int aceNum = 0;
        for (Card card : cards) {
            points += card.rank.points;
            aceNum += card.rank == Rank.ACE ? 1 : 0;
        }
        return points - (points > 21 ? 10 * aceNum : 0);
    }

    /**
     * Calculates points given by each ace in this set.
     *
     * @return how many points each ace give
     */
    public int acePoints() {
        int points = 0;
        for (Card card : cards) {
            points += card.rank.points;
        }
        return points > 21 ? 1 : 11;
    }

    /**
     * Returns true if this set is blackjack.
     *
     * @return true, if there is two cards with a total of 21 points
     */
    public boolean isBlackjack() {
        if (cards.size() != 2) {
            return false;
        }
        return cards.get(0).rank.points + cards.get(1).rank.points == 21;
    }
}
