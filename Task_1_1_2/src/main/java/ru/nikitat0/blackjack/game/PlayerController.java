package ru.nikitat0.blackjack.game;

import ru.nikitat0.blackjack.cards.Card;

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
