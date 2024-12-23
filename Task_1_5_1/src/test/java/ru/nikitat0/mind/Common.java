package ru.nikitat0.mind;

import java.util.StringJoiner;
import org.junit.jupiter.api.Assertions;

class Common {
    static final Element.Inline EMPTY_ELEMENT = new Text("");
    static final Element.Inline DUMMY_ELEMENT = new Text("Dummy");

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

    static void assertIdentity(Object expected, Object actual) {
        Assertions.assertTrue(expected.hashCode() == actual.hashCode());
        Assertions.assertEquals(expected, actual);
    }
}
