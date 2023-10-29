package com.wingmann.chess.piece;

import com.wingmann.chess.util.BoardCoordinates;

public interface PawnPiece {
    void setPreviousCoordinate(BoardCoordinates previous);
    BoardCoordinates getPreviousCoordinate();
    void setHasMovedTwo();
    boolean getEnPassantLeft();
    boolean getEnPassantRight();
    Piece promotionQuery(BoardCoordinates promotionSquare);
    boolean canPromoteBlack(BoardCoordinates coordinate);
    boolean canPromoteWhite(BoardCoordinates coordinate);
    Piece getPromotedPiece();
}
