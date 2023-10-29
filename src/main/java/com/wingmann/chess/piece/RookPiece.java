package com.wingmann.chess.piece;

import com.wingmann.chess.util.BoardCoordinates;

public interface RookPiece {
    BoardCoordinates getCastleCoordinatesRook();
    void setCastleCoordinatesRook(BoardCoordinates castleCoordinatesRook);
}
