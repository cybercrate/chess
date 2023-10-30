package com.wingmann.chess.board;

import com.wingmann.chess.piece.*;
import com.wingmann.chess.util.BoardLimit;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.HashMap;
import java.util.Map;

public class ChessBoardManager implements BoardManager {
    @Override
    public Map<BoardCoordinates, Piece> get() {
        Map<BoardCoordinates, Piece> pieces = new HashMap<>();

        BoardPiecesFactory.getBlack(pieces);
        BoardPiecesFactory.getWhite(pieces);

        return pieces;
    }

    @Override
    public String display(PieceState pieces) {
        int dimRow = BoardLimit.FIRST_ROW.getRow();
        char dimColumn = BoardLimit.FIRST_COLUMN.getColumn();
        char lastColumn = BoardLimit.LAST_COLUMN.getColumn();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s%n%s%n", columnIndex(), separator()));

        for (int row = 8; row >= dimRow; row--) {
            stringBuilder.append(String.format("%s|", spacer(row,"L")));

            for (char column = dimColumn; column <= lastColumn; column++) {
                BoardCoordinates coords = new BoardCoordinates(column, row);
                Piece piece = pieces.getPieces().get(coords);

                if (piece != null) {
                    stringBuilder.append(String.format(" %s |", piece.toBoardString()));
                } else {
                    stringBuilder.append("    |");
                }
            }
            stringBuilder.append(String.format("%s%n%s%n", spacer(row,"R"), separator()));
        }

        stringBuilder.append(String.format("%s%n", columnIndex()));
        return stringBuilder.toString();
    }

    private String spacer(int n, String type) {
        return String.format(type.equals("L") ? "%s " : " %s", n);
    }

    private String columnIndex() {
        StringBuilder stringBuilder = new StringBuilder("   ");

        for (char column = 'A'; column <= 'H'; column++) {
            stringBuilder.append(String.format(" %s   ", column));
        }
        return stringBuilder.toString();
    }

    private String separator() {
        return String.format("  %s|", "|====".repeat(8));
    }
}
