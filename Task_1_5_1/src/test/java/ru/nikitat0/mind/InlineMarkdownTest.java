package ru.nikitat0.mind;

import org.junit.jupiter.api.Test;

class InlineMarkdownTest {
    @Test
    void testText() {
        Common.assertMd("normal", new Text("normal"));
        Common.assertMd("_italic_", new Text.Italic("italic"));
        Common.assertMd("**bold**", new Text.Bold("bold"));
        Common.assertMd("~~strikethrough~~", new Text.Strikethrough("strikethrough"));
        Common.assertMd("`code`", new Text.Code("code"));

        Common.assertMd("**bold**_italic_ and text",
                new Text.Bold("bold")
                        .join(new Text.Italic("italic"))
                        .join(new Text(" and text")));

        Common.assertMd("\\*\\_\\~\\`\\[\\]", new Text("*_~`[]"));
        Common.assertMd("<br/>", new Text("\n"));
    }

    @Test
    void testLink() {
        Link.Builder builder = new Link.Builder("https://sys.pro");
        Common.assertMd("[https://sys.pro](https://sys.pro)", builder.build());
        builder.setText(new Text("Sys Pro"));
        Common.assertMd("[Sys Pro](https://sys.pro)", builder.build());
        builder.setTooltip("Sys Pro");
        Common.assertMd("[Sys Pro](https://sys.pro \"Sys Pro\")", builder.build());
    }

    @Test
    void testImage() {
        Image.Builder builder = new Image.Builder("https://sys.pro/assets/logo.jpg");
        Common.assertMd("![](https://sys.pro/assets/logo.jpg)", builder.build());
        builder.setDescription(new Text("SysPro logo"));
        Common.assertMd("![SysPro logo](https://sys.pro/assets/logo.jpg)", builder.build());
        builder.setTooltip(">_ sys.pro mmf.nsu");
        Common.assertMd("![SysPro logo](https://sys.pro/assets/logo.jpg \">_ sys.pro mmf.nsu\")",
                builder.build());
    }

    @Test
    void testParagraph() {
        Common.assertMd("\\#", new Text("#").toParagraph());
    }
}
