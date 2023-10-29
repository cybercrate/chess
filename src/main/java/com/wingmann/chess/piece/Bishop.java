package com.wingmann.chess.piece;

import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(Color color, BoardCoordinates coords) {
        super(ID.BISHOP, color, coords);
    }

    private Bishop(Bishop original) {
        super(original);
    }

    @Override
    public Bishop makeCopy() {
        return new Bishop(this);
    }

    @Override
    public ArrayList<BoardCoordinates> getRawMoves(PieceState pieces) {
        ArrayList<BoardCoordinates> frontRDig = moveManager.frontRDigFree(pieces, this, dimension);
        ArrayList<BoardCoordinates> backLDig = moveManager.backLDigFree(pieces, this, dimension);

        frontRDig.addAll(moveManager.backRDigFree(pieces, this, dimension));
        backLDig.addAll(moveManager.frontLDigFree(pieces, this, dimension));

        frontRDig.addAll(backLDig);

        return frontRDig;
    }
}
