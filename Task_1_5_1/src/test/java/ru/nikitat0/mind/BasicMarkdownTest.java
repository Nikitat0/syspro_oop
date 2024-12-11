package ru.nikitat0.mind;

import static ru.nikitat0.mind.MdTest.EMPTY_ELEMENT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BasicMarkdownTest {
    @Test
    void testText() {
        MdTest.assertMd("normal", new Text("normal"));
        MdTest.assertMd("_italic_", new Text.Italic("italic"));
        MdTest.assertMd("**bold**", new Text.Bold("bold"));
        MdTest.assertMd("~~strikethrough~~", new Text.Strikethrough("strikethrough"));
        MdTest.assertMd("`code`", new Text.Code("code"));

        MdTest.assertMd("**bold**_italic_ and text",
                new Text.Bold("bold")
                        .join(new Text.Italic("italic"))
                        .join(new Text(" and text")));
    }

    @Test
    void testLink() {
        Link.Builder builder = new Link.Builder("https://sys.pro");
        MdTest.assertMd("[https://sys.pro](https://sys.pro)", builder.build());
        builder.setText(new Text("Sys Pro"));
        MdTest.assertMd("[Sys Pro](https://sys.pro)", builder.build());
        builder.setTooltip("Sys Pro");
        MdTest.assertMd("[Sys Pro](https://sys.pro \"Sys Pro\")", builder.build());
    }

    @Test
    void testImage() {
        Image.Builder builder = new Image.Builder("https://sys.pro/assets/logo.jpg");
        MdTest.assertMd("![](https://sys.pro/assets/logo.jpg)", builder.build());
        builder.setDescription(new Text("SysPro logo"));
        MdTest.assertMd("![SysPro logo](https://sys.pro/assets/logo.jpg)", builder.build());
        builder.setTooltip(">_ sys.pro mmf.nsu");
        MdTest.assertMd("![SysPro logo](https://sys.pro/assets/logo.jpg \">_ sys.pro mmf.nsu\")",
                builder.build());
    }

    @Test
    void testHeader() {
        MdTest.assertMd("# H1", new Header(new Text("H1")));
        MdTest.assertMd("#### H4", new Header(new Text("H4"), 4));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Header(EMPTY_ELEMENT, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Header(EMPTY_ELEMENT, 7));
    }

    @Test
    void testCodeBlock() {
        CodeBlock shellSession = new CodeBlock(MdTest.multiline(
                "$ echo Hello",
                "Hello",
                ""));
        MdTest.assertMd(MdTest.multiline(
                "```",
                "$ echo Hello",
                "Hello",
                "",
                "```"),
                shellSession);

        CodeBlock cHello = new CodeBlock(MdTest.multiline(
                "#include <stdio.h>",
                "",
                "int main() {",
                "  printf(\"Hello\")",
                "}"), "c");
        MdTest.assertMd(MdTest.multiline(
                "```c",
                "#include <stdio.h>",
                "",
                "int main() {",
                "  printf(\"Hello\")",
                "}",
                "```"), cHello);
    }
}
