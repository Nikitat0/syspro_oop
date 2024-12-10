package ru.nikitat0.mind;

import java.util.Objects;

public class CodeBlock {
    public final String code;
    public final String lang;

    public CodeBlock(String code) {
        this(code, "");
    }

    public CodeBlock(String code, String lang) {
        Text.noNewline(lang);
        this.code = code;
        this.lang = lang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), code, lang);
    }

    @Override
    public boolean equals(Object other) {
        if (this.getClass() != other.getClass())
            return false;
        CodeBlock otherBlock = (CodeBlock) other;
        return this.code.equals(otherBlock.code) && this.lang.equals(otherBlock.lang);
    }

    @Override
    public String toString() {
        return String.format("```%s\n%s\n```", lang, code);
    }
}
