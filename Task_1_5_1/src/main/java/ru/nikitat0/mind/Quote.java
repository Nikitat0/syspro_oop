package ru.nikitat0.mind;

public class Quote extends Element.Block {
    private final Element.Block content;

    public Quote(Element.Inline content) {
        this(content.toParagraph());
    }

    public Quote(Element.Block content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }
        if (this.getClass() != otherObj.getClass()) {
            return false;
        }
        Quote other = (Quote) otherObj;
        return this.content.equals(other.content);
    }

    @Override
    public String toString() {
        return content.toString().replaceAll("(?m)^", "> ");
    }
}
