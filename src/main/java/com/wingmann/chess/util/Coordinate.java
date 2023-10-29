package com.wingmann.chess.util;

import com.wingmann.chess.board.BoardLimit;

import java.util.Objects;

public class Coordinate {
    public char file;
    public int rank;
    public static Coordinate emptyCoordinate = new Coordinate((char) 0,0);

    public Coordinate(char file, int rank) {
        this.file = Character.toLowerCase(file);
        this.rank = rank;
    }

    public Coordinate(Coordinate original) {
        file = original.file;
        rank = original.rank;
    }

    public Coordinate(String coordinate) {
        boolean isValidCoordsFormat = (coordinate.length() == 2)
                && Character.isLetter(coordinate.charAt(0))
                && Character.isDigit(coordinate.charAt(1));

        if (isValidCoordsFormat) {
            file = Character.toLowerCase(coordinate.charAt(0));
            rank = Character.getNumericValue(coordinate.charAt(1));
        } else {
            System.err.println("Invalid coordinate format. Empty coordinate provided.");
            file = 0;
            rank = 0;
        }
    }

    public Coordinate() {
        file = 0;
        rank = 0;
    }

    public char getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public static boolean onBoard(Coordinate coords) {
        char coordsFile = coords.getFile();
        int coordsRank = coords.getRank();

        return (coordsFile >= BoardLimit.FIRST_FILE.getFile())
                && (coordsFile <= BoardLimit.LAST_FILE.getFile())
                && (coordsRank >= BoardLimit.FIRST_RANK.getRank())
                && (coordsRank <= BoardLimit.LAST_RANK.getRank());
    }

    @Override
    public String toString() {
        return String.format("%s%s", file, rank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return (file == that.file) && (rank == that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }
}
