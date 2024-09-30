package ru.nikitat0.expressions;

import java.util.Map;

/** An addition expression. */
public final class Add extends BinaryExpression {
    /**
     * Constructs addition expression.
     *
     * @param lhs left operand
     * @param rhs right operand
     */
    public Add(Expression lhs, Expression rhs) {
        super(lhs, rhs);
    }

    @Override
    protected String fmt() {
        return "(%s+%s)";
    }

    @Override
    public Expression derivative(String varName) {
        return new Add(lhs.derivative(varName), rhs.derivative(varName));
    }

    @Override
    public Expression partialEval(Map<String, Integer> values) {
        Expression lhs = this.lhs.partialEval(values);
        Expression rhs = this.rhs.partialEval(values);
        if (lhs instanceof Number && rhs instanceof Number) {
            return new Number(((Number) lhs).value + ((Number) rhs).value);
        }
        return new Add(lhs, rhs);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Add) {
            Add otherAdd = (Add) o;
            return this.lhs.equals(otherAdd.lhs) && this.rhs.equals(otherAdd.rhs);
        }
        return false;
    }
}
