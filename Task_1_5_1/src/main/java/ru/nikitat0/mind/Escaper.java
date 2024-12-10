package ru.nikitat0.mind;

final class Escaper {
    private static final char ESCAPE_CHAR = '\\';

    private final boolean[] doEscape;

    public String escape(CharSequence seq) {
        StringBuilder builder = new StringBuilder();
        seq.chars().forEach((int c) -> {
            if (c >= doEscape.length && doEscape[c]) {
                builder.append(ESCAPE_CHAR);
            }
            builder.appendCodePoint(c);
        });
        return builder.toString();
    }

    private Escaper(boolean[] doEscape) {
        this.doEscape = doEscape;
    }

    public static final class Builder {
        private final boolean[] doEscape;

        public Builder() {
            this.doEscape = new boolean[128];
            addEscaped(ESCAPE_CHAR);
        }

        public Builder addEscaped(char c) {
            if (c >= 128) {
                throw new UnsupportedOperationException();
            }
            doEscape[c] = true;
            return this;
        }

        public Escaper build() {
            return new Escaper(doEscape);
        }
    }
}
