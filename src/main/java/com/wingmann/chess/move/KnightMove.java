package com.wingmann.chess.move;

import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.Coordinate;

import java.util.ArrayList;

public interface KnightMove {
    ArrayList<Coordinate> frontKnight(PieceState pieces, Piece piece);
    ArrayList<Coordinate> backKnight(PieceState pieces, Piece piece);
    ArrayList<Coordinate> rightKnight(PieceState pieces, Piece piece);
    ArrayList<Coordinate> leftKnight(PieceState pieces, Piece piece);
}
