package ru.nikitat0.blackjack.game;

import java.util.ArrayDeque;
import java.util.Deque;
import ru.nikitat0.blackjack.cards.Card;
import ru.nikitat0.blackjack.cards.Deck;

class DeckProviderMock implements DeckProvider {
    Deque<DeckMock> decks = new ArrayDeque<>();

    DeckProviderMock addDeck(Card... cards) {
        decks.push(new DeckMock(cards));
        return this;
    }

    @Override
    public Deck obtainDeck() {
        return decks.remove();
    }

    private static class DeckMock implements Deck {
        Deque<Card> cards = new ArrayDeque<>();

        public DeckMock(Card... cards) {
            for (Card card : cards) {
                this.cards.add(card);
            }
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
