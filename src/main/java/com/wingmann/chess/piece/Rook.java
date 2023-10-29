package com.wingmann.chess.piece;

import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;

import java.util.ArrayList;

public class Rook extends Piece implements RookPiece {
    private Coordinate castleCoordinatesRook;

    public Rook(Color color, Coordinate coordinate) {
        super(ID.ROOK, color, coordinate);
    }

    private Rook(Rook original) {
        super(original);
    }

    @Override
    public Rook makeCopy() {
        return new Rook(this);
    }

    @Override
    public ArrayList<Coordinate> getRawMoves(PieceState pieces) {
        ArrayList<Coordinate> front = moveManager.frontFree(pieces,this, dimension);
        ArrayList<Coordinate> right = moveManager.rightFree(pieces,this, dimension);
        ArrayList<Coordinate> back = moveManager.backFree(pieces,this, dimension);
        ArrayList<Coordinate> left = moveManager.leftFree(pieces,this, dimension);

        front.addAll(right);
        back.addAll(left);
        front.addAll(back);

        return front;
    }

    @Override
    public Coordinate getCastleCoordinatesRook() {
        return castleCoordinatesRook;
    }

    @Override
    public void setCastleCoordinatesRook(Coordinate castleCoordinatesRook) {
        this.castleCoordinatesRook = castleCoordinatesRook;
    }
}
