package ru.nikitat0.mind;

import java.util.Arrays;

final class Escaper {
    private static final char ESCAPE_CHAR = '\\';

    private final String[] escapeSequence;

    public String escape(CharSequence seq) {
        StringBuilder builder = new StringBuilder();
        seq.chars().forEach((int c) -> {
            if (c < escapeSequence.length && escapeSequence[c] != null) {
                builder.append(escapeSequence[c]);
            } else {
                builder.appendCodePoint(c);
            }
        });
        return builder.toString();
    }

    private Escaper(String[] escapeSequence) {
        this.escapeSequence = escapeSequence;
    }

    static final class Builder {
        private final String[] escapeSequence;

        Builder() {
            this.escapeSequence = new String[128];
            addEscaped(ESCAPE_CHAR);
        }

        Builder addEscaped(int codePoint) {
            return addEscaped(codePoint, new StringBuilder()
                    .append(ESCAPE_CHAR)
                    .appendCodePoint(codePoint)
                    .toString());
        }

        Builder addEscaped(int codePoint, String escapeSequence) {
            if (codePoint >= 128) {
                throw new UnsupportedOperationException();
            }
            this.escapeSequence[codePoint] = escapeSequence;
            return this;
        }

        Escaper build() {
            return new Escaper(Arrays.copyOf(escapeSequence, escapeSequence.length));
        }
    }
}
