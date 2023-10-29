package com.wingmann.chess.piece;

import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(Color color, Coordinate coordinate) {
        super(ID.BISHOP, color, coordinate);
    }

    private Bishop(Bishop original) {
        super(original);
    }

    @Override
    public Bishop makeCopy() {
        return new Bishop(this);
    }

    @Override
    public ArrayList<Coordinate> getRawMoves(PieceState pieces) {
        ArrayList<Coordinate> frontRDig = moveManager.frontRDigFree(pieces, this, dimension);
        ArrayList<Coordinate> backLDig = moveManager.backLDigFree(pieces, this, dimension);

        frontRDig.addAll(moveManager.backRDigFree(pieces, this, dimension));
        backLDig.addAll(moveManager.frontLDigFree(pieces, this, dimension));

        frontRDig.addAll(backLDig);

        return frontRDig;
    }
}
