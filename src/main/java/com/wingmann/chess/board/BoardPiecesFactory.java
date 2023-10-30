package com.wingmann.chess.board;

import com.wingmann.chess.piece.*;
import com.wingmann.chess.util.BoardCoordinates;
import com.wingmann.chess.util.Color;

import java.util.Map;

public class BoardPiecesFactory {
    public static void getBlack(Map<BoardCoordinates, Piece> pieces) {
        get(pieces, Color.BLACK);
    }

    public static void getWhite(Map<BoardCoordinates, Piece> pieces) {
        get(pieces, Color.WHITE);
    }

    private static void get(Map<BoardCoordinates, Piece> pieces, Color color) {
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
}
