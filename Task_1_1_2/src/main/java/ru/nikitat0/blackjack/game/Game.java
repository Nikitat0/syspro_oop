package ru.nikitat0.blackjack.game;

import java.util.ArrayList;
import java.util.List;
import ru.nikitat0.blackjack.cards.Card;
import ru.nikitat0.blackjack.cards.CardSet;
import ru.nikitat0.blackjack.cards.CardView;
import ru.nikitat0.blackjack.cards.CardView.CardWithPoints;
import ru.nikitat0.blackjack.cards.Deck;
import ru.nikitat0.blackjack.cards.Rank;
import ru.nikitat0.blackjack.game.PlayerController.Cause;
import ru.nikitat0.blackjack.game.PlayerController.Side;

import static ru.nikitat0.blackjack.cards.CardView.CLOSED_CARD;

/**
 * A blackjack game.
 */
public final class Game {
    private Score score = new Score();
    private PlayerController playerController;

    /**
     * Constructs new game.
     * 
     * @param playerController
     */
    public Game(PlayerController playerController) {
        this.playerController = playerController;
    }

    /**
     * Starts this game.
     */
    public void play() {
        do {
            new Round().play();
        } while (playerController.doPlayNextRound());
    }

    /**
     * @return current score
     */
    public Score getScore() {
        return score;
    }

    private void playerWins() {
        score = score.incLeft();
    }

    private void dealerWins() {
        score = score.incRight();
    }

    private static List<CardView> seeHand(CardSet hand) {
        List<CardView> view = new ArrayList<>();
        int acePoints = hand.acePoints();
        for (Card card : hand.asList()) {
            int points = card.rank == Rank.ACE ? acePoints : card.rank.points;
            view.add(new CardWithPoints(card, points));
        }
        return view;
    }

    /**
     * A round of the blackjack game.
     */
    public final class Round {
        private final Deck deck;
        private final CardSet playerHand;
        private final CardSet dealerHand;

        private boolean dealerTurn = false;

        private Round() {
            deck = new Deck();
            deck.shuffle();
            playerHand = new CardSet(deck.pick(), deck.pick());
            dealerHand = new CardSet(deck.pick(), deck.pick());
        }

        private void play() {
            playerController.onRoundBegins(this);
            if (playerHand.isBlackjack()) {
                playerWins();
                playerController.onRoundEnds(Side.PLAYER, Cause.BLACKJACK);
                return;
            }
            while (playerController.doPickCard()) {
                Card card = deck.pick();
                playerHand.add(card);
                playerController.onPick(Side.PLAYER, card);
                if (playerHand.points() > 21) {
                    dealerWins();
                    playerController.onRoundEnds(Side.DEALER, Cause.BUST);
                    return;
                }
            }
            dealerTurn = true;
            playerController.onDealerTurn(dealerHand.asList().get(1));
            if (dealerHand.isBlackjack()) {
                dealerWins();
                playerController.onRoundEnds(Side.DEALER, Cause.BLACKJACK);
                return;
            }
            while (dealerHand.points() < 17) {
                Card card = deck.pick();
                dealerHand.add(card);
                playerController.onPick(Side.DEALER, card);
                if (dealerHand.points() > 21) {
                    playerWins();
                    playerController.onRoundEnds(Side.PLAYER, Cause.BUST);
                    return;
                }
            }
            Side winner;
            if (dealerHand.points() >= playerHand.points()) {
                winner = Side.DEALER;
                dealerWins();
            } else {
                winner = Side.PLAYER;
                playerWins();
            }
            playerController.onRoundEnds(winner, Cause.HIGHER_SUM);
        }

        /**
         * @return ordinal number of this round
         */
        public int ordinalNumber() {
            return score.left + score.right + 1;
        }

        /**
         * Shows player hand.
         * 
         * @return
         */
        public List<CardView> seePlayerHand() {
            return seeHand(playerHand);
        }

        /**
         * Shows dealer hand.
         * 
         * @return
         */
        public List<CardView> seeDealerHand() {
            if (!dealerTurn) {
                List<CardView> view = new ArrayList<>();
                view.add(new CardWithPoints(dealerHand.asList().get(0)));
                view.add(CLOSED_CARD);
                return view;
            }
            return seeHand(dealerHand);
        }
    }
}
