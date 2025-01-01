package ru.nikitat0.mind;

import java.util.Objects;

public class Header extends Element.Block {
    private final Element.Inline content;
    private final int level;

    public Header(Element.Inline content) {
        this(content, 1);
    }

    public Header(Element.Inline content, int level) {
        if (6 < level || level < 1) {
            throw new IllegalArgumentException("header level must be range 1-6");
        }
        this.content = content;
        this.level = level;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }
        if (!(otherObj instanceof Header)) {
            return false;
        }
        Header other = (Header) otherObj;
        return this.content.equals(other.content) && this.level == other.level;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append('#');
        }
        builder.append(' ');
        builder.append(content);
        return builder.toString();
    }
}
