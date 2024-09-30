package ru.nikitat0.expressions;

import java.util.Objects;

/**
 * An expression consisting of a single number.
 */
public final class Number extends Expression {
    /**
     * A constant holding zero as expression.
     */
    public static final Expression ZERO = new Number(0);

    /**
     * A constant holding one as expression.
     */
    public static final Expression ONE = new Number(1);

    /**
     * The value of expression.
     */
    public final int value;

    /**
     * Constructs a number expression.
     *
     * @param value number value
     */
    public Number(int value) {
        this.value = value;
    }

    @Override
    public Expression derivative(String varName) {
        return ZERO;
    }

    @Override
    public String toString() {
        return value < 0 ? String.format("(%d)", value) : String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Number) {
            return ((Number) o).value == this.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
