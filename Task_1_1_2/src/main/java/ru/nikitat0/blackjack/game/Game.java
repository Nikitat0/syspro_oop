package ru.nikitat0.blackjack.game;

import static ru.nikitat0.blackjack.cards.CardView.CLOSED_CARD;

import java.util.ArrayList;
import java.util.List;
import ru.nikitat0.blackjack.cards.Card;
import ru.nikitat0.blackjack.cards.CardSet;
import ru.nikitat0.blackjack.cards.CardView;
import ru.nikitat0.blackjack.cards.CardView.CardWithPoints;
import ru.nikitat0.blackjack.cards.Deck;
import ru.nikitat0.blackjack.cards.Rank;

/**
 * A blackjack game.
 */
public final class Game {
    private Score score = new Score();
    private final PlayerController playerController;
    private final DeckProvider deckProvider;
    private final EventBus eventBus = new EventBus();

    /**
     * Constructs new game.
     *
     * @param playerController player controller
     */
    public Game(PlayerController playerController, DeckProvider deckProvider) {
        this.playerController = playerController;
        this.deckProvider = deckProvider;
    }

    /**
     * Subscribes listener on game events.
     * 
     * @param listener listener to subscribe
     */
    public void subscribe(EventListener listener) {
        eventBus.subscribe(listener);
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
     * Returns current game score.
     *
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
            deck = deckProvider.obtainDeck();
            playerHand = new CardSet(deck.pick(), deck.pick());
            dealerHand = new CardSet(deck.pick(), deck.pick());
        }

        private void play() {
            eventBus.onRoundBegins(this);
            if (playerHand.isBlackjack()) {
                playerWins();
                eventBus.onRoundEnds(Side.PLAYER, Cause.BLACKJACK);
                return;
            }
            while (playerController.doPickCard()) {
                Card card = deck.pick();
                playerHand.add(card);
                eventBus.onPick(Side.PLAYER, card);
                if (playerHand.points() > 21) {
                    dealerWins();
                    eventBus.onRoundEnds(Side.DEALER, Cause.BUST);
                    return;
                }
            }
            dealerTurn = true;
            eventBus.onDealerTurn(dealerHand.asList().get(1));
            if (dealerHand.isBlackjack()) {
                dealerWins();
                eventBus.onRoundEnds(Side.DEALER, Cause.BLACKJACK);
                return;
            }
            while (dealerHand.points() < 17) {
                Card card = deck.pick();
                dealerHand.add(card);
                eventBus.onPick(Side.DEALER, card);
                if (dealerHand.points() > 21) {
                    playerWins();
                    eventBus.onRoundEnds(Side.PLAYER, Cause.BUST);
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
            eventBus.onRoundEnds(winner, Cause.HIGHER_SUM);
        }

        /**
         * Returns ordinal number of round.
         *
         * @return ordinal number of this round
         */
        public int ordinalNumber() {
            return score.left + score.right + 1;
        }

        /**
         * Shows player hand.
         *
         * @return player hand
         */
        public List<CardView> seePlayerHand() {
            return seeHand(playerHand);
        }

        /**
         * Shows dealer hand.
         *
         * @return dealer hand
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

    public static interface EventListener {
        /**
         * Called when round begins.
         *
         * @param round rouns object
         */
        void onRoundBegins(Game.Round round);

        /**
         * Called when round ends.
         *
         * @param winner winner
         * @param reason reason
         */
        void onRoundEnds(Side winner, Cause reason);

        /**
         * Called when someone pick a card.
         *
         * @param who  who picks
         * @param card picked card
         */
        void onPick(Side who, Card card);

        /**
         * Called when dealer turn begins.
         *
         * @param card card opened by dealer
         */
        void onDealerTurn(Card card);
    }

    /**
     * An object responsible for interaction with player.
     */
    public interface PlayerController {
        /**
         * Asks player about picking card.
         *
         * @return true, if player is going to pick one more card
         */
        boolean doPickCard();

        /**
         * Asks player about next round.
         *
         * @return true, if player is going to play one more round
         */
        boolean doPlayNextRound();
    }

    /**
     * Eiether player or dealer.
     */
    public static enum Side {
        PLAYER,
        DEALER;
    }

    /**
     * Cause of round ending.
     */
    public static enum Cause {
        BLACKJACK,
        BUST,
        HIGHER_SUM,
    }
}
