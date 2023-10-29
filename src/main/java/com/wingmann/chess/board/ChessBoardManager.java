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
        int dimRank = BoardLimit.FIRST_ROW.getRow();
        char dimFile = BoardLimit.FIRST_COLUMN.getColumn();
        char lastFile = BoardLimit.LAST_COLUMN.getColumn();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(columnIndex())
                .append("\n")
                .append(separator())
                .append("\n");

        for (int rank = 8; rank >= dimRank; --rank) {
            stringBuilder
                    .append(spacer(rank,"L"))
                    .append("|");

            for (char file = dimFile; file <= lastFile; file++) {
                BoardCoordinates coords = new BoardCoordinates(file, rank);
                boolean correctCoordinates = pieces.getPieces().get(coords) != null;

                stringBuilder.append(correctCoordinates
                        ? String.format(" %s |", pieces.getPieces().get(coords).toBoardString())
                        : "    |");
            }
            stringBuilder
                    .append(spacer(rank,"R"))
                    .append("\n")
                    .append(separator())
                    .append("\n");
        }
        stringBuilder.append(columnIndex()).append("\n");
        return stringBuilder.toString();
    }

    private String spacer(int n, String type) {
        return String.format(type.equals("L") ? "%s " : " %s", n);
    }

    private String columnIndex() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("   ");

        for (char file = 'a'; file <= 'h'; ++file) {
            stringBuilder
                    .append(" ")
                    .append(file)
                    .append("   ");
        }
        return stringBuilder.toString();
    }

    private String separator() {
        return String.format("  %s|", "|====".repeat(8));
    }
}
