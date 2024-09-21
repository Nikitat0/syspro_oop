package ru.nikitat0.blackjack.game;

import static ru.nikitat0.blackjack.game.PlayerMock.DO_NOT_PICK_CARD;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nikitat0.blackjack.cards.Card;
import ru.nikitat0.blackjack.cards.Rank;
import ru.nikitat0.blackjack.cards.Suit;
import ru.nikitat0.blackjack.game.Game.Cause;
import ru.nikitat0.blackjack.game.Game.Round;
import ru.nikitat0.blackjack.game.Game.Side;
import ru.nikitat0.blackjack.game.PlayerMock.Step;

class GameTest {
    static final Card ACE = new Card(Suit.CLUBS, Rank.ACE);
    static final Card TWO = new Card(Suit.CLUBS, Rank.ACE);
    static final Card JACK = new Card(Suit.CLUBS, Rank.JACK);

    @Test
    void testScore() {
        Assertions.assertEquals(new Score(0, 0), new Score());
        Assertions.assertEquals(new Score(1, 0), new Score().incLeft());
        Assertions.assertEquals(new Score(0, 1), new Score().incRight());
        Assertions.assertEquals(new Score(1, 1), new Score().incLeft().incRight());
        Score score = new Score(26, 62);
        Assertions.assertEquals(26, score.left);
        Assertions.assertEquals(62, score.right);
        Assertions.assertEquals("26:62", score.toString());
        Assertions.assertNotEquals(new Object(), new Score());
    }

    @Test
    void testPlayerBlackjack() {
        DeckProvider decks = new DeckProviderMock().addDeck(ACE, JACK, TWO, TWO);
        PlayerMock mock = new PlayerMock()
                .addStep(new Step() {
                    @Override
                    public void onRoundBegins(Round round) {
                    }
                })
                .addStep(new Step() {
                    @Override
                    public void onRoundEnds(Side winner, Cause reason) {
                        Assertions.assertEquals(Side.PLAYER, winner);
                        Assertions.assertEquals(Cause.BLACKJACK, reason);
                    }
                });
        Game game = new Game(mock, decks);
        game.subscribe(mock);
        game.play();
        mock.assertFinished();
        Assertions.assertEquals(new Score(1, 0), game.getScore());
    }

    @Test
    void testDealerBlackjack() {
        DeckProvider decks = new DeckProviderMock().addDeck(TWO, TWO, ACE, JACK);
        PlayerMock mock = new PlayerMock()
                .addStep(new Step() {
                    @Override
                    public void onRoundBegins(Round round) {
                    }
                })
                .addStep(DO_NOT_PICK_CARD)
                .addStep(new Step() {
                    @Override
                    public void onDealerTurn(Card card) {
                        Assertions.assertEquals(JACK, card);
                    }
                })
                .addStep(new Step() {
                    @Override
                    public void onRoundEnds(Side winner, Cause reason) {
                        Assertions.assertEquals(Side.DEALER, winner);
                        Assertions.assertEquals(Cause.BLACKJACK, reason);
                    }
                });
        Game game = new Game(mock, decks);
        game.subscribe(mock);
        game.play();
        mock.assertFinished();
        Assertions.assertEquals(new Score(0, 1), game.getScore());
    }
}
