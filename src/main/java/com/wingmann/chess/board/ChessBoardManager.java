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
        final boolean isBlack = (color == Color.BLACK);
        final int pawnRow = isBlack ? 7 : 2;
        final int row = isBlack ? 8 : 1;

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
    public String prepareDraw(PieceState pieces) {
        int firstRow = BoardLimit.FIRST_ROW.getRow();
        char firstColumn = BoardLimit.FIRST_COLUMN.getColumn();
        char lastColumn = BoardLimit.LAST_COLUMN.getColumn();

        StringBuilder sb = new StringBuilder();

        sb.append(getColumnIndex())
                .append("\n")
                .append(getSeparator())
                .append("\n");

        for (int row = 8; row >= firstRow; row--) {
            sb.append(getSpacer(row, true)).append("|");

            Piece piece;

            for (char column = firstColumn; column <= lastColumn; column++) {
                BoardCoordinates coords = new BoardCoordinates(column, row);
                piece = pieces.getPieces().get(coords);

                if (piece != null) {
                    sb.append(" ")
                            .append(piece.toBoardString())
                            .append(" |");
                } else {
                    sb.append("    |");
                }
            }
            sb.append(getSpacer(row, false))
                    .append("\n")
                    .append(getSeparator())
                    .append("\n");
        }
        return sb.append(getColumnIndex())
                .append("\n")
                .toString();
    }

    private String getSpacer(int row, boolean leftSide) {
        return String.format(leftSide ? "%s " : " %s", row);
    }

    private String getColumnIndex() {
        StringBuilder sb = new StringBuilder("   ");

        for (char column = 'A'; column <= 'H'; column++) {
            sb.append(" ")
                    .append(column)
                    .append("   ");
        }
        return sb.toString();
    }

    private String getSeparator() {
        return String.format("  %s|", "|====".repeat(8));
    }
}
