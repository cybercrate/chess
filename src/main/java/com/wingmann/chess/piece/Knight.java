package com.wingmann.chess.piece;

import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(Color color, Coordinate coordinate) {
        super(ID.KNIGHT, color, coordinate);
    }

    private Knight(Knight original) {
        super(original);
    }

    @Override
    public Knight makeCopy() {
        return new Knight(this);
    }

    @Override
    public ArrayList<Coordinate> getRawMoves(PieceState pieces) {
        ArrayList<Coordinate> front = moveManager.frontKnight(pieces,this);
        ArrayList<Coordinate> back = moveManager.rightKnight(pieces,this);

        front.addAll(moveManager.backKnight(pieces,this));
        back.addAll(moveManager.leftKnight(pieces,this));
        front.addAll(back);

        return front;
    }
}
