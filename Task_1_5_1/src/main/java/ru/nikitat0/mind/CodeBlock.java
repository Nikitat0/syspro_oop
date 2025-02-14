package ru.nikitat0.mind;

import java.util.Objects;

public class CodeBlock extends Element.Block {
    public final String code;
    public final String lang;

    public CodeBlock(String code) {
        this(code, "");
    }

    public CodeBlock(String code, String lang) {
        this.code = code;
        this.lang = lang.replace("\n", "");
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        CodeBlock otherBlock = (CodeBlock) other;
        return this.code.equals(otherBlock.code) && this.lang.equals(otherBlock.lang);
    }

    @Override
    public String toString() {
        return String.format("```%s\n%s\n```", lang, code);
    }
}
