package ru.nikitat0.blackjack.cards;

import java.util.List;
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

    @Test
    void testEmptyCardSet() {
        CardSet cards = new CardSet();
        Assertions.assertEquals(0, cards.points());
        Assertions.assertFalse(cards.isBlackjack());
        Assertions.assertTrue(cards.asList().isEmpty());
    }

    @Test
    void testCardSet() {
        Suit suit = Suit.CLUBS;
        Card ace = new Card(suit, Rank.ACE);
        Card two = new Card(suit, Rank.TWO);
        Card ten = new Card(suit, Rank.TEN);
        Card jack = new Card(suit, Rank.JACK);

        Assertions.assertEquals(2, new CardSet(ace, ace).points());
        Assertions.assertEquals(13, new CardSet(ace, two).points());
        Assertions.assertEquals(20, new CardSet(ten, jack).points());
        Assertions.assertEquals(21, new CardSet(ace, jack).points());

        Assertions.assertTrue(new CardSet(ace, jack).isBlackjack());
        Assertions.assertFalse(new CardSet(ace, ace).isBlackjack());

        CardSet cards = new CardSet();
        cards.add(jack);
        cards.add(ace);
        Assertions.assertTrue(cards.isBlackjack());
        Assertions.assertEquals(21, cards.points());
        Assertions.assertEquals(11, cards.acePoints());
        cards.add(ace);
        Assertions.assertFalse(cards.isBlackjack());
        Assertions.assertEquals(12, cards.points());
        Assertions.assertEquals(1, cards.acePoints());

        List<Card> cardList = cards.asList();
        Assertions.assertEquals(jack, cardList.get(0));
        Assertions.assertEquals(ace, cardList.get(1));
        Assertions.assertEquals(ace, cardList.get(2));
    }

    @Test
    void testCardView() {
        Assertions.assertTrue(CardView.CLOSED_CARD.see(new CardViewer<Boolean>() {
            @Override
            public Boolean cardWithPoints(Card card, int points) {
                return false;
            }

            @Override
            public Boolean closedCard() {
                return true;
            }
        }));

        Card card = new Card(Suit.CLUBS, Rank.JACK);
        Assertions.assertEquals(card.rank.points, new CardView.CardWithPoints(card).points);
        Assertions.assertTrue(new CardView.CardWithPoints(card, 0).see(new CardViewer<Boolean>() {
            @Override
            public Boolean cardWithPoints(Card actualCard, int actualPoints) {
                Assertions.assertEquals(card, actualCard);
                Assertions.assertEquals(0, actualPoints);
                return true;
            }

            @Override
            public Boolean closedCard() {
                return false;
            }
        }));
    }
}
