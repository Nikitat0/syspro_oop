package ru.nikitat0.mind;

import org.junit.jupiter.api.Test;

class ElementTest {
    @Test
    void testSequenceEquals() {
        Common.assertIdentity(
                new Text("1").join("2").join("3"),
                new Text("1").join(new Text("2").join(new Text("3"))));
        Common.assertIdentity(
                new Text("1").then("2").then(new Text("3").toParagraph()),
                new Text("1").then(new Text("2").then(new Text("3").toParagraph())));
    }
}
