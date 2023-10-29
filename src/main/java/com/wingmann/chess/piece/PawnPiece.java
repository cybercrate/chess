package com.wingmann.chess.piece;

import com.wingmann.chess.util.Coordinate;

public interface PawnPiece {
    void setPreviousCoordinate(Coordinate previous);
    Coordinate getPreviousCoordinate();
    void setHasMovedTwo();
    boolean getEnPassantLeft();
    boolean getEnPassantRight();
    Piece promotionQuery(Coordinate promotionSquare);
    boolean canPromoteBlack(Coordinate coordinate);
    boolean canPromoteWhite(Coordinate coordinate);
    Piece getPromotedPiece();
}
