package com.wingmann.chess.util;

import java.util.Objects;

public class BoardCoordinates implements Coordinates {
    private final CoordinatesData data;
    public static final BoardCoordinates EMPTY_COORDS = new BoardCoordinates((char) 0,0);

    public BoardCoordinates() {
        this.data = new CoordinatesData();
    }

    public BoardCoordinates(char column, int row) {
        this.data = new CoordinatesData(Character.toLowerCase(column), row);
    }

    public BoardCoordinates(BoardCoordinates original) {
        this.data = original.data;
    }

    public BoardCoordinates(String coords) {
        boolean isValid = (coords.length() == 2)
                && Character.isLetter(coords.charAt(0))
                && Character.isDigit(coords.charAt(1));

        if (isValid) {
            this.data = new CoordinatesData(
                    Character.toLowerCase(coords.charAt(0)),
                    Character.getNumericValue(coords.charAt(1)));
        } else {
            System.err.println("Invalid coordinate format. Empty coordinate provided.");
            this.data = new CoordinatesData();
        }
    }

    @Override
    public char getColumn() {
        return data.column;
    }

    @Override
    public int getRow() {
        return data.row;
    }

    public static boolean onBoard(BoardCoordinates coords) {
        char coordsColumn = coords.getColumn();
        int coordsRow = coords.getRow();

        return (coordsColumn >= BoardLimit.FIRST_COLUMN.getColumn())
                && (coordsColumn <= BoardLimit.LAST_COLUMN.getColumn())
                && (coordsRow >= BoardLimit.FIRST_ROW.getRow())
                && (coordsRow <= BoardLimit.LAST_ROW.getRow());
    }

    @Override
    public String toString() {
        return String.format("%s%s", getColumn(), getRow());
    }

    @Override
    public boolean equals(Object value) {
        if (this == value) return true;
        if ((value == null) || (getClass() != value.getClass())) return false;

        BoardCoordinates other = (BoardCoordinates) value;
        return (this.getColumn() == other.getColumn()) && (this.getRow() == other.getRow());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColumn(), getRow());
    }
}
