package com.wingmann.chess.piece;

import com.wingmann.chess.util.Coordinate;

public interface KingPiece {
    Coordinate getCastleCoordinatesKingK();
    Coordinate getCastleCoordinatesKingQ();
    Coordinate getTransitionCoordinatesKingK();
    Coordinate getTransitionCoordinatesKingQ();
    Rook getRookKing();
    Rook getRookQueen();
    boolean canCastleKing (PieceState pieces);
    boolean canCastleQueen (PieceState pieces);
}
