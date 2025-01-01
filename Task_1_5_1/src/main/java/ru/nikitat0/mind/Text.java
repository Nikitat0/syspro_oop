package ru.nikitat0.mind;

import java.util.Objects;

public class Text extends Element.Inline {
    private static final Escaper ESCAPER = new Escaper.Builder()
            .addEscaped('*')
            .addEscaped('_')
            .addEscaped('~')
            .addEscaped('`')
            .addEscaped('[')
            .addEscaped(']')
            .addEscaped('\n', "<br/>")
            .build();

    private final String raw;

    public Text(CharSequence seq) {
        this.raw = ESCAPER.escape(seq);
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }
        if (this.getClass() != otherObj.getClass()) {
            return false;
        }
        Text other = (Text) otherObj;
        return this.raw.equals(other.raw);
    }

    @Override
    public String toString() {
        return raw;
    }

    public static class Italic extends Text {
        public Italic(CharSequence seq) {
            super(seq);
        }

        @Override
        public String toString() {
            return String.format("_%s_", super.toString());
        }
    }

    public static class Bold extends Text {
        public Bold(CharSequence seq) {
            super(seq);
        }

        @Override
        public String toString() {
            return String.format("**%s**", super.toString());
        }
    }

    public static class Strikethrough extends Text {
        public Strikethrough(CharSequence seq) {
            super(seq);
        }

        @Override
        public String toString() {
            return String.format("~~%s~~", super.toString());
        }
    }

    public static class Code extends Text {
        public Code(CharSequence seq) {
            super(seq);
        }

        @Override
        public String toString() {
            return String.format("`%s`", super.toString());
        }
    }
}
