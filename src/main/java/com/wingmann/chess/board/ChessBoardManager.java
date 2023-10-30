package com.wingmann.chess.board;

import com.wingmann.chess.piece.*;
import com.wingmann.chess.util.BoardLimit;
import com.wingmann.chess.util.BoardCoordinates;
import com.wingmann.chess.util.Color;

import java.util.HashMap;
import java.util.Map;

public class ChessBoardManager implements BoardManager {
    @Override
    public Map<BoardCoordinates, Piece> get() {
        Map<BoardCoordinates, Piece> pieces = new HashMap<>();

        generate(Color.BLACK, pieces);
        generate(Color.WHITE, pieces);

        return pieces;
    }

    private void generate(Color color, Map<BoardCoordinates, Piece> pieces) {
        final int pawnRow;
        final int row;

        if (color == Color.BLACK) {
            pawnRow = 7;
            row = 8;
        } else {
            pawnRow = 2;
            row = 1;
        }

        char column = 'a';

        for (int i = 0; i < 8; i++, column++) {
            BoardCoordinates current = new BoardCoordinates(column, pawnRow);
            pieces.put(current, new Pawn(color, current));
        }

        Piece current;

        current = new Rook(color, new BoardCoordinates('a', row));
        pieces.put(current.getCoords(), current);

        current = new Rook(color, new BoardCoordinates('h', row));
        pieces.put(current.getCoords(), current);

        current = new Knight(color, new BoardCoordinates('b', row));
        pieces.put(current.getCoords(), current);

        current = new Knight(color, new BoardCoordinates('g', row));
        pieces.put(current.getCoords(), current);

        current = new Bishop(color, new BoardCoordinates('c', row));
        pieces.put(current.getCoords(), current);

        current = new Bishop(color, new BoardCoordinates('f', row));
        pieces.put(current.getCoords(), current);

        current = new Queen(color, new BoardCoordinates('d', row));
        pieces.put(current.getCoords(), current);

        current = new King(color, new BoardCoordinates('e', row));
        pieces.put(current.getCoords(), current);
    }

    @Override
    public String display(PieceState pieces) {
        int dimRow = BoardLimit.FIRST_ROW.getRow();
        char dimColumn = BoardLimit.FIRST_COLUMN.getColumn();
        char lastColumn = BoardLimit.LAST_COLUMN.getColumn();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%n%s%n", getColumnIndex(), getSeparator()));

        for (int row = 8; row >= dimRow; row--) {
            sb.append(String.format("%s|", getSpacer(row,true)));
            Piece piece;

            for (char column = dimColumn; column <= lastColumn; column++) {
                BoardCoordinates coords = new BoardCoordinates(column, row);
                piece = pieces.getPieces().get(coords);

                sb.append(piece != null ? String.format(" %s |", piece.toBoardString()) : "    |");
            }
            sb.append(String.format("%s%n%s%n", getSpacer(row,false), getSeparator()));
        }

        sb.append(String.format("%s%n", getColumnIndex()));
        return sb.toString();
    }

    private String getSpacer(int row, boolean leftSide) {
        return String.format(leftSide ? "%s " : " %s", row);
    }

    private String getColumnIndex() {
        StringBuilder sb = new StringBuilder("   ");

        for (char column = 'A'; column <= 'H'; column++) {
            sb.append(String.format(" %s   ", column));
        }
        return sb.toString();
    }

    private String getSeparator() {
        return String.format("  %s|", "|====".repeat(8));
    }
}
