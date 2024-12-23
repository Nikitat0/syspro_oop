package ru.nikitat0.mind;

import java.util.Objects;

public class Quote extends Element.Block {
    private final Element.Block content;

    public Quote(Element.Inline content) {
        this(content.toParagraph());
    }

    public Quote(Element.Block content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object other) {
        if (this.getClass() != other.getClass()) {
            return false;
        }
        return this.content.equals(((Quote) other).content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), content);
    }

    @Override
    public String toString() {
        return content.toString().replaceAll("^", "> ");
    }
}
