package ru.nikitat0.expressions.lexer;

import java.util.Arrays;
import java.util.Objects;

/**
 * An lexer used in parsing expressions and variable assignments.
 */
public final class Lexer {
    private String src;
    private int pos = 0;
    private int latestTokenPos = 0;

    /**
     * Constructs a lexer for the given source string.
     *
     * @param src the string to tokenize
     */
    public Lexer(String src) {
        this.src = src;
    }

    /**
     * Returns the next token from source string.
     *
     * @return the next token
     */
    public Token next() {
        char c;
        while (pos < src.length() && Character.isSpaceChar(src.charAt(pos))) {
            pos++;
        }
        latestTokenPos = pos;
        if (pos == src.length()) {
            return Token.END;
        }
        c = src.charAt(pos);
        if (Character.isDigit(c)) {
            return new Token.Number(readNumber());
        }
        if (Character.isLetter(c)) {
            return new Token.Word(readWord());
        }
        pos++;
        switch (c) {
            case '+':
                return Token.PLUS;
            case '-':
                return Token.MINUS;
            case '*':
                return Token.MUL;
            case '/':
                return Token.DIV;
            case '=':
                return Token.EQUAL;
            case ';':
                return Token.SEMICOLON;
            case '(':
                return Token.OPEN_PAREN;
            case ')':
                return Token.CLOSE_PAREN;
            default:
                throw unexpectedCharacterException(c, latestTokenPos);
        }
    }

    private String readWord() {
        int i = pos;
        while (pos < src.length() && Character.isLetter(src.charAt(pos))) {
            pos++;
        }
        return src.substring(i, pos);
    }

    private int readNumber() {
        int i = pos;
        while (pos < src.length() && Character.isDigit(src.charAt(pos))) {
            pos++;
        }
        return Integer.parseInt(src.substring(i, pos));
    }

    /**
     * Returns begging index of latest token.
     *
     * @return begging index of latest token
     */
    public int getLatestTokenPosition() {
        return latestTokenPos;
    }

    /**
     * Returns the next token from source string and asserts that it matches one of
     * expected tokens.
     *
     * @param tokens expected tokens
     * @return the next token
     * @throws RuntimeException if the next token does not match any of expected
     *                          tokens
     */
    public Token expect(Token... tokens) {
        if (tokens.length == 0) {
            throw new IllegalArgumentException("at least one token should be provided");
        }
        Token actual = next();
        for (Token expected : tokens) {
            if (Objects.equals(expected, actual)) {
                return actual;
            }
        }
        throw unexpectedTokenException(actual, tokens, getLatestTokenPosition());
    }

    private static RuntimeException unexpectedCharacterException(char c, int pos) {
        return new RuntimeException(String.format("unexpected char '%c' at pos %d", c, pos));
    }

    private static RuntimeException unexpectedTokenException(Token actual, Token[] expected, int pos) {
        boolean single = expected.length == 1;
        String msg = String.format(
                "Expected token%s %s; actual: %s at pos %d",
                single ? "" : "s",
                single ? expected[0] : Arrays.toString(expected),
                actual,
                pos);
        return new RuntimeException(msg);
    }
}
