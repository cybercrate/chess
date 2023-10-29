package com.wingmann.chess.piece;

import com.wingmann.chess.util.BoardCoordinates;

public interface KingPiece {
    BoardCoordinates getCastleCoordinatesKingK();
    BoardCoordinates getCastleCoordinatesKingQ();
    BoardCoordinates getTransitionCoordinatesKingK();
    BoardCoordinates getTransitionCoordinatesKingQ();
    Rook getRookKing();
    Rook getRookQueen();
    boolean canCastleKing (PieceState pieces);
    boolean canCastleQueen (PieceState pieces);
}
