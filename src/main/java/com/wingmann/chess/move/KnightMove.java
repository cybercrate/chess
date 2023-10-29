package com.wingmann.chess.move;

import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.ArrayList;

public interface KnightMove {
    ArrayList<BoardCoordinates> frontKnight(PieceState pieces, Piece piece);
    ArrayList<BoardCoordinates> backKnight(PieceState pieces, Piece piece);
    ArrayList<BoardCoordinates> rightKnight(PieceState pieces, Piece piece);
    ArrayList<BoardCoordinates> leftKnight(PieceState pieces, Piece piece);
}
