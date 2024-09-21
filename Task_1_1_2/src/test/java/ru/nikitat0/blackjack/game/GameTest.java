package ru.nikitat0.blackjack.game;

import static ru.nikitat0.blackjack.game.PlayerMock.CONTINUE;
import static ru.nikitat0.blackjack.game.PlayerMock.DO_NOT_PICK_CARD;
import static ru.nikitat0.blackjack.game.PlayerMock.PICK_CARD;

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
    static final Card TWO = new Card(Suit.CLUBS, Rank.TWO);
    static final Card SIX = new Card(Suit.CLUBS, Rank.SIX);
    static final Card SEVEN = new Card(Suit.CLUBS, Rank.SEVEN);
    static final Card EIGHT = new Card(Suit.CLUBS, Rank.EIGHT);
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

    @Test
    void testFourRounds() {
        DeckProvider decks = new DeckProviderMock()
                .addDeck(EIGHT, EIGHT, JACK, TWO, EIGHT)
                .addDeck(JACK, JACK, JACK, SIX, EIGHT)
                .addDeck(JACK, JACK, JACK, SEVEN)
                .addDeck(TWO, TWO, JACK, SIX, TWO);
        final Game[] gameRef = new Game[1];
        PlayerMock mock = new PlayerMock()
                .addStep(new Step() {
                    @Override
                    public void onRoundBegins(Round round) {
                        Assertions.assertEquals(1, round.ordinalNumber());
                    }
                })
                .addStep(PICK_CARD)
                .addStep(new Step() {
                    @Override
                    public void onPick(Side who, Card card) {
                        Assertions.assertEquals(Side.PLAYER, who);
                        Assertions.assertEquals(EIGHT, card);
                    }
                })
                .addStep(new Step() {
                    @Override
                    public void onRoundEnds(Side winner, Cause reason) {
                        Assertions.assertEquals(Side.DEALER, winner);
                        Assertions.assertEquals(Cause.BUST, reason);
                        Assertions.assertEquals(new Score(0, 1), gameRef[0].getScore());
                    }
                })
                .addStep(CONTINUE)
                .addStep(new Step() {
                    @Override
                    public void onRoundBegins(Round round) {
                        Assertions.assertEquals(2, round.ordinalNumber());
                    }
                })
                .addStep(DO_NOT_PICK_CARD)
                .addStep(new Step() {
                    @Override
                    public void onDealerTurn(Card card) {
                        Assertions.assertEquals(SIX, card);
                    }
                })
                .addStep(new Step() {
                    @Override
                    public void onPick(Side who, Card card) {
                        Assertions.assertEquals(Side.DEALER, who);
                        Assertions.assertEquals(EIGHT, card);
                    }
                })
                .addStep(new Step() {
                    @Override
                    public void onRoundEnds(Side winner, Cause reason) {
                        Assertions.assertEquals(Side.PLAYER, winner);
                        Assertions.assertEquals(Cause.BUST, reason);
                        Assertions.assertEquals(new Score(1, 1), gameRef[0].getScore());
                    }
                })
                .addStep(CONTINUE)
                .addStep(new Step() {
                    @Override
                    public void onRoundBegins(Round round) {
                        Assertions.assertEquals(3, round.ordinalNumber());
                    }
                })
                .addStep(DO_NOT_PICK_CARD)
                .addStep(new Step() {
                    @Override
                    public void onDealerTurn(Card card) {
                        Assertions.assertEquals(SEVEN, card);
                    }
                })
                .addStep(new Step() {
                    @Override
                    public void onRoundEnds(Side winner, Cause reason) {
                        Assertions.assertEquals(Side.PLAYER, winner);
                        Assertions.assertEquals(Cause.HIGHER_SUM, reason);
                        Assertions.assertEquals(new Score(2, 1), gameRef[0].getScore());
                    }
                })
                .addStep(CONTINUE)
                .addStep(new Step() {
                    @Override
                    public void onRoundBegins(Round round) {
                        Assertions.assertEquals(4, round.ordinalNumber());
                    }
                })
                .addStep(DO_NOT_PICK_CARD)
                .addStep(new Step() {
                    @Override
                    public void onDealerTurn(Card card) {
                        Assertions.assertEquals(SIX, card);
                    }
                })
                .addStep(new Step() {
                    @Override
                    public void onPick(Side who, Card card) {
                        Assertions.assertEquals(Side.DEALER, who);
                        Assertions.assertEquals(TWO, card);
                    }
                })
                .addStep(new Step() {
                    @Override
                    public void onRoundEnds(Side winner, Cause reason) {
                        Assertions.assertEquals(Side.DEALER, winner);
                        Assertions.assertEquals(Cause.HIGHER_SUM, reason);
                        Assertions.assertEquals(new Score(2, 2), gameRef[0].getScore());
                    }
                });
        Game game = new Game(mock, decks);
        gameRef[0] = game;
        game.subscribe(mock);
        game.play();
        mock.assertFinished();
    }
}
