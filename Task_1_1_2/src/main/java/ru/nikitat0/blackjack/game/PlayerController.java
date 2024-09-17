package ru.nikitat0.blackjack.game;

import ru.nikitat0.blackjack.cards.Card;

/**
 * An object responsible for interaction with player.
 */
public interface PlayerController {
    /**
     * @return true, if player is going to pick one more card
     */
    boolean doPickCard();

    /**
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
     * @param winner
     * @param reason
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
     * Why someone winned?
     */
    public static enum Cause {
        BLACKJACK,
        BUST,
        HIGHER_SUM,
    }
}
