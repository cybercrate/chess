package com.wingmann.chess.move;

import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;

import java.util.ArrayList;
import java.util.Scanner;

public interface MoveManager extends KnightMove {
    boolean isNotTileColor(PieceState pieces, Coordinate destination, Color color);
    boolean tileFull(PieceState pieces, Coordinate destination);
    ArrayList<Coordinate> frontFree(PieceState pieces, Piece piece, int limit);
    ArrayList<Coordinate> backFree(PieceState pieces, Piece piece, int limit);
    ArrayList<Coordinate> rightFree(PieceState pieces, Piece piece, int limit);
    ArrayList<Coordinate> leftFree(PieceState pieces, Piece piece, int limit);
    ArrayList<Coordinate> frontRDigFree(PieceState pieces, Piece piece, int limit);
    ArrayList<Coordinate> backRDigFree(PieceState pieces, Piece piece, int limit);
    ArrayList<Coordinate> backLDigFree(PieceState pieces, Piece piece, int limit);
    ArrayList<Coordinate> frontLDigFree(PieceState pieces, Piece piece, int limit);
    String[] moveQuery(Scanner scanner);
}
