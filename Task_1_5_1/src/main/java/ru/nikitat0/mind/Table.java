package ru.nikitat0.mind;

import java.util.ArrayList;

public class Table extends Element.Block {
    private final Element.Inline[] headers;
    private final Alignment[] alignment;
    private final Element.Inline[][] rows;

    private int getRowCount() {
        return rows.length;
    }

    private int getColumnCount() {
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
        if (this.getColumnCount() != otherTable.getColumnCount()) {
            return false;
        }
        if (this.getRowCount() != otherTable.getRowCount()) {
            return false;
        }
        for (int i = 0; i < this.getColumnCount(); i++) {
            if (!this.headers[i].equals(otherTable.headers[i])) {
                return false;
            }
            if (this.alignment[i] != otherTable.alignment[i]) {
                return false;
            }
        }
        for (int j = 0; j < this.getRowCount(); j++) {
            for (int i = 0; i < this.getColumnCount(); i++) {
                if (this.rows[j][i].equals(otherTable.rows[j][i])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        ColumnsWidths widths = new ColumnsWidths(getColumnCount()).updateAll(3);

        String[] renderedHeaders = new String[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++) {
            renderedHeaders[i] = headers[i].toString();
            widths.update(i, renderedHeaders[i].length());
        }

        String[][] renderedRows = new String[getRowCount()][];
        for (int j = 0; j < getRowCount(); j++) {
            renderedRows[j] = new String[getColumnCount()];
            for (int i = 0; i < getColumnCount(); i++) {
                renderedRows[j][i] = rows[j][i].toString();
                widths.update(i, renderedRows[j][i].length());
            }
        }

        String[] delimeterRows = new String[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++) {
            delimeterRows[i] = alignment[i].getDelimeterRow(widths.get(i));
        }

        String[][] rows = new String[getRowCount() + 2][];
        rows[0] = renderedHeaders;
        rows[1] = delimeterRows;
        System.arraycopy(renderedRows, 0, rows, 2, getRowCount());

        StringBuilder builder = new StringBuilder();
        for (String[] row : rows) {
            for (int i = 0; i < getColumnCount(); i++) {
                builder.append("| ");

                int totalPadding = widths.get(i) - row[i].length();
                for (int c = 0; c < alignment[i].getLeftPadding(totalPadding); c++) {
                    builder.append(' ');
                }
                builder.append(row[i]);
                for (int c = 0; c < alignment[i].getRightPadding(totalPadding); c++) {
                    builder.append(' ');
                }

                builder.append(' ');
            }
            builder.append("|\n");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private static final class ColumnsWidths {
        private final int[] widths;

        ColumnsWidths(int columnCount) {
            this.widths = new int[columnCount];
        }

        public int getColumnsCount() {
            return widths.length;
        }

        public int get(int column) {
            return widths[column];
        }

        public ColumnsWidths update(int column, int width) {
            if (width > widths[column]) {
                widths[column] = width;
            }
            return this;
        }

        public ColumnsWidths updateAll(int width) {
            for (int i = 0; i < getColumnsCount(); i++) {
                update(i, width);
            }
            return this;
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
                case Unspecified:
                case Left:
                    return totalPadding;
                case Center:
                    return totalPadding / 2 + totalPadding % 2;
                default:
                    return 0;
            }
        }
    }

    private Table(Element.Inline[] headers, Alignment[] alignment, Element.Inline[][] rows) {
        this.headers = headers;
        this.alignment = alignment;
        this.rows = rows;
    }

    public static final class Builder {
        private final Element.Inline[] headers;
        private Alignment[] alignment;
        private ArrayList<Element.Inline[]> rows;

        private int getRowCount() {
            return rows.size();
        }

        private int getColumnCount() {
            return headers.length;
        }

        public Builder(Element.Inline... headers) {
            this.headers = headers;
            this.alignment = new Alignment[getColumnCount()];
            this.rows = new ArrayList<>();
        }

        private void checkColumns(int actual) {
            if (actual == getColumnCount()) {
                return;
            }
            throw new IllegalArgumentException(
                    String.format("%d columns expected", getColumnCount()));
        }

        public Builder setAlignment(Alignment... alignment) {
            checkColumns(alignment.length);
            this.alignment = alignment;
            return this;
        }

        public Builder addRow(Element.Inline... cells) {
            checkColumns(cells.length);
            rows.add(cells);
            return this;
        }

        public Table build() {
            for (int i = 0; i < getColumnCount(); i++) {
                if (alignment[i] == null) {
                    alignment[i] = Alignment.Unspecified;
                }
            }
            return new Table(headers, alignment, rows.toArray(new Element.Inline[getRowCount()][]));
        }
    }
}
