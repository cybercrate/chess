package com.wingmann.chess.piece;

import com.wingmann.chess.board.BoardManager;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;

import java.util.Map;
import java.util.Set;

public interface State {
    BoardManager getBoardManager();
    Map<Coordinate, Piece> getPieces();
    void setPieces(Map<Coordinate, Piece> pieces);
    boolean getIsCapture();
    Map<Coordinate, Piece> getPreviousPieces();
    Coordinate findPiece(Piece piece);
    Coordinate findKing(Color colour);
    Piece getPiece(Coordinate coordinate);
    Map<Coordinate, Piece> getColourPieces(Color colour);
    Set<Coordinate> allColouredRaws (Color colour);
    boolean pieceInSameFile (Piece piece);
    boolean pieceInSameRank (Piece piece);
    boolean pieceToSameCoordinate (Coordinate coordinate, Piece piece);
    boolean isCheck(Color colour);
    boolean isMate(Color colour);
    boolean isDraw();
    boolean isStalemate(Color colour);
    void pieceMove(Coordinate coordinate, Piece piece);
    void makeMove(Coordinate coordinate, Piece piece);
}
