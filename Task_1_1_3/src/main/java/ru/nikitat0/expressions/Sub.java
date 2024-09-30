package ru.nikitat0.expressions;

import java.util.Map;

/**
 * An substraction expression.
 */
public final class Sub extends BinaryExpression {
    /**
     * Constructs substraction expression.
     *
     * @param lhs minued
     * @param rhs subtrahend
     */
    public Sub(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    protected String fmt() {
        return "(%s-%s)";
    }

    @Override
    public Expression derivative(String varName) {
        return new Sub(lhs.derivative(varName), rhs.derivative(varName));
    }

    @Override
    public Expression partialEval(Map<String, Integer> values) {
        Expression lhs = this.lhs.partialEval(values);
        Expression rhs = this.rhs.partialEval(values);
        if (lhs.equals(rhs)) {
            return Number.ZERO;
        }
        if (lhs instanceof Number && rhs instanceof Number) {
            return new Number(((Number) lhs).value - ((Number) rhs).value);
        }
        return new Add(lhs, rhs);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Sub) {
            Sub otherSub = (Sub) o;
            return this.lhs.equals(otherSub.lhs) && this.rhs.equals(otherSub.rhs);
        }
        return false;
    }
}
