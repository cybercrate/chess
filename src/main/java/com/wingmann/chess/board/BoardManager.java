package com.wingmann.chess.board;

import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.Coordinate;

import java.util.Map;

public interface BoardManager {
    Map<Coordinate, Piece> getBoard();
    String displayBoard(PieceState pieces);
}
