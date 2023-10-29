package com.wingmann.chess.game;

import com.wingmann.chess.board.ChessBoard;
import com.wingmann.chess.board.BoardManager;
import com.wingmann.chess.board.ChessBoardManager;
import com.wingmann.chess.piece.PieceState;

public class ChessGame implements Game {
    private final ChessBoard board;

    public ChessGame() {
        BoardManager boardManager = new ChessBoardManager();
        PieceState pieces = new PieceState(boardManager);

        this.board = new ChessBoard(pieces);
    }

    @Override
    public void play() {
        board.gameLoop();
    }
}
