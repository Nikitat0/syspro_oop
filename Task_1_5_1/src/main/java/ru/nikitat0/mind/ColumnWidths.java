package ru.nikitat0.mind;

final class ColumnsWidths {
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
