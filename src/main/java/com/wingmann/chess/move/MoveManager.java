package com.wingmann.chess.move;

import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.ArrayList;
import java.util.Scanner;

public interface MoveManager extends KnightMove {
    boolean isNotTileColor(PieceState pieces, BoardCoordinates destination, Color color);
    boolean tileFull(PieceState pieces, BoardCoordinates destination);
    ArrayList<BoardCoordinates> frontFree(PieceState pieces, Piece piece, int limit);
    ArrayList<BoardCoordinates> backFree(PieceState pieces, Piece piece, int limit);
    ArrayList<BoardCoordinates> rightFree(PieceState pieces, Piece piece, int limit);
    ArrayList<BoardCoordinates> leftFree(PieceState pieces, Piece piece, int limit);
    ArrayList<BoardCoordinates> frontRDigFree(PieceState pieces, Piece piece, int limit);
    ArrayList<BoardCoordinates> backRDigFree(PieceState pieces, Piece piece, int limit);
    ArrayList<BoardCoordinates> backLDigFree(PieceState pieces, Piece piece, int limit);
    ArrayList<BoardCoordinates> frontLDigFree(PieceState pieces, Piece piece, int limit);
    String[] moveQuery(Scanner scanner);
}
