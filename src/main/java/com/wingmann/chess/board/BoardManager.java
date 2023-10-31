package com.wingmann.chess.board;

import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.Map;

public interface BoardManager {
    Map<BoardCoordinates, Piece> get();
    String prepareDraw(PieceState pieces);
}
