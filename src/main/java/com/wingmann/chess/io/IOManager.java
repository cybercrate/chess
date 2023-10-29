package com.wingmann.chess.io;

import com.wingmann.chess.util.BoardCoordinates;
import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;

import java.nio.file.Path;
import java.util.Scanner;

public interface IOManager {
    String moveString(PieceState state, BoardCoordinates coords, Piece piece);
    Path fileQuery(Scanner in);
    boolean save(String game, Path saveFile);
}
