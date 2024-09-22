package ru.nikitat0.expressions;

import static ru.nikitat0.expressions.Expression.parse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExpressionsTest {
    static final Number ONE = new Number(1);
    static final Number TWO = new Number(2);
    static final Number THREE = new Number(3);
    static final Number FIVE = new Number(5);
    static final Number TEN = new Number(10);

    @Test
    void testExpressionParsing() {
        Assertions.assertEquals(new Variable("a"), parse("a"));
        Assertions.assertEquals(new Number(23126), parse("23126"));
        Assertions.assertEquals(new Add(ONE, TWO), parse("1+2"));
        Assertions.assertEquals(new Sub(ONE, TWO), parse("1-2"));
        Assertions.assertEquals(new Mul(ONE, TWO), parse("1*2"));
        Assertions.assertEquals(new Div(ONE, TWO), parse("1/2"));

        Assertions.assertEquals(new Add(ONE, new Mul(TWO, THREE)), parse("1+2*3"));
        Assertions.assertEquals(new Sub(new Add(ONE, new Mul(TWO, THREE)), FIVE), parse("1+2*3-5"));
        Assertions.assertEquals(new Mul(new Add(ONE, TWO), THREE), parse("(1+2)*3"));
        Assertions.assertEquals(new Div(new Mul(TWO, FIVE), TEN), parse("2*5/10"));

        Assertions.assertEquals(new Add(ONE, TWO), parse("(  (((1) + ((2)))))"));

        Assertions.assertEquals(parse("a+b"), parse("a+b"));
        Assertions.assertEquals(parse("2+(3*5)"), parse("2+3*5"));
    }

    @Test
    void testDerivative() {
        Assertions.assertEquals(parse("0"), parse("23126").derivative("x"));
        Assertions.assertEquals(parse("1"), parse("a").derivative("a"));
        Assertions.assertEquals(parse("0"), parse("a").derivative("b"));

        Assertions.assertEquals(parse("1+0"), parse("a+b").derivative("a"));
        Assertions.assertEquals(parse("0-1"), parse("a-b").derivative("b"));
        Assertions.assertEquals(parse("1*x+x*1"), parse("x*x").derivative("x"));
        Assertions.assertEquals(parse("(0*x-1*1)/(x*x)"), parse("1/x").derivative("x"));
    }

    @Test
    void testSimplify() {
        Assertions.assertEquals(parse("6"), parse("2+2*2").simplify());
        Assertions.assertEquals(parse("0"), parse("0*x").simplify());
        Assertions.assertEquals(parse("0"), parse("2*0").simplify());
        Assertions.assertEquals(parse("x"), parse("1*x").simplify());
        Assertions.assertEquals(parse("2"), parse("2*1").simplify());
        Assertions.assertEquals(parse("0"), parse("x*x-x*x").simplify());
    }

    @Test
    void testEval() {
        Assertions.assertEquals(1, parse("a").eval("a = 1"));
        Assertions.assertEquals(3, parse("a+b").eval("a = 1;b=2;"));

        Assertions.assertEquals(23, parse("3+2*x").eval("x = 10; y = 13"));
        Assertions.assertEquals(4, parse("a*a").eval("a = 2")); // Square area
        Assertions.assertEquals(282, parse("h*r*r*22/7").eval("h = 10; r = 3")); // Cylinder volume
    }
}
