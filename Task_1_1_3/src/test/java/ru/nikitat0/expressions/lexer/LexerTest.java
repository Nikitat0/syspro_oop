package ru.nikitat0.expressions.lexer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LexerTest {
    private static final String SRC = "two words 0 23126 + - * / = ;";
    private static final Token[] TOKENS = new Token[] {
        new Token.Word("two"),
        new Token.Word("words"),
        new Token.Number(0),
        new Token.Number(23126),
        Token.PLUS,
        Token.MINUS,
        Token.MUL,
        Token.DIV,
        Token.EQUAL,
        Token.SEMICOLON,
        Token.END,
    };

    @Test
    void testEmptyString() {
        Assertions.assertEquals(Token.END, new Lexer("").next());
    }

    @Test
    void testNext() {
        Lexer lex = new Lexer(SRC);
        for (int i = 0; i < TOKENS.length; i++) {
            Assertions.assertEquals(TOKENS[i], lex.next());
        }
    }

    @Test
    void testInvalidCharacter() {
        Lexer lex = new Lexer("!#@");
        for (int i = 0; i < 3; i++) {
            Assertions.assertThrows(RuntimeException.class, () -> lex.next());
        }
        // Lexer should be able to recover
        Assertions.assertEquals(Token.END, lex.expect(Token.END));
    }

    @Test
    void testUnexpectedToken() {
        // Oops, someone uses Yoda notation out of place
        Lexer lex = new Lexer("123 = var");
        Assertions.assertThrows(RuntimeException.class, () -> lex.expect(Token.WORD));
        // Lexer should be able to recover
        Assertions.assertEquals(Token.EQUAL, lex.expect(Token.EQUAL));
        Assertions.assertThrows(RuntimeException.class, () -> lex.expect(Token.NUMBER));
        // Lexer should be able to recover
        Assertions.assertEquals(Token.END, lex.expect(Token.END));
    }

    @Test
    void testExpect() {
        Lexer lex = new Lexer(SRC);
        int i = 0;
        for (; i < 2; i++) {
            Assertions.assertEquals(TOKENS[i], lex.expect(Token.WORD));
        }
        for (; i < 4; i++) {
            Assertions.assertEquals(TOKENS[i], lex.expect(Token.NUMBER));
        }
        for (; i < TOKENS.length; i++) {
            Assertions.assertEquals(TOKENS[i], lex.next());
        }
    }
}
