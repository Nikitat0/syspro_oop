package ru.nikitat0.blackjack.cards;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CardTest {
    static void assertStringEquals(Object o1, Object o2) {
        Assertions.assertEquals(o1.toString(), o2.toString());
    }

    @Test
    void testCardNames() {
        assertStringEquals("Туз Черви", new Card(Suit.HEARTS, Rank.ACE));
        assertStringEquals("Двойка Бубны", new Card(Suit.DIAMONDS, Rank.TWO));
        assertStringEquals("Тройка Трефы", new Card(Suit.CLUBS, Rank.THREE));
        assertStringEquals("Четвёрка Пики", new Card(Suit.SPADES, Rank.FOUR));
        assertStringEquals("Пятёрка Черви", new Card(Suit.HEARTS, Rank.FIVE));
        assertStringEquals("Шестёрка Бубны", new Card(Suit.DIAMONDS, Rank.SIX));
        assertStringEquals("Семёрка Трефы", new Card(Suit.CLUBS, Rank.SEVEN));
        assertStringEquals("Восмёрка Пики", new Card(Suit.SPADES, Rank.EIGHT));
        assertStringEquals("Девятка Черви", new Card(Suit.HEARTS, Rank.NINE));
        assertStringEquals("Десятка Бубны", new Card(Suit.DIAMONDS, Rank.TEN));

        assertStringEquals("Трефовый валет", new Card(Suit.CLUBS, Rank.JACK));
        assertStringEquals("Пиковый валет", new Card(Suit.SPADES, Rank.JACK));
        assertStringEquals("Червонный король", new Card(Suit.HEARTS, Rank.KING));
        assertStringEquals("Бубновый король", new Card(Suit.DIAMONDS, Rank.KING));

        assertStringEquals("Трефовая дама", new Card(Suit.CLUBS, Rank.QUEEN));
        assertStringEquals("Пиковая дама", new Card(Suit.SPADES, Rank.QUEEN));
        assertStringEquals("Червонная дама", new Card(Suit.HEARTS, Rank.QUEEN));
        assertStringEquals("Бубновая дама", new Card(Suit.DIAMONDS, Rank.QUEEN));
    }

    @Test
    void testDeck() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.pick();
        }
        Assertions.assertThrows(NoSuchElementException.class, () -> deck.pick());
    }
}
