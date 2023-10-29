package com.wingmann.chess.piece;

import com.wingmann.chess.util.BoardCoordinates;
import com.wingmann.chess.util.Color;

public interface StateChecker {
    boolean isCapture();
    boolean pieceInSameColumn(Piece piece);
    boolean pieceInSameRow(Piece piece);
    boolean pieceToSameCoordinate(BoardCoordinates coords, Piece piece);
    boolean isCheck(Color color);
    boolean isMate(Color color);
    boolean isDraw();
    boolean isStalemate(Color color);
}
