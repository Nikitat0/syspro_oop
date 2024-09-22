package ru.nikitat0.expressions;

import java.util.Map;

/**
 * An division expression.
 */
public final class Div extends BinaryExpression {
    /**
     * Constructs division expression.
     *
     * @param lhs dividend
     * @param rhs divisor
     */
    public Div(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    protected String fmt() {
        return "(%s/%s)";
    }

    @Override
    public Expression derivative(String varName) {
        return new Div(
                new Sub(
                        new Mul(lhs.derivative(varName), rhs),
                        new Mul(lhs, rhs.derivative(varName))),
                new Mul(rhs, rhs));
    }

    @Override
    public Expression partialEval(Map<String, Integer> values) {
        Expression lhs = this.lhs.partialEval(values);
        Expression rhs = this.rhs.partialEval(values);
        if (rhs.equals(Number.ONE)) {
            return lhs;
        }
        if (lhs instanceof Number && rhs instanceof Number) {
            return new Number(((Number) lhs).value / ((Number) rhs).value);
        }
        return new Add(lhs, rhs);
    }
}
