package com.wingmann.chess.board;

import com.wingmann.chess.io.ChessIOManager;
import com.wingmann.chess.io.IOManager;
import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;

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
    public void gameLoop() {
        boolean exit = false;
        Color turn = Color.WHITE;
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        int numberOfTurns = 0;

        while (!exit) {
            String[] move = Piece.moveManager.moveQuery(scanner);

            boolean notOnBoard = !Coordinate.onBoard(new Coordinate(move[0]))
                    || !Coordinate.onBoard(new Coordinate(move[1]));

            if (notOnBoard) {
                System.err.println("At least one of the given coordinates isn't in the board. Please try again!");
                continue;
            }
            Coordinate origin = new Coordinate(move[0]);
            Coordinate destination = new Coordinate(move[1]);

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
            System.out.println(boardManager.displayBoard(pieces));

            if (pieces.isMate(Color.not(turn))) {
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

                        if (ioManager.saveGame(stringBuilder.toString(), filePath)) {
                            System.out.printf("Game saved successfully on path %s%n", filePath);
                        } else {
                            System.out.printf("There was an error saving the file on the path %s%n", filePath);
                        }
                    }
                }

                if (!exit) {
                    turn = Color.not(turn);
                    System.out.printf("%s to move.%n", turn);
                }
            }
        }
    }
}
