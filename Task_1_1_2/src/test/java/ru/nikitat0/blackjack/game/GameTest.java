package ru.nikitat0.blackjack.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameTest {
    @Test
    void testScore() {
        Assertions.assertEquals(new Score(0, 0), new Score());
        Assertions.assertEquals(new Score(1, 0), new Score().incLeft());
        Assertions.assertEquals(new Score(0, 1), new Score().incRight());
        Score score = new Score(26, 62);
        Assertions.assertEquals(26, score.left);
        Assertions.assertEquals(62, score.right);
        Assertions.assertEquals("26:62", score.toString());
    }
}
