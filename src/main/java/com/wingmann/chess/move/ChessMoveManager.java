package com.wingmann.chess.move;

import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ChessMoveManager implements MoveManager {
    private static final String nullPieces = "You can't play with a null board.";
    private static final String nullCoord = "Board coordinates must not be null.";

    @Override
    public boolean isNotTileColor(PieceState pieces, Coordinate destination, Color color) {
        Objects.requireNonNull(pieces, nullPieces);
        Objects.requireNonNull(destination, nullCoord);

        return !(pieces.getPieces().get(destination).getColor() == (color));
    }

    @Override
    public boolean tileFull(PieceState pieces, Coordinate destination) {
        Objects.requireNonNull(pieces, nullPieces);
        Objects.requireNonNull(destination, nullCoord);

        return pieces.getPieces().get(destination) != null;
    }

    @Override
    public ArrayList<Coordinate> frontFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? -1 : 1;

        return getCoordinates(pieces, piece, limit, moves, factor);
    }

    private ArrayList<Coordinate> getCoordinates(
            PieceState pieces,
            Piece piece,
            int limit,
            ArrayList<Coordinate> moves,
            int factor) {
        for (int advance = 1; advance <= limit; ++advance) {
            int change = factor * advance;
            Coordinate checkCoord = new Coordinate(piece.getFile(), piece.getRank() + change);

            if (check(pieces, piece, moves, checkCoord)) {
                return moves;
            }
        }
        return moves;
    }

    private boolean check(PieceState pieces, Piece piece, ArrayList<Coordinate> moves, Coordinate checkCoord) {
        if (Coordinate.onBoard(checkCoord)) {
            boolean occupiedTile = tileFull(pieces, checkCoord);

            if (occupiedTile && isNotTileColor(pieces, checkCoord, piece.getColor())) {
                moves.add(checkCoord);
                return true;
            } else if (occupiedTile) {
                return true;
            } else {
                moves.add(checkCoord);
            }
        }
        return false;
    }

    @Override
    public ArrayList<Coordinate> backFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? 1 : -1;

        return getCoordinates(pieces, piece, limit, moves, factor);
    }

    @Override
    public ArrayList<Coordinate> rightFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? -1 : 1;

        for (int advance = 1; advance <= limit; advance++) {
            int change = factor*advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile()+change),piece.getRank());

            if (check(pieces, piece, moves, checkCoord)) {
                return moves;
            }
        }
        return moves;
    }

    @Override
    public ArrayList<Coordinate> leftFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? 1 : -1;

        for (int advance = 1; advance <= limit; advance++) {
            int change = factor*advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile()+change),piece.getRank());
            if (check(pieces, piece, moves, checkCoord)) return moves;
        }
        return moves;
    }

    @Override
    public ArrayList<Coordinate> frontRDigFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factorV;
        int factorH;

        factorV = factorH = piece.getColor().equals(Color.BLACK) ? -1 : 1;

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV * advance;
            int changeH = factorH * advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile() + changeV),piece.getRank() + changeH);

            if (Coordinate.onBoard(checkCoord)) {
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
    public ArrayList<Coordinate> backRDigFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        boolean isBlack = piece.getColor().equals(Color.BLACK);

        int factorV = isBlack ? -1 : 1;
        int factorH = isBlack ? 1 : -1;

        for (int advance = 1; advance <= limit; ++advance) {
            int changeV = factorV * advance;
            int changeH = factorH * advance;

            Coordinate checkCoord = new Coordinate((char) (piece.getFile() + changeV),piece.getRank() + changeH);

            if (check(pieces, piece, moves, checkCoord)) {
                return moves;
            }
        }
        return moves;
    }

    @Override
    public ArrayList<Coordinate> backLDigFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factorV;
        int factorH;

        factorV = factorH = piece.getColor().equals(Color.BLACK) ? 1 : -1;

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV * advance;
            int changeH = factorH * advance;
            Coordinate checkCoord = new Coordinate((char) (piece.getFile()+changeV),piece.getRank()+changeH);
            if (check(pieces, piece, moves, checkCoord)) return moves;
        }
        return moves;
    }

    @Override
    public ArrayList<Coordinate> frontLDigFree(PieceState pieces, Piece piece, int limit) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        boolean isBlack = piece.getColor().equals(Color.BLACK);

        int factorV = isBlack ? 1 : -1;
        int factorH = isBlack ? -1 : 1;

        for (int advance = 1; advance <= limit; advance++) {
            int changeV = factorV * advance;
            int changeH = factorH * advance;

            Coordinate checkCoord = new Coordinate((char) (piece.getFile() + changeV),piece.getRank() + changeH);

            if (check(pieces, piece, moves, checkCoord)) {
                return moves;
            }
        }
        return moves;
    }

    @Override
    public ArrayList<Coordinate> frontKnight(PieceState pieces, Piece piece) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? -1 : 1;
        int sideDistance = 1;
        int changeV = 2 * factor;
        int newRank = piece.getRank() + changeV;

        Coordinate frontRight = new Coordinate((char) (piece.getFile() + sideDistance),newRank);
        Coordinate frontLeft = new Coordinate((char) (piece.getFile() - sideDistance),newRank);

        if (Coordinate.onBoard(frontLeft)) {
            boolean occupiedTile = tileFull(pieces, frontLeft);

            if (!occupiedTile || isNotTileColor(pieces, frontLeft, piece.getColor())) {
                moves.add(frontLeft);
            }
        }

        if (Coordinate.onBoard(frontRight)) {
            boolean occupiedTile = tileFull(pieces, frontRight);

            if (!occupiedTile || isNotTileColor(pieces, frontRight, piece.getColor())) {
                moves.add(frontRight);
            }
        }
        return moves;
    }

    @Override
    public ArrayList<Coordinate> backKnight(PieceState pieces, Piece piece) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? 1 : -1;
        int sideDistance = 1;
        int changeV = 2 * factor;
        int newRank = piece.getRank() + changeV;

        Coordinate backRight = new Coordinate((char) (piece.getFile() + sideDistance),newRank);
        Coordinate backLeft = new Coordinate((char) (piece.getFile() - sideDistance),newRank);

        if (Coordinate.onBoard(backLeft)) {
            boolean occupiedTile = tileFull(pieces, backLeft);

            if (!occupiedTile || isNotTileColor(pieces, backLeft, piece.getColor())) {
                moves.add(backLeft);
            }
        }

        if (Coordinate.onBoard(backRight)) {
            boolean occupiedTile = tileFull(pieces, backRight);

            if (!occupiedTile || isNotTileColor(pieces, backRight, piece.getColor())) {
                moves.add(backRight);
            }
        }
        return moves;
    }

    @Override
    public ArrayList<Coordinate> rightKnight(PieceState pieces, Piece piece) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? -1 : 1;
        int sideDistance = 1;
        int changeH = 2 * factor;
        char newFile = (char) (piece.getFile() + changeH);

        Coordinate rightTop = new Coordinate(newFile,piece.getRank() + sideDistance);
        Coordinate rightBottom = new Coordinate(newFile, piece.getRank() - sideDistance);

        if (Coordinate.onBoard(rightTop)) {
            boolean occupiedTile = tileFull(pieces, rightTop);

            if (!occupiedTile || isNotTileColor(pieces, rightTop, piece.getColor())) {
                moves.add(rightTop);
            }
        }

        if (Coordinate.onBoard(rightBottom)) {
            boolean occupiedTile = tileFull(pieces, rightBottom);

            if (!occupiedTile || isNotTileColor(pieces, rightBottom, piece.getColor())) {
                moves.add(rightBottom);
            }
        }
        return moves;
    }

    @Override
    public ArrayList<Coordinate> leftKnight(PieceState pieces, Piece piece) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int factor = piece.getColor().equals(Color.BLACK) ? 1 : -1;
        int sideDistance = 1;
        int changeH = 2 * factor;
        char newFile = (char) (piece.getFile() + changeH);

        Coordinate leftTop = new Coordinate(newFile,piece.getRank() + sideDistance);
        Coordinate leftBottom = new Coordinate(newFile, piece.getRank() - sideDistance);

        if (Coordinate.onBoard(leftBottom)) {
            boolean occupiedTile = tileFull(pieces, leftBottom);

            if (!occupiedTile || isNotTileColor(pieces, leftBottom, piece.getColor())) {
                moves.add(leftBottom);
            }
        }

        if (Coordinate.onBoard(leftTop)) {
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
