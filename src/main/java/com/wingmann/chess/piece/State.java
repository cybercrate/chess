package com.wingmann.chess.piece;

import com.wingmann.chess.board.BoardManager;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.Map;
import java.util.Set;

public interface State {
    BoardManager getBoardManager();
    Map<BoardCoordinates, Piece> getPieces();
    void setPieces(Map<BoardCoordinates, Piece> pieces);
    boolean getIsCapture();
    Map<BoardCoordinates, Piece> getPreviousPieces();
    BoardCoordinates findPiece(Piece piece);
    BoardCoordinates findKing(Color color);
    Piece getPiece(BoardCoordinates coords);
    Map<BoardCoordinates, Piece> getColourPieces(Color color);
    Set<BoardCoordinates> allColouredRaws(Color color);
    boolean pieceInSameFile (Piece piece);
    boolean pieceInSameRank (Piece piece);
    boolean pieceToSameCoordinate (BoardCoordinates coords, Piece piece);
    boolean isCheck(Color color);
    boolean isMate(Color color);
    boolean isDraw();
    boolean isStalemate(Color color);
    void pieceMove(BoardCoordinates coords, Piece piece);
    void makeMove(BoardCoordinates coords, Piece piece);
}
