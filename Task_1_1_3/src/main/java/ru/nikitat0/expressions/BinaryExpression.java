package ru.nikitat0.expressions;

import java.util.Objects;

/**
 * A binary expression.
 */
public abstract class BinaryExpression extends Expression {
    /**
     * The left hand side operand.
     */
    public final Expression lhs;

    /**
     * The right hand side operand.
     */
    public final Expression rhs;

    /**
     * Constructs new binary expression.
     *
     * @param lhs left operand
     * @param rhs right operand
     */
    public BinaryExpression(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    protected abstract String fmt();

    @Override
    public final String toString() {
        return String.format(fmt(), lhs.toString(), rhs.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fmt(), lhs, rhs);
    }
}
