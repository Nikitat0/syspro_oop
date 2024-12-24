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

    @Test
    void testQuote() {
        Quote quote = new Quote(new Header(new Text("some header"))
                .then("some text")
                .then("some text 2"));
        Common.assertMd(Common.multiline(
                "> # some header",
                "> ",
                "> some text",
                "> ",
                "> some text 2"), quote);
    }

    @Test
    void testList() {
        List ordered = new List.Builder(List.Style.Ordered)
                .addItem(new Text("first"))
                .addItem(new Text("second"))
                .addItem(new Text("third"))
                .build();
        Common.assertMd(Common.multiline(
                "1. first",
                "2. second",
                "3. third"), ordered);

        List unordered = new List.Builder(List.Style.Unordered)
                .addItem(new Text("non-empty, next is empty"))
                .addItem(null)
                .addItem(new Text("nested"),
                        new Text("here is nested list")
                                .then(new List.Builder(List.Style.Unordered)
                                        .addItem(new Text("nested"))
                                        .build()))
                .build();
        Common.assertMd(Common.multiline(
                "- non-empty, next is empty",
                "-",
                "- nested",
                "    here is nested list",
                "    ",
                "    - nested"), unordered);

        List tasks = new List.TaskListBuilder()
                .addItem(new Text("complete"), true)
                .addItem(new Text("incomplete"), false)
                .build();
        Common.assertMd(Common.multiline(
                "- [x] complete",
                "- [ ] incomplete"), tasks);
    }
}
