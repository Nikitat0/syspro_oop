package ru.nikitat0.mind;

import java.util.Objects;

public class Link extends Element.Inline {
    private final String path;
    private final Text text;
    private final String tooltip;

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }
        if (this.getClass() != otherObj.getClass()) {
            return false;
        }
        Link other = (Link) otherObj;
        return this.text.equals(other.text) && this.path.equals(other.path);
    }

    @Override
    public String toString() {
        String text = this.text == null ? path : this.text.toString();
        if (tooltip == null) {
            return String.format("[%s](%s)", text, path);
        } else {
            return String.format("[%s](%s \"%s\")", text, path, tooltip);
        }
    }

    private Link(String path, Text text, String tooltip) {
        this.text = text;
        this.path = path;
        this.tooltip = tooltip;
    }

    public static final class Builder {
        private final String path;
        private Text text = null;
        private String tooltip = null;

        public Builder(String path) {
            this.path = path;
        }

        public Builder setText(Text text) {
            this.text = text;
            return this;
        }

        public Builder setTooltip(String tooltip) {
            this.tooltip = tooltipEscaper.escape(tooltip);
            return this;
        }

        public Link build() {
            return new Link(path, text, tooltip);
        }
    }

    private static Escaper tooltipEscaper = new Escaper.Builder()
            .addEscaped('"')
            .addEscaped('\n', " ")
            .build();
}
