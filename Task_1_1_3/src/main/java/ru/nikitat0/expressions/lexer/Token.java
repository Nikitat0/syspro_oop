package ru.nikitat0.expressions.lexer;

import java.util.Objects;

/** A token used in parsing. */
public abstract class Token {
    /** A constant holding unique '+' token. */
    public static final Token PLUS = new SingletonToken("<PLUS>");

    /** A constant holding unique '-' token. */
    public static final Token MINUS = new SingletonToken("<MINUS>");

    /** A constant holding unique '*' token. */
    public static final Token MUL = new SingletonToken("<MUL>");

    /** A constant holding unique '/' token. */
    public static final Token DIV = new SingletonToken("<DIV>");

    /** A constant holding unique '=' token. */
    public static final Token EQUAL = new SingletonToken("<EQUAL>");

    /** A constant holding unique ';' token. */
    public static final Token SEMICOLON = new SingletonToken("<SEMICOLON>");

    /** A constant holding unique '(' token. */
    public static final Token OPEN_PAREN = new SingletonToken("<OPEN_PAREN>");

    /** A constant holding unique ')' token. */
    public static final Token CLOSE_PAREN = new SingletonToken("<CLOSE_PAREN>");

    /**
     * A constant holding generic number token. It should be used only as argument
     * to {@link
     * ru.nikitat0.expressions.lexer.Lexer#expect(Token...)}.
     */
    public static final Token NUMBER = new SingletonToken("<NUMBER>") {
        @Override
        public boolean equals(Object o) {
            return o == this || o instanceof Number;
        }
    };

    /**
     * A constant holding generic word token. It should be used only as argument to
     * {@link
     * ru.nikitat0.expressions.lexer.Lexer#expect(Token...)}.
     */
    public static final Token WORD = new SingletonToken("<WORD>") {
        @Override
        public boolean equals(Object o) {
            return o == this || o instanceof Word;
        }
    };

    /** A constant holding end of input token. */
    public static final Token END = new SingletonToken("<END>");

    private static class SingletonToken extends Token {
        private final String repr;

        private SingletonToken(String repr) {
            this.repr = repr;
        }

        @Override
        public String toString() {
            return repr;
        }
    }

    /** Represents a token constisting of a single number. */
    public static class Number extends Token {

        /** The number value of this token. */
        public final int num;

        /**
         * Constucts a new number token.
         *
         * @param num the number for this token
         */
        public Number(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return String.valueOf(num);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Number) {
                return ((Number) o).num == this.num;
            }
            return o == NUMBER;
        }

        @Override
        public int hashCode() {
            return Objects.hash(num);
        }
    }

    /** Represents a token consisting of a single word. */
    public static class Word extends Token {

        /** The string value of this token. */
        public final String word;

        /**
         * Constucts a new word token.
         *
         * @param word the word for this token
         */
        public Word(String word) {
            this.word = word;
        }

        @Override
        public String toString() {
            return word;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Word) {
                return ((Word) o).word.equals(this.word);
            }
            return o == WORD;
        }

        @Override
        public int hashCode() {
            return Objects.hash(word);
        }
    }
}
