package com.wingmann.chess.io;

import com.wingmann.chess.util.Coordinate;
import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;

import java.nio.file.Path;
import java.util.Scanner;

public interface IOManager {
    String moveString(PieceState pieces, Coordinate coordinate, Piece piece);
    Path fileQuery(Scanner in);
    boolean saveGame(String game, Path saveFile);
}
