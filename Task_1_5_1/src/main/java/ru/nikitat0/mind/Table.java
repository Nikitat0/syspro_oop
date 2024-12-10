package ru.nikitat0.mind;

import java.util.ArrayList;

public class Table {
    private final InlineElement[] headers;
    private final Alignment[] alignment;
    private final InlineElement[][] rows;

    private int getHeight() {
        return rows.length;
    }

    private int getWidth() {
        return headers.length;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Table)) {
            return false;
        }
        Table otherTable = (Table) other;
        if (this.getWidth() != otherTable.getWidth()) {
            return false;
        }
        if (this.getHeight() != otherTable.getHeight()) {
            return false;
        }
        for (int i = 0; i < this.getWidth(); i++) {
            if (!this.headers[i].equals(otherTable.headers[i])) {
                return false;
            }
            if (this.alignment[i] != otherTable.alignment[i]) {
                return false;
            }
        }
        for (int j = 0; j < this.getHeight(); j++) {
            for (int i = 0; i < this.getWidth(); i++) {
                if (this.rows[j][i].equals(otherTable.rows[j][i])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        ColumnsWidths widths = new ColumnsWidths(getWidth()).updateAll(3);

        String[] renderedHeaders = new String[getWidth()];
        for (int i = 0; i < getWidth(); i++) {
            renderedHeaders[i] = headers[i].toString();
            widths.update(i, renderedHeaders[i].length());
        }

        String[][] renderedRows = new String[getHeight()][];
        for (int j = 0; j < getHeight(); j++) {
            renderedRows[j] = new String[getWidth()];
            for (int i = 0; i < getWidth(); i++) {
                renderedRows[j][i] = rows[j][i].toString();
                widths.update(i, renderedRows[j][i].length());
            }
        }

        String[] delimeterRows = new String[getWidth()];
        for (int i = 0; i < getWidth(); i++) {
            delimeterRows[i] = alignment[i].getDelimeterRow(widths.get(i));
        }

        String[][] rows = new String[getHeight() + 2][];
        rows[0] = renderedHeaders;
        rows[1] = delimeterRows;
        System.arraycopy(renderedRows, 0, rows, 2, getHeight());

        StringBuilder builder = new StringBuilder();
        for (String[] row : rows) {
            for (int i = 0; i < getWidth(); i++) {
                if (i != 0) {
                    builder.append(' ');
                }
                builder.append("| ");

                int totalPadding = widths.get(i) - row[i].length();
                for (int c = 0; c < alignment[i].getLeftPadding(totalPadding); c++) {
                    builder.append(' ');
                }
                builder.append(row[i]);
                for (int c = 0; c < alignment[i].getRightPadding(totalPadding); c++) {
                    builder.append(' ');
                }
            }
            builder.append(" |\n");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private Table(InlineElement[] headers, Alignment[] alignment, InlineElement[][] rows) {
        this.headers = headers;
        this.alignment = alignment;
        this.rows = rows;
    }

    public static final class Builder {
        private final InlineElement[] headers;
        private Alignment[] alignment;
        private ArrayList<InlineElement[]> rows;

        private int getRowCount() {
            return rows.size();
        }

        private int getColumnCount() {
            return headers.length;
        }

        public Builder(InlineElement... headers) {
            this.headers = headers;
            this.alignment = new Alignment[getColumnCount()];
            this.rows = new ArrayList<>();
        }

        public Builder setAlignment(Alignment... alignment) {
            if (alignment.length != getColumnCount()) {
                throw new IllegalArgumentException();
            }
            this.alignment = alignment;
            return this;
        }

        public Builder addRow(InlineElement... cells) {
            if (cells.length != getColumnCount()) {
                throw new IllegalArgumentException();
            }
            rows.add(cells);
            return this;
        }

        public Table build() {
            for (int i = 0; i < getColumnCount(); i++) {
                if (alignment[i] == null) {
                    alignment[i] = Alignment.Unspecified;
                }
            }
            return new Table(headers, alignment, rows.toArray(new InlineElement[getRowCount()][]));
        }
    }

    public static enum Alignment {
        Unspecified(false, false),
        Left(true, false),
        Center(true, true),
        Right(false, true);

        private final boolean colonLeft;
        private final boolean colonRight;

        private Alignment(boolean colonLeft, boolean colonRight) {
            this.colonLeft = colonLeft;
            this.colonRight = colonRight;
        }

        private String getDelimeterRow(int length) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append('-');
            }
            if (colonLeft) {
                builder.setCharAt(0, ':');
            }
            if (colonRight) {
                builder.setCharAt(length - 1, ':');
            }
            return builder.toString();
        }

        private int getLeftPadding(int totalPadding) {
            switch (this) {
                case Center:
                    return totalPadding / 2;
                case Right:
                    return totalPadding;
                default:
                    return 0;
            }
        }

        private int getRightPadding(int totalPadding) {
            switch (this) {
                case Left:
                    return totalPadding;
                case Center:
                    return totalPadding / 2 + totalPadding % 2;
                default:
                    return 0;
            }
        }
    }
}
