package com.wingmann.chess.util;

public enum BoardLimit implements Coordinates {
    FIRST_COLUMN('a'),
    LAST_COLUMN('h'),
    FIRST_ROW(1),
    LAST_ROW(8);

    private final CoordinatesData data = new CoordinatesData();

    BoardLimit(char column) {
        this.data.column = column;
    }

    BoardLimit(int row) {
        this.data.row = row;
    }

    @Override
    public char getColumn() {
        return data.column;
    }

    @Override
    public int getRow() {
        return data.row;
    }
}
