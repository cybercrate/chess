package com.wingmann.chess.board;

import com.wingmann.chess.piece.*;
import com.wingmann.chess.util.BoardLimit;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.HashMap;
import java.util.Map;

public class ChessBoardManager implements BoardManager {
    @Override
    public Map<BoardCoordinates, Piece> get() {
        Map<BoardCoordinates, Piece> pieces = new HashMap<>();

        getBlackPieces(pieces);
        getWhitePieces(pieces);

        return pieces;
    }

    private void getBlackPieces(Map<BoardCoordinates, Piece> pieces) {
        int blackPawnRank = 7;
        int blackRank = 8;

        Piece pawnBa = new Pawn(Color.BLACK, new BoardCoordinates('a', blackPawnRank));
        Piece pawnBb = new Pawn(Color.BLACK, new BoardCoordinates('b', blackPawnRank));
        Piece pawnBc = new Pawn(Color.BLACK, new BoardCoordinates('c', blackPawnRank));
        Piece pawnBd = new Pawn(Color.BLACK, new BoardCoordinates('d', blackPawnRank));
        Piece pawnBe = new Pawn(Color.BLACK, new BoardCoordinates('e', blackPawnRank));
        Piece pawnBf = new Pawn(Color.BLACK, new BoardCoordinates('f', blackPawnRank));
        Piece pawnBg = new Pawn(Color.BLACK, new BoardCoordinates('g', blackPawnRank));
        Piece pawnBh = new Pawn(Color.BLACK, new BoardCoordinates('h', blackPawnRank));
        Piece rookBa = new Rook(Color.BLACK, new BoardCoordinates('a', blackRank));
        Piece rookBh = new Rook(Color.BLACK, new BoardCoordinates('h', blackRank));
        Piece knightBb = new Knight(Color.BLACK, new BoardCoordinates('b', blackRank));
        Piece knightBg = new Knight(Color.BLACK, new BoardCoordinates('g', blackRank));
        Piece bishopBc = new Bishop(Color.BLACK, new BoardCoordinates('c', blackRank));
        Piece bishopBf = new Bishop(Color.BLACK, new BoardCoordinates('f', blackRank));
        Piece queenB = new Queen(Color.BLACK, new BoardCoordinates('d', blackRank));
        Piece kingB = new King(Color.BLACK, new BoardCoordinates('e', blackRank));

        pieces.put(pawnBa.getCoords(), pawnBa);
        pieces.put(pawnBb.getCoords(), pawnBb);
        pieces.put(pawnBc.getCoords(), pawnBc);
        pieces.put(pawnBd.getCoords(), pawnBd);
        pieces.put(pawnBe.getCoords(), pawnBe);
        pieces.put(pawnBf.getCoords(), pawnBf);
        pieces.put(pawnBg.getCoords(), pawnBg);
        pieces.put(pawnBh.getCoords(), pawnBh);
        pieces.put(rookBa.getCoords(), rookBa);
        pieces.put(rookBh.getCoords(), rookBh);
        pieces.put(knightBb.getCoords(), knightBb);
        pieces.put(knightBg.getCoords(), knightBg);
        pieces.put(bishopBc.getCoords(), bishopBc);
        pieces.put(bishopBf.getCoords(), bishopBf);
        pieces.put(queenB.getCoords(), queenB);
        pieces.put(kingB.getCoords(), kingB);
    }

    private void getWhitePieces(Map<BoardCoordinates, Piece> pieces) {
        int whitePawnRank = 2;
        int whiteRank = 1;

        Piece pawnWa = new Pawn(Color.WHITE, new BoardCoordinates('a', whitePawnRank));
        Piece pawnWb = new Pawn(Color.WHITE, new BoardCoordinates('b', whitePawnRank));
        Piece pawnWc = new Pawn(Color.WHITE, new BoardCoordinates('c', whitePawnRank));
        Piece pawnWd = new Pawn(Color.WHITE, new BoardCoordinates('d', whitePawnRank));
        Piece pawnWe = new Pawn(Color.WHITE, new BoardCoordinates('e', whitePawnRank));
        Piece pawnWf = new Pawn(Color.WHITE, new BoardCoordinates('f', whitePawnRank));
        Piece pawnWg = new Pawn(Color.WHITE, new BoardCoordinates('g', whitePawnRank));
        Piece pawnWh = new Pawn(Color.WHITE, new BoardCoordinates('h', whitePawnRank));
        Piece rookWa = new Rook(Color.WHITE, new BoardCoordinates('a', whiteRank));
        Piece rookWh = new Rook(Color.WHITE, new BoardCoordinates('h', whiteRank));
        Piece knightWb = new Knight(Color.WHITE, new BoardCoordinates('b', whiteRank));
        Piece knightWg = new Knight(Color.WHITE, new BoardCoordinates('g', whiteRank));
        Piece bishopWc = new Bishop(Color.WHITE, new BoardCoordinates('c', whiteRank));
        Piece bishopWf = new Bishop(Color.WHITE, new BoardCoordinates('f', whiteRank));
        Piece queenW = new Queen(Color.WHITE, new BoardCoordinates('d', whiteRank));
        Piece kingW = new King(Color.WHITE, new BoardCoordinates('e', whiteRank));

        pieces.put(pawnWa.getCoords(), pawnWa);
        pieces.put(pawnWb.getCoords(), pawnWb);
        pieces.put(pawnWc.getCoords(), pawnWc);
        pieces.put(pawnWd.getCoords(), pawnWd);
        pieces.put(pawnWe.getCoords(), pawnWe);
        pieces.put(pawnWf.getCoords(), pawnWf);
        pieces.put(pawnWg.getCoords(), pawnWg);
        pieces.put(pawnWh.getCoords(), pawnWh);
        pieces.put(rookWa.getCoords(), rookWa);
        pieces.put(rookWh.getCoords(), rookWh);
        pieces.put(knightWb.getCoords(), knightWb);
        pieces.put(knightWg.getCoords(), knightWg);
        pieces.put(bishopWc.getCoords(), bishopWc);
        pieces.put(bishopWf.getCoords(), bishopWf);
        pieces.put(queenW.getCoords(), queenW);
        pieces.put(kingW.getCoords(), kingW);
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
