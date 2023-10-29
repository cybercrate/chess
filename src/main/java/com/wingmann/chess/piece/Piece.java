package com.wingmann.chess.piece;

import com.wingmann.chess.board.*;
import com.wingmann.chess.move.ChessMoveManager;
import com.wingmann.chess.move.MoveManager;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;

import java.util.*;

public abstract class Piece {
    private final ID name;
    private final Color color;
    private Coordinate coords;
    private final Coordinate coordinate;
    private final String pieceID;
    private Set<Coordinate> potentialMoves = new HashSet<>();
    public int dimension = BoardLimit.LAST_RANK.getRank();
    public int single = BoardLimit.FIRST_RANK.getRank();
    private boolean hasMoved = false;
    public static Piece emptyPiece = new Rook(Color.WHITE, Coordinate.emptyCoordinate);

    public static final MoveManager moveManager = new ChessMoveManager();

    public Piece(ID name, Color color, Coordinate coordinate) {
        Objects.requireNonNull(name, "The piece must be correctly identified with an ID.");
        Objects.requireNonNull(color, "The piece must be either white or black.");
        Objects.requireNonNull(coordinate, "The piece must have an origin coordinate to be correctly initiallised.");

        this.name = name;
        this.color = color;
        this.coordinate = coordinate;

        coords = coordinate;
        pieceID = String.format("*%s*%s*%s*", name, color, coordinate.getFile());
    }

    public Piece(Piece original) {
        Objects.requireNonNull(original,"You can't copy a null piece");

        this.name = original.name;
        this.color = original.color;
        this.coordinate = new Coordinate(original.coordinate);
        this.coords = new Coordinate(original.coords);
        this.pieceID = original.pieceID;
        this.potentialMoves = new HashSet<>();

        for (Coordinate coordinate : original.getPotentialMoves()) {
            this.potentialMoves.add(new Coordinate(coordinate));
        }
        this.dimension = original.dimension;
        this.single = original.single;
        this.hasMoved = original.hasMoved;
    }

    public Coordinate getCoords() {
        return coords;
    }

    public char getFile() {
        return getCoords().getFile();
    }

    public int getRank() {
        return getCoords().getRank();
    }

    public Color getColor() {
        return color;
    }

    public ID getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getPieceID() {
        return pieceID;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved() {
        hasMoved = true;
    }

    public void addMoves(ArrayList<Coordinate> someMoves) {
        potentialMoves.addAll(someMoves);
    }

    public void clearMoves() {
        potentialMoves.clear();
    }

    public Set<Coordinate> getPotentialMoves() {
        return potentialMoves;
    }

    private ArrayList<Coordinate> removeOwnCheck(PieceState pieces) {
        King potentialKing = null;
        boolean removeKingCastle = false;
        boolean removeQueenCastle = false;

        ArrayList<Coordinate> potentials = getRawMoves(pieces);

        if (potentials.isEmpty()) {
            return potentials;
        }
        Iterator<Coordinate> it = potentials.iterator();

        while (it.hasNext()) {
            Coordinate nextMove = it.next();
            PieceState p = new PieceState(pieces);
            p.pieceMove(nextMove, this.makeCopy());
            Coordinate kingPosition = p.findKing(getColor());
            Set<Coordinate> dangerMoves = p.allColouredRaws(Color.not(getColor()));

            if (dangerMoves.contains(kingPosition)) {
                if (this.getName() == ID.KING) {
                    potentialKing = (King) this;

                    if (nextMove.equals(potentialKing.getTransitionCoordinatesKingK())) {
                        it.remove();
                        removeKingCastle = true;
                    } else if (nextMove.equals(potentialKing.getTransitionCoordinatesKingQ())) {
                        it.remove();
                        removeQueenCastle = true;
                    } else {
                        it.remove();
                    }
                } else {
                    it.remove();
                }
            }
        }

        if (potentialKing != null) {
            if (removeKingCastle) {
                potentials.remove(potentialKing.getCastleCoordinatesKingK());
            }

            if (removeQueenCastle) {
                potentials.remove(potentialKing.getCastleCoordinatesKingQ());
            }
        }
        return potentials;
    }

    public void updatePotentialMoves(PieceState pieces) {
        addMoves(removeOwnCheck(pieces));
    }

    public boolean isValidMove(Coordinate destination, Color colour) {
        return getPotentialMoves().contains(destination) && (getColor() == colour);
    }

    @Override
    public String toString() {
        return String.format("%s%s", name, coords);
    }

    public String toBoardString() {
        return String.format("%s%s", name, color.toShortString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;

        return (name == piece.name)
                && (color == piece.color)
                && coordinate.equals(piece.coordinate)
                && pieceID.equals(piece.pieceID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, coordinate, pieceID);
    }

    public abstract ArrayList<Coordinate> getRawMoves(PieceState pieces);

    public abstract Piece makeCopy();
}
