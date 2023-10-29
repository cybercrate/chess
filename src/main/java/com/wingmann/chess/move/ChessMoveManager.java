package com.wingmann.chess.move;

import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ChessMoveManager implements MoveManager {
    private static final String nullPieces = "You can't play with a null board.";
    private static final String nullCoord = "Board coordinates must not be null.";

    @Override
    public boolean isNotTileColor(PieceState pieces, BoardCoordinates destination, Color color) {
        Objects.requireNonNull(pieces, nullPieces);
        Objects.requireNonNull(destination, nullCoord);

        return !(pieces.getPieces().get(destination).getColor() == (color));
    }

    @Override
    public boolean tileFull(PieceState pieces, BoardCoordinates destination) {
        Objects.requireNonNull(pieces, nullPieces);
        Objects.requireNonNull(destination, nullCoord);

        return pieces.getPieces().get(destination) != null;
    }

    @Override
    public ArrayList<BoardCoordinates> frontFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? -1 : 1;

        return getCoordinates(pieces, piece, limit, moves, factor);
    }

    private ArrayList<BoardCoordinates> getCoordinates(
            PieceState pieces,
            Piece piece,
            int limit,
            ArrayList<BoardCoordinates> moves,
            int factor) {
        for (int advance = 1; advance <= limit; ++advance) {
            int change = factor * advance;
            BoardCoordinates checkCoords = new BoardCoordinates(piece.getFile(), piece.getRank() + change);

            if (check(pieces, piece, moves, checkCoords)) {
                return moves;
            }
        }
        return moves;
    }

    private boolean check(PieceState pieces, Piece piece, ArrayList<BoardCoordinates> moves, BoardCoordinates checkCoords) {
        if (BoardCoordinates.onBoard(checkCoords)) {
            boolean occupiedTile = tileFull(pieces, checkCoords);

            if (occupiedTile && isNotTileColor(pieces, checkCoords, piece.getColor())) {
                moves.add(checkCoords);
                return true;
            } else if (occupiedTile) {
                return true;
            } else {
                moves.add(checkCoords);
            }
        }
        return false;
    }

    @Override
    public ArrayList<BoardCoordinates> backFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? 1 : -1;

        return getCoordinates(pieces, piece, limit, moves, factor);
    }

    @Override
    public ArrayList<BoardCoordinates> rightFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? -1 : 1;

        for (int advance = 1; advance <= limit; advance++) {
            int change = factor*advance;
            BoardCoordinates checkCoord = new BoardCoordinates((char) (piece.getFile()+change),piece.getRank());

            if (check(pieces, piece, moves, checkCoord)) {
                return moves;
            }
        }
        return moves;
    }

    @Override
    public ArrayList<BoardCoordinates> leftFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? 1 : -1;

        for (int advance = 1; advance <= limit; advance++) {
            int change = factor*advance;
            BoardCoordinates checkCoord = new BoardCoordinates((char) (piece.getFile()+change),piece.getRank());
            if (check(pieces, piece, moves, checkCoord)) return moves;
        }
        return moves;
    }

    @Override
    public ArrayList<BoardCoordinates> frontRDigFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factorV;
        int factorH;

        factorV = factorH = piece.getColor().equals(Color.BLACK) ? -1 : 1;

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV * advance;
            int changeH = factorH * advance;
            BoardCoordinates checkCoord = new BoardCoordinates((char) (piece.getFile() + changeV),piece.getRank() + changeH);

            if (BoardCoordinates.onBoard(checkCoord)) {
                boolean occupiedTile = tileFull(pieces, checkCoord);

                if (occupiedTile && isNotTileColor(pieces, checkCoord, piece.getColor())) {
                    moves.add(checkCoord);
                    return moves;
                } else if (occupiedTile) {
                    return moves;
                } else if (!tileFull(pieces, checkCoord)) {
                    moves.add(checkCoord);
                }
            }
        }
        return moves;
    }

    @Override
    public ArrayList<BoardCoordinates> backRDigFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        boolean isBlack = piece.getColor().equals(Color.BLACK);

        int factorV = isBlack ? -1 : 1;
        int factorH = isBlack ? 1 : -1;

        for (int advance = 1; advance <= limit; ++advance) {
            int changeV = factorV * advance;
            int changeH = factorH * advance;

            BoardCoordinates checkCoords = new BoardCoordinates((char) (piece.getFile() + changeV),piece.getRank() + changeH);

            if (check(pieces, piece, moves, checkCoords)) {
                return moves;
            }
        }
        return moves;
    }

    @Override
    public ArrayList<BoardCoordinates> backLDigFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factorV;
        int factorH;

        factorV = factorH = piece.getColor().equals(Color.BLACK) ? 1 : -1;

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV * advance;
            int changeH = factorH * advance;
            BoardCoordinates checkCoords = new BoardCoordinates((char) (piece.getFile()+changeV),piece.getRank()+changeH);
            if (check(pieces, piece, moves, checkCoords)) return moves;
        }
        return moves;
    }

    @Override
    public ArrayList<BoardCoordinates> frontLDigFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        boolean isBlack = piece.getColor().equals(Color.BLACK);

        int factorV = isBlack ? 1 : -1;
        int factorH = isBlack ? -1 : 1;

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV * advance;
            int changeH = factorH * advance;

            BoardCoordinates checkCoords = new BoardCoordinates((char) (piece.getFile() + changeV),piece.getRank() + changeH);

            if (check(pieces, piece, moves, checkCoords)) {
                return moves;
            }
        }
        return moves;
    }

    @Override
    public ArrayList<BoardCoordinates> frontKnight(PieceState pieces, Piece piece) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? -1 : 1;
        int sideDistance = 1;
        int changeV = 2 * factor;
        int newRank = piece.getRank() + changeV;

        BoardCoordinates frontRight = new BoardCoordinates((char) (piece.getFile() + sideDistance),newRank);
        BoardCoordinates frontLeft = new BoardCoordinates((char) (piece.getFile() - sideDistance),newRank);

        if (BoardCoordinates.onBoard(frontLeft)) {
            boolean occupiedTile = tileFull(pieces, frontLeft);

            if (!occupiedTile || isNotTileColor(pieces, frontLeft, piece.getColor())) {
                moves.add(frontLeft);
            }
        }

        if (BoardCoordinates.onBoard(frontRight)) {
            boolean occupiedTile = tileFull(pieces, frontRight);

            if (!occupiedTile || isNotTileColor(pieces, frontRight, piece.getColor())) {
                moves.add(frontRight);
            }
        }
        return moves;
    }

    @Override
    public ArrayList<BoardCoordinates> backKnight(PieceState pieces, Piece piece) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? 1 : -1;
        int sideDistance = 1;
        int changeV = 2 * factor;
        int newRank = piece.getRank() + changeV;

        BoardCoordinates backRight = new BoardCoordinates((char) (piece.getFile() + sideDistance),newRank);
        BoardCoordinates backLeft = new BoardCoordinates((char) (piece.getFile() - sideDistance),newRank);

        if (BoardCoordinates.onBoard(backLeft)) {
            boolean occupiedTile = tileFull(pieces, backLeft);

            if (!occupiedTile || isNotTileColor(pieces, backLeft, piece.getColor())) {
                moves.add(backLeft);
            }
        }

        if (BoardCoordinates.onBoard(backRight)) {
            boolean occupiedTile = tileFull(pieces, backRight);

            if (!occupiedTile || isNotTileColor(pieces, backRight, piece.getColor())) {
                moves.add(backRight);
            }
        }
        return moves;
    }

    @Override
    public ArrayList<BoardCoordinates> rightKnight(PieceState pieces, Piece piece) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? -1 : 1;
        int sideDistance = 1;
        int changeH = 2 * factor;
        char newFile = (char) (piece.getFile() + changeH);

        BoardCoordinates rightTop = new BoardCoordinates(newFile,piece.getRank() + sideDistance);
        BoardCoordinates rightBottom = new BoardCoordinates(newFile, piece.getRank() - sideDistance);

        if (BoardCoordinates.onBoard(rightTop)) {
            boolean occupiedTile = tileFull(pieces, rightTop);

            if (!occupiedTile || isNotTileColor(pieces, rightTop, piece.getColor())) {
                moves.add(rightTop);
            }
        }

        if (BoardCoordinates.onBoard(rightBottom)) {
            boolean occupiedTile = tileFull(pieces, rightBottom);

            if (!occupiedTile || isNotTileColor(pieces, rightBottom, piece.getColor())) {
                moves.add(rightBottom);
            }
        }
        return moves;
    }

    @Override
    public ArrayList<BoardCoordinates> leftKnight(PieceState pieces, Piece piece) {
        ArrayList<BoardCoordinates> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? 1 : -1;
        int sideDistance = 1;
        int changeH = 2 * factor;
        char newFile = (char) (piece.getFile() + changeH);

        BoardCoordinates leftTop = new BoardCoordinates(newFile,piece.getRank() + sideDistance);
        BoardCoordinates leftBottom = new BoardCoordinates(newFile, piece.getRank() - sideDistance);

        if (BoardCoordinates.onBoard(leftBottom)) {
            boolean occupiedTile = tileFull(pieces, leftBottom);

            if (!occupiedTile || isNotTileColor(pieces, leftBottom, piece.getColor())) {
                moves.add(leftBottom);
            }
        }

        if (BoardCoordinates.onBoard(leftTop)) {
            boolean occupiedTile = tileFull(pieces, leftTop);

            if (!occupiedTile || isNotTileColor(pieces, leftTop, piece.getColor())) {
                moves.add(leftTop);
            }
        }
        return moves;
    }

    @Override
    public String[] moveQuery(Scanner scanner) {
        String[] move = new String[2];
        boolean validFormat = false;

        while (!validFormat) {
            System.out.println("""
                    Enter a piece and its destination.
                    For example, to move a piece in e2 to e4, write 'e2 e4'.
                    """);
            String userInput = scanner.nextLine();

            if (userInput.length() != 5 || !userInput.contains(" ")) {
                System.out.println("""
                        Move instructions provided in an incorrect format.
                        Try again!
                        """);
                continue;
            }

            move = userInput.split(" ");
            validFormat = true;
        }
        return move;
    }
}
