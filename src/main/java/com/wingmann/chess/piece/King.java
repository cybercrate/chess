package com.wingmann.chess.piece;

import com.wingmann.chess.util.BoardLimit;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.ArrayList;
import java.util.Map;

public class King extends Piece implements KingPiece {
    private BoardCoordinates castleCoordinatesKingK;
    private BoardCoordinates castleCoordinatesKingQ;
    private BoardCoordinates transitionCoordinatesKingK;
    private BoardCoordinates transitionCoordinatesKingQ;
    private Rook rookKing;
    private Rook rookQueen;

    public King(Color colour, BoardCoordinates coordinate) {
        super(ID.KING, colour, coordinate);
    }

    private King(King original) {
        super(original);
    }

    @Override
    public King makeCopy() {
        return new King(this);
    }

    @Override
    public ArrayList<BoardCoordinates> getRawMoves(PieceState pieces) {
        ArrayList<BoardCoordinates> front = moveManager.frontFree(pieces,this, single);
        ArrayList<BoardCoordinates> back = moveManager.backFree(pieces,this, single);
        ArrayList<BoardCoordinates> frontRDig = moveManager.frontRDigFree(pieces, this, single);
        ArrayList<BoardCoordinates> backLDig = moveManager.backLDigFree(pieces, this, single);

        front.addAll(moveManager.rightFree(pieces,this, single));
        back.addAll(moveManager.leftFree(pieces,this, single));
        front.addAll(back);

        frontRDig.addAll(moveManager.backRDigFree(pieces, this, single));
        backLDig.addAll(moveManager.frontLDigFree(pieces, this, single));
        frontRDig.addAll(backLDig);

        front.addAll(frontRDig);

        if (canCastleKing(pieces)) {
            front.add(castleCoordinatesKingK);
        }

        if (canCastleQueen(pieces)) {
            front.add(castleCoordinatesKingQ);
        }
        return front;
    }

    @Override
    public BoardCoordinates getCastleCoordinatesKingK() {
        return castleCoordinatesKingK;
    }

    @Override
    public BoardCoordinates getCastleCoordinatesKingQ() {
        return castleCoordinatesKingQ;
    }

    @Override
    public BoardCoordinates getTransitionCoordinatesKingK() {
        return transitionCoordinatesKingK;
    }

    @Override
    public BoardCoordinates getTransitionCoordinatesKingQ() {
        return transitionCoordinatesKingQ;
    }

    @Override
    public Rook getRookKing() {
        return rookKing;
    }

    @Override
    public Rook getRookQueen() {
        return rookQueen;
    }

    @Override
    public boolean canCastleKing (PieceState pieces) {
        if (pieces.isCheck(getColor())) {
            return false;
        }
        Map<BoardCoordinates, Piece> coloredPieces = pieces.getColorPieces(getColor());

        for (Piece value : coloredPieces.values()) {
            if ((value.getName() == ID.ROOK) && (value.getFile() == BoardLimit.LAST_COLUMN.getColumn())) {
                rookKing = (Rook) value;
            }
        }
        int distanceRookKing = 2;
        ArrayList<BoardCoordinates> castleCoords;

        if (getColor() == Color.BLACK) {
            castleCoords = moveManager.leftFree(pieces, this, dimension);
        } else {
            castleCoords = moveManager.rightFree(pieces, this, dimension);
        }

        boolean isSpace = (castleCoords.size() == distanceRookKing);

        boolean canCastle = (rookKing != null)
                && !rookKing.getHasMoved()
                && !getHasMoved()
                && isSpace;

        if (canCastle) {
            castleCoordinatesKingK = castleCoords.get(1);
            transitionCoordinatesKingK = castleCoords.get(0);
            rookKing.setCastleCoordinatesRook(castleCoords.get(0));

            return true;
        }
        return false;
    }

    @Override
    public boolean canCastleQueen (PieceState pieces) {
        if (pieces.isCheck(getColor())) {
            return false;
        }
        Map<BoardCoordinates,Piece> colouredPieces = pieces.getColorPieces(getColor());

        for (Piece value : colouredPieces.values()) {
            if ((value.getName() == ID.ROOK) && (value.getFile() == BoardLimit.FIRST_COLUMN.getColumn())) {
                rookQueen = (Rook) value;
            }
        }
        int distanceRookQueen = 3;

        ArrayList<BoardCoordinates> castleCoords = (getColor() == Color.WHITE)
                ? moveManager.leftFree(pieces, this, dimension)
                : moveManager.rightFree(pieces, this, dimension);

        boolean isSpace = (castleCoords.size() == distanceRookQueen);

        boolean canCastle = (rookQueen != null)
                && !rookQueen.getHasMoved()
                && !getHasMoved()
                && isSpace;

        if (canCastle) {
            castleCoordinatesKingQ = castleCoords.get(1);
            transitionCoordinatesKingQ = castleCoords.get(0);
            rookQueen.setCastleCoordinatesRook(castleCoords.get(0));
            return true;
        }
        return false;
    }
}