package com.wingmann.chess.board;

public enum BoardLimit {
    FIRST_FILE('a'),
    LAST_FILE('h'),
    FIRST_RANK(1),
    LAST_RANK(8);

    private char file;
    private int rank;

    BoardLimit(char file) {
        this.file = file;
    }

    BoardLimit(int rank) {
        this.rank = rank;
    }

    public char getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }
}
