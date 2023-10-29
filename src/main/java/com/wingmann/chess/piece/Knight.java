package com.wingmann.chess.piece;

import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(Color color, BoardCoordinates coords) {
        super(ID.KNIGHT, color, coords);
    }

    private Knight(Knight original) {
        super(original);
    }

    @Override
    public Knight makeCopy() {
        return new Knight(this);
    }

    @Override
    public ArrayList<BoardCoordinates> getRawMoves(PieceState pieces) {
        ArrayList<BoardCoordinates> front = moveManager.frontKnight(pieces,this);
        ArrayList<BoardCoordinates> back = moveManager.rightKnight(pieces,this);

        front.addAll(moveManager.backKnight(pieces,this));
        back.addAll(moveManager.leftKnight(pieces,this));
        front.addAll(back);

        return front;
    }
}
