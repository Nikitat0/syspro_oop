package ru.nikitat0.mind;

public class Paragraph extends Element.Block {
    private static final char[] ESCAPED_AT_THE_BEGINNING = new char[] {
            '#',
            '-',
            '+',
            '*',
            '>',
            '|',
    };

    public final Element.Inline content;

    public Paragraph(Element.Inline content) {
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
        Paragraph other = (Paragraph) otherObj;
        return this.content.equals(other.content);
    }

    @Override
    public String toString() {
        return content.toString().replaceFirst("^[#-+*>|]", "\\\\$0");
    }
}
