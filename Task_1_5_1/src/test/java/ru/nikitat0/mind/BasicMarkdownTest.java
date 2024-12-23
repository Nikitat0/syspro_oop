package ru.nikitat0.mind;

import static ru.nikitat0.mind.Common.EMPTY_ELEMENT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BasicMarkdownTest {
    @Test
    void testHeader() {
        Common.assertMd("# H1", new Header(new Text("H1")));
        Common.assertMd("#### H4", new Header(new Text("H4"), 4));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Header(EMPTY_ELEMENT, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Header(EMPTY_ELEMENT, 7));
    }

    @Test
    void testCodeBlock() {
        CodeBlock shellSession = new CodeBlock(Common.multiline(
                "$ echo Hello",
                "Hello",
                ""));
        Common.assertMd(Common.multiline(
                "```",
                "$ echo Hello",
                "Hello",
                "",
                "```"),
                shellSession);

        CodeBlock cHello = new CodeBlock(Common.multiline(
                "#include <stdio.h>",
                "",
                "int main() {",
                "  printf(\"Hello\")",
                "}"), "c");
        Common.assertMd(Common.multiline(
                "```c",
                "#include <stdio.h>",
                "",
                "int main() {",
                "  printf(\"Hello\")",
                "}",
                "```"), cHello);
    }
}
