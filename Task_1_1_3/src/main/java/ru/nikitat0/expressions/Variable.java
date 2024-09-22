package ru.nikitat0.expressions;

import java.util.Map;

/**
 * An expression consisting of single variable.
 */
public final class Variable extends Expression {
    /**
     * The name of variable.
     */
    public final String name;

    /**
     * Constructs a expression consisting of single variable.
     *
     * @param name name of variable
     */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Expression derivative(String varName) {
        return varName.equals(this.name) ? Number.ONE : Number.ZERO;
    }

    @Override
    public Expression partialEval(Map<String, Integer> values) {
        if (values.containsKey(name)) {
            return new Number(values.get(name));
        }
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Variable) {
            return ((Variable) o).name.equals(this.name);
        }
        return false;
    }
}
