package ru.nikitat0.expressions;

import static ru.nikitat0.expressions.lexer.Token.CLOSE_PAREN;
import static ru.nikitat0.expressions.lexer.Token.DIV;
import static ru.nikitat0.expressions.lexer.Token.END;
import static ru.nikitat0.expressions.lexer.Token.MINUS;
import static ru.nikitat0.expressions.lexer.Token.MUL;
import static ru.nikitat0.expressions.lexer.Token.NUMBER;
import static ru.nikitat0.expressions.lexer.Token.OPEN_PAREN;
import static ru.nikitat0.expressions.lexer.Token.PLUS;
import static ru.nikitat0.expressions.lexer.Token.WORD;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import ru.nikitat0.expressions.lexer.Lexer;
import ru.nikitat0.expressions.lexer.Token;

/**
 * A mathematical expression.
 */
public abstract class Expression {
    protected static Map<String, Integer> parseAssigments(String src) {
        Lexer lex = new Lexer(src);
        Map<String, Integer> values = new HashMap<>();
        Token varName;
        while ((varName = lex.expect(Token.WORD, END)) != END) {
            lex.expect(Token.EQUAL);
            int value = 1;
            Token tok = lex.expect(NUMBER, PLUS, MINUS);
            if (!(tok instanceof Token.Number)) {
                if (tok == MINUS) {
                    value = -1;
                }
                tok = lex.expect(NUMBER);
            }
            value *= ((Token.Number) tok).num;
            values.put(((Token.Word) varName).word, value);
            lex.expect(Token.SEMICOLON, END);
        }
        return values;
    }

    /**
     * Parses expression from string.
     *
     * @param src string to parse from
     * @return parsed expression
     */
    public static Expression parse(String src) {
        Lexer lex = new Lexer(src);
        return parse(lex, END);
    }

    private static Expression parse(Lexer lex, Token end) {
        ArithmeticalParser parser = new ArithmeticalParser();
        parser.pushOperand(parseOperand(lex));
        Token token;
        while ((token = lex.expect(PLUS, MINUS, MUL, DIV, end)) != end) {
            parser.pushOperation(token);
            parser.pushOperand(parseOperand(lex));
        }
        return parser.output();
    }

    private static Expression parseOperand(Lexer lex) {
        Token token = lex.expect(OPEN_PAREN, NUMBER, WORD);
        if (token instanceof Token.Number) {
            return new Number(((Token.Number) token).num);
        } else if (token instanceof Token.Word) {
            return new Variable(((Token.Word) token).word);
        } else {
            return parse(lex, CLOSE_PAREN);
        }
    }

    /**
     * Fully evaluates expression.
     *
     * @param values semicolon-separated list of variable assignments
     * @return result of evaluation
     */
    public int eval(String values) {
        return eval(parseAssigments(values));
    }

    /**
     * Fully evaluates expression.
     *
     * @param values variable->value mapping
     * @return result of evaluation
     */
    public int eval(Map<String, Integer> values) {
        Expression evaluated = partialEval(values);
        if (evaluated instanceof Number) {
            return ((Number) evaluated).value;
        } else {
            String msg = String.format("expression \"%s\" cannot be further evaluated");
            throw new ArithmeticException(msg);
        }
    }

    /**
     * Partial evaluates expression.
     *
     * @param values semicolon-separated list of variable assignments
     * @return result of evaluation
     */
    public Expression partialEval(String values) {
        return partialEval(parseAssigments(values));
    }

    /**
     * Partially evaluates expression.
     *
     * @param values variable->value mapping
     * @return result of evaluation
     */
    public Expression partialEval(Map<String, Integer> values) {
        return this;
    }

    /**
     * Take derivative of this expression with respect to given variable.
     *
     * @param varName name of given variable
     * @return derivative of this expression
     */
    public abstract Expression derivative(String varName);

    /**
     * Print this expression to standart output.
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * Simplifies expression.
     *
     * @return simplified expression
     */
    public Expression simplify() {
        return partialEval(Collections.emptyMap());
    }
}

final class ArithmeticalParser {
    private final Deque<Expression> operands = new ArrayDeque<>();
    private final Deque<Token> operations = new ArrayDeque<>();

    private static int priority(Token token) {
        if (token == Token.PLUS || token == Token.MINUS) {
            return 1;
        } else if (token == Token.MUL || token == Token.DIV) {
            return 2;
        } else {
            return 0;
        }
    }

    private static Expression binaryOperation(Token op, Expression lhs, Expression rhs) {
        if (op == Token.PLUS) {
            return new Add(lhs, rhs);
        } else if (op == Token.MINUS) {
            return new Sub(lhs, rhs);
        } else if (op == Token.MUL) {
            return new Mul(lhs, rhs);
        } else if (op == Token.DIV) {
            return new Div(lhs, rhs);
        } else {
            String msg = String.format("token %s doesn't represent a binary operation", op);
            throw new RuntimeException(msg);
        }
    }

    private void apply(Token op) {
        Expression rhs = operands.pop();
        Expression lhs = operands.pop();
        operands.push(binaryOperation(op, lhs, rhs));
    }

    void pushOperand(Expression operand) {
        operands.push(operand);
    }

    void pushOperation(Token op) {
        while (!operations.isEmpty() && priority(operations.peek()) >= priority(op)) {
            apply(operations.pop());
        }
        operations.push(op);
    }

    Expression output() {
        while (!operations.isEmpty()) {
            apply(operations.pop());
        }
        return operands.pop();
    }
}
