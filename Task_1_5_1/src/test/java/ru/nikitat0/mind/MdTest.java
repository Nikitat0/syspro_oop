package ru.nikitat0.mind;

import java.util.StringJoiner;
import org.junit.jupiter.api.Assertions;

class MdTest {
    static String multiline(String... lines) {
        StringJoiner joiner = new StringJoiner("\n");
        for (String line : lines) {
            joiner.add(line);
        }
        return joiner.toString();
    }

    static void assertMd(String expected, Object actual) {
        Assertions.assertEquals(expected, actual.toString());
    }
}
