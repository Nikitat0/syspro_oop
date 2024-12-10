package ru.nikitat0.mind;

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
}
