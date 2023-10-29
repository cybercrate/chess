package com.wingmann.chess.board;

import com.wingmann.chess.piece.*;
import com.wingmann.chess.util.BoardCoordinates;
import com.wingmann.chess.util.Color;

import java.util.Map;

public class BoardPiecesFactory {
    public static void getBlack(Map<BoardCoordinates, Piece> pieces) {
        int blackPawnRowLevel = 7;
        int blackPiecesRowLevel = 8;

        Piece pawnBa = new Pawn(Color.BLACK, new BoardCoordinates('a', blackPawnRowLevel));
        Piece pawnBb = new Pawn(Color.BLACK, new BoardCoordinates('b', blackPawnRowLevel));
        Piece pawnBc = new Pawn(Color.BLACK, new BoardCoordinates('c', blackPawnRowLevel));
        Piece pawnBd = new Pawn(Color.BLACK, new BoardCoordinates('d', blackPawnRowLevel));
        Piece pawnBe = new Pawn(Color.BLACK, new BoardCoordinates('e', blackPawnRowLevel));
        Piece pawnBf = new Pawn(Color.BLACK, new BoardCoordinates('f', blackPawnRowLevel));
        Piece pawnBg = new Pawn(Color.BLACK, new BoardCoordinates('g', blackPawnRowLevel));
        Piece pawnBh = new Pawn(Color.BLACK, new BoardCoordinates('h', blackPawnRowLevel));
        Piece rookBa = new Rook(Color.BLACK, new BoardCoordinates('a', blackPiecesRowLevel));
        Piece rookBh = new Rook(Color.BLACK, new BoardCoordinates('h', blackPiecesRowLevel));
        Piece knightBb = new Knight(Color.BLACK, new BoardCoordinates('b', blackPiecesRowLevel));
        Piece knightBg = new Knight(Color.BLACK, new BoardCoordinates('g', blackPiecesRowLevel));
        Piece bishopBc = new Bishop(Color.BLACK, new BoardCoordinates('c', blackPiecesRowLevel));
        Piece bishopBf = new Bishop(Color.BLACK, new BoardCoordinates('f', blackPiecesRowLevel));
        Piece queenB = new Queen(Color.BLACK, new BoardCoordinates('d', blackPiecesRowLevel));
        Piece kingB = new King(Color.BLACK, new BoardCoordinates('e', blackPiecesRowLevel));

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

    public static void getWhite(Map<BoardCoordinates, Piece> pieces) {
        int whitePawnRowLevel = 2;
        int whitePiecesRowLevel = 1;

        Piece pawnWa = new Pawn(Color.WHITE, new BoardCoordinates('a', whitePawnRowLevel));
        Piece pawnWb = new Pawn(Color.WHITE, new BoardCoordinates('b', whitePawnRowLevel));
        Piece pawnWc = new Pawn(Color.WHITE, new BoardCoordinates('c', whitePawnRowLevel));
        Piece pawnWd = new Pawn(Color.WHITE, new BoardCoordinates('d', whitePawnRowLevel));
        Piece pawnWe = new Pawn(Color.WHITE, new BoardCoordinates('e', whitePawnRowLevel));
        Piece pawnWf = new Pawn(Color.WHITE, new BoardCoordinates('f', whitePawnRowLevel));
        Piece pawnWg = new Pawn(Color.WHITE, new BoardCoordinates('g', whitePawnRowLevel));
        Piece pawnWh = new Pawn(Color.WHITE, new BoardCoordinates('h', whitePawnRowLevel));
        Piece rookWa = new Rook(Color.WHITE, new BoardCoordinates('a', whitePiecesRowLevel));
        Piece rookWh = new Rook(Color.WHITE, new BoardCoordinates('h', whitePiecesRowLevel));
        Piece knightWb = new Knight(Color.WHITE, new BoardCoordinates('b', whitePiecesRowLevel));
        Piece knightWg = new Knight(Color.WHITE, new BoardCoordinates('g', whitePiecesRowLevel));
        Piece bishopWc = new Bishop(Color.WHITE, new BoardCoordinates('c', whitePiecesRowLevel));
        Piece bishopWf = new Bishop(Color.WHITE, new BoardCoordinates('f', whitePiecesRowLevel));
        Piece queenW = new Queen(Color.WHITE, new BoardCoordinates('d', whitePiecesRowLevel));
        Piece kingW = new King(Color.WHITE, new BoardCoordinates('e', whitePiecesRowLevel));

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
}
