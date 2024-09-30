package ru.nikitat0.expressions;

import java.util.Map;

/**
 * An multiplication expression.
 */
public final class Mul extends BinaryExpression {
    /**
     * Constructs multiplication expression.
     *
     * @param lhs left factor
     * @param rhs right factor
     */
    public Mul(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    protected String fmt() {
        return "(%s*%s)";
    }

    @Override
    public Expression derivative(String varName) {
        return new Add(
                new Mul(lhs.derivative(varName), rhs),
                new Mul(lhs, rhs.derivative(varName)));
    }

    @Override
    public Expression partialEval(Map<String, Integer> values) {
        Expression lhs = this.lhs.partialEval(values);
        Expression rhs = this.rhs.partialEval(values);
        if (lhs.equals(Number.ZERO) || rhs.equals(Number.ZERO)) {
            return Number.ZERO;
        }
        if (lhs.equals(Number.ONE)) {
            return rhs;
        }
        if (rhs.equals(Number.ONE)) {
            return lhs;
        }
        if (lhs instanceof Number && rhs instanceof Number) {
            return new Number(((Number) lhs).value * ((Number) rhs).value);
        }
        return new Mul(lhs, rhs);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Mul) {
            Mul otherMul = (Mul) o;
            return this.lhs.equals(otherMul.lhs) && this.rhs.equals(otherMul.rhs);
        }
        return false;
    }
}
