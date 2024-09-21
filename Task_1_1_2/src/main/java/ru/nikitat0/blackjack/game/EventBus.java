package ru.nikitat0.blackjack.game;

import java.util.ArrayList;
import java.util.Collection;
import ru.nikitat0.blackjack.cards.Card;
import ru.nikitat0.blackjack.game.Game.Cause;
import ru.nikitat0.blackjack.game.Game.Round;
import ru.nikitat0.blackjack.game.Game.Side;

/**
 * A game event bus.
 */
public final class EventBus implements Game.EventListener {
    private final Collection<Game.EventListener> listeners = new ArrayList<>();

    /**
     * Subscribes listener on events.
     *
     * @param listener listener to subscribe
     */
    public void subscribe(Game.EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onRoundBegins(Round round) {
        for (Game.EventListener listener : listeners) {
            listener.onRoundBegins(round);
        }
    }

    @Override
    public void onRoundEnds(Side winner, Cause reason) {
        for (Game.EventListener listener : listeners) {
            listener.onRoundEnds(winner, reason);
        }
    }

    @Override
    public void onPick(Side who, Card card) {
        for (Game.EventListener listener : listeners) {
            listener.onPick(who, card);
        }
    }

    @Override
    public void onDealerTurn(Card card) {
        for (Game.EventListener listener : listeners) {
            listener.onDealerTurn(card);
        }
    }
}
