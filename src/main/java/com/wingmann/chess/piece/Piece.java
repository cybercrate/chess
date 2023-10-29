package com.wingmann.chess.piece;

import com.wingmann.chess.move.ChessMoveManager;
import com.wingmann.chess.move.MoveManager;
import com.wingmann.chess.util.BoardLimit;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.*;

public abstract class Piece {
    private final ID name;
    private final Color color;
    private BoardCoordinates coords;
    private final BoardCoordinates coordinate;
    private final String pieceID;
    private Set<BoardCoordinates> potentialMoves = new HashSet<>();
    public int dimension = BoardLimit.LAST_ROW.getRow();
    public int single = BoardLimit.FIRST_ROW.getRow();
    private boolean hasMoved = false;
    public static Piece emptyPiece = new Rook(Color.WHITE, BoardCoordinates.EMPTY_COORDS);

    public static final MoveManager moveManager = new ChessMoveManager();

    public Piece(ID name, Color color, BoardCoordinates coords) {
        Objects.requireNonNull(name, "The piece must be correctly identified with an ID.");
        Objects.requireNonNull(color, "The piece must be either white or black.");
        Objects.requireNonNull(coords, "The piece must have an origin coordinate to be correctly initiallised.");

        this.name = name;
        this.color = color;
        this.coordinate = coords;

        this.coords = coords;
        pieceID = String.format("*%s*%s*%s*", name, color, coords.getColumn());
    }

    public Piece(Piece original) {
        Objects.requireNonNull(original,"You can't copy a null piece");

        this.name = original.name;
        this.color = original.color;
        this.coordinate = new BoardCoordinates(original.coordinate);
        this.coords = new BoardCoordinates(original.coords);
        this.pieceID = original.pieceID;
        this.potentialMoves = new HashSet<>();

        for (BoardCoordinates coordinate : original.getPotentialMoves()) {
            this.potentialMoves.add(new BoardCoordinates(coordinate));
        }
        this.dimension = original.dimension;
        this.single = original.single;
        this.hasMoved = original.hasMoved;
    }

    public BoardCoordinates getCoords() {
        return coords;
    }

    public char getFile() {
        return getCoords().getColumn();
    }

    public int getRank() {
        return getCoords().getRow();
    }

    public Color getColor() {
        return color;
    }

    public ID getName() {
        return name;
    }

    public BoardCoordinates getCoordinate() {
        return coordinate;
    }

    public String getPieceID() {
        return pieceID;
    }

    public void setCoords(BoardCoordinates coords) {
        this.coords = coords;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved() {
        hasMoved = true;
    }

    public void addMoves(ArrayList<BoardCoordinates> someMoves) {
        potentialMoves.addAll(someMoves);
    }

    public void clearMoves() {
        potentialMoves.clear();
    }

    public Set<BoardCoordinates> getPotentialMoves() {
        return potentialMoves;
    }

    private ArrayList<BoardCoordinates> removeOwnCheck(PieceState pieces) {
        King potentialKing = null;
        boolean removeKingCastle = false;
        boolean removeQueenCastle = false;

        ArrayList<BoardCoordinates> potentials = getRawMoves(pieces);

        if (potentials.isEmpty()) {
            return potentials;
        }
        Iterator<BoardCoordinates> it = potentials.iterator();

        while (it.hasNext()) {
            BoardCoordinates nextMove = it.next();
            PieceState p = new PieceState(pieces);
            p.pieceMove(nextMove, this.makeCopy());
            BoardCoordinates kingPosition = p.findKing(getColor());
            Set<BoardCoordinates> dangerMoves = p.allColouredRaws(Color.invert(getColor()));

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

    public boolean isValidMove(BoardCoordinates destination, Color color) {
        return getPotentialMoves().contains(destination) && (getColor() == color);
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

    public abstract ArrayList<BoardCoordinates> getRawMoves(PieceState pieces);

    public abstract Piece makeCopy();
}
