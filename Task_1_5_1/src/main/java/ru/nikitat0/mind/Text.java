package ru.nikitat0.mind;

public class Text extends Element.Inline {
    private final String raw;

    static void noNewline(CharSequence seq) {
        seq.chars().forEach((int c) -> {
            if (c == '\n') {
                throw new IllegalArgumentException("newline is not allowed");
            }
        });
    }

    private static String mdEscape(CharSequence seq) {
        return seq.toString();
    }

    public Text(CharSequence seq) {
        noNewline(seq);
        this.raw = mdEscape(seq);
    }

    @Override
    public boolean equals(Object other) {
        return this.getClass() == other.getClass() && this.raw.equals(((Text) other).raw);
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
