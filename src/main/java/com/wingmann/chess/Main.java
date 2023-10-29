package com.wingmann.chess;

import com.wingmann.chess.game.ChessGame;
import com.wingmann.chess.game.Game;

public class Main {
    public static void main(String[] args) {
        Game chess = new ChessGame();
        chess.play();
    }
}
