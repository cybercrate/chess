package com.wingmann.chess.piece;

import com.wingmann.chess.board.BoardManager;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.Map;
import java.util.Set;

public interface State extends StateChecker {
    BoardManager getBoardManager();
    Map<BoardCoordinates, Piece> getPieces();
    Map<BoardCoordinates, Piece> getPreviousPieces();
    BoardCoordinates findPiece(Piece piece);
    BoardCoordinates findKing(Color color);
    Piece getPiece(BoardCoordinates coords);
    Map<BoardCoordinates, Piece> getColorPieces(Color color);
    Set<BoardCoordinates> allColouredRaws(Color color);
    void setPieces(Map<BoardCoordinates, Piece> pieces);
    void pieceMove(BoardCoordinates coords, Piece piece);
    void makeMove(BoardCoordinates coords, Piece piece);
}
