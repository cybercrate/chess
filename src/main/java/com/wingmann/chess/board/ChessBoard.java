package com.wingmann.chess.board;

import com.wingmann.chess.io.ChessIOManager;
import com.wingmann.chess.io.IOManager;
import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

public class ChessBoard implements Board {
    private final PieceState pieces;
    private final BoardManager boardManager;
    private final IOManager ioManager;

    public ChessBoard(PieceState pieces) {
        this.pieces = pieces;
        this.boardManager = pieces.getBoardManager();
        this.ioManager = new ChessIOManager();
    }

    @Override
    public void runLoop() {
        boolean exit = false;
        Color turn = Color.WHITE;
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int numberOfTurns = 0;

        draw();

        while (!exit) {
            String[] move = Piece.moveManager.moveQuery(scanner);

            boolean notOnBoard = !BoardCoordinates.onBoard(new BoardCoordinates(move[0]))
                    || !BoardCoordinates.onBoard(new BoardCoordinates(move[1]));

            if (notOnBoard) {
                System.err.println("At least one of the given coordinates isn't in the board. Please try again!");
                continue;
            }
            BoardCoordinates origin = new BoardCoordinates(move[0]);
            BoardCoordinates destination = new BoardCoordinates(move[1]);

            Piece piece = pieces.getPiece(origin);

            if (piece.equals(Piece.emptyPiece)) {
                System.err.println("The origin coordinate doesn't contain a piece. Please try again!");
                continue;
            }

            if (!piece.isValidMove(destination, turn)) {
                System.err.println("Invalid move provided.");
                continue;
            }
            pieces.makeMove(destination, piece);

            if (Objects.requireNonNull(turn) == Color.WHITE) {
                ++numberOfTurns;
                stringBuilder
                        .append(numberOfTurns)
                        .append(". ")
                        .append(ioManager.moveString(pieces, destination, piece))
                        .append(" ");
            } else {
                stringBuilder.append(ioManager.moveString(pieces, destination, piece)).append(" ");
            }

            draw();

            if (pieces.isMate(Color.invert(turn))) {
                System.out.printf("%s win.%n", turn);
                exit = true;
            } else if (pieces.isStalemate(turn)) {
                System.out.println("It's a draw by stalemate.");
                exit = true;
            } else if (pieces.isDraw()) {
                System.out.println("It's a draw.");
                exit = true;
            } else {
                System.out.println("Enter 'exit' to end the game, or 'save' to save the current state of the game.");
                String input = scanner.nextLine();

                switch (input) {
                    case "exit" -> exit = true;
                    case "save" -> {
                        Path filePath = ioManager.fileQuery(scanner);

                        if (ioManager.save(stringBuilder.toString(), filePath)) {
                            System.out.printf("Game saved successfully on path %s%n", filePath);
                        } else {
                            System.out.printf("There was an error saving the file on the path %s%n", filePath);
                        }
                    }
                }

                if (!exit) {
                    turn = Color.invert(turn);
                    System.out.printf("%s to move.%n", turn);
                }
            }
        }
    }

    private void draw() {
        System.out.println(boardManager.prepareDraw(pieces));
    }
}
