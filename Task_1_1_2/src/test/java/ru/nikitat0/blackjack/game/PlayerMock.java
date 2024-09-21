package ru.nikitat0.blackjack.game;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.jupiter.api.Assertions;
import ru.nikitat0.blackjack.cards.Card;
import ru.nikitat0.blackjack.game.Game.Cause;
import ru.nikitat0.blackjack.game.Game.EventListener;
import ru.nikitat0.blackjack.game.Game.PlayerController;
import ru.nikitat0.blackjack.game.Game.Round;
import ru.nikitat0.blackjack.game.Game.Side;

class PlayerMock implements PlayerController, EventListener {
    static final Step PICK_CARD = new Step() {
        @Override
        public boolean doPickCard() {
            return true;
        }
    };

    static final Step DO_NOT_PICK_CARD = new Step() {
        @Override
        public boolean doPickCard() {
            return false;
        }
    };

    static final Step CONTINUE = new Step() {
        @Override
        public boolean doPlayNextRound() {
            return true;
        }
    };

    static final Step DO_NOT_CONTINUE = new Step() {
        @Override
        public boolean doPlayNextRound() {
            return false;
        }
    };

    Deque<Step> steps = new ArrayDeque<>();

    PlayerMock addStep(Step step) {
        steps.add(step);
        return this;
    }

    private Step nextStep() {
        return steps.isEmpty() ? DO_NOT_CONTINUE : steps.remove();
    }

    void assertFinished() {
        Assertions.assertTrue(steps.isEmpty());
    }

    @Override
    public void onRoundBegins(Round round) {
        nextStep().onRoundBegins(round);
    }

    @Override
    public void onRoundEnds(Side winner, Cause reason) {
        nextStep().onRoundEnds(winner, reason);
    }

    @Override
    public void onPick(Side who, Card card) {
        nextStep().onPick(who, card);
    }

    @Override
    public void onDealerTurn(Card card) {
        nextStep().onDealerTurn(card);
    }

    @Override
    public boolean doPickCard() {
        return nextStep().doPickCard();
    }

    @Override
    public boolean doPlayNextRound() {
        return nextStep().doPlayNextRound();
    }

    static class Step implements PlayerController, EventListener {
        @Override
        public void onRoundBegins(Round round) {
            Assertions.fail("unexpected RoundBegin event");
        }

        @Override
        public void onRoundEnds(Side winner, Cause reason) {
            Assertions.fail("unexpected RoundEnds event");
        }

        @Override
        public void onPick(Side who, Card card) {
            Assertions.fail("unexpected CardPick event");
        }

        @Override
        public void onDealerTurn(Card card) {
            Assertions.fail("unexpected DealerTurn event");
        }

        @Override
        public boolean doPickCard() {
            Assertions.fail("unexpected doPickCard request");
            return false;
        }

        @Override
        public boolean doPlayNextRound() {
            Assertions.fail("unexpected doPlayNextRound request");
            return false;
        }

    }
}
