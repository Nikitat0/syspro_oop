package ru.nikitat0.blackjack.game;

import java.util.Objects;

/**
 * A score in a game.
 */
public final class Score {
    /**
     * Left counter.
     */
    public final int left;

    /**
     * Right counter.
     */
    public final int right;

    /**
     * Constructs 0:0 score.
     */
    public Score() {
        this.left = 0;
        this.right = 0;
    }

    /**
     * Constructs left:right score.
     *
     * @param left  left
     * @param right right
     */
    public Score(int left, int right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Adds score point to the left side.
     *
     * @return new score
     */
    public Score incLeft() {
        return new Score(left + 1, right);
    }

    /**
     * Adds score point to the right side.
     *
     * @return new score
     */
    public Score incRight() {
        return new Score(left, right + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Score) {
            Score other = (Score) o;
            return this.left == other.left && this.right == other.right;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return String.format("%d:%d", left, right);
    }
}
