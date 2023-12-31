package com.wingmann.chess.piece;

import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(Color color, BoardCoordinates coords) {
        super(ID.QUEEN, color, coords);
    }

    private Queen(Queen original) {
        super(original);
    }

    @Override
    public Queen makeCopy() {
        return new Queen(this);
    }

    @Override
    public ArrayList<BoardCoordinates> getRawMoves(PieceState pieces) {
        ArrayList<BoardCoordinates> front = moveManager.frontFree(pieces,this, dimension);
        ArrayList<BoardCoordinates> back = moveManager.backFree(pieces,this, dimension);
        ArrayList<BoardCoordinates> frontRDig = moveManager.frontRDigFree(pieces, this, dimension);
        ArrayList<BoardCoordinates> backLDig = moveManager.backLDigFree(pieces, this, dimension);

        front.addAll(moveManager.rightFree(pieces,this, dimension));
        back.addAll(moveManager.leftFree(pieces,this, dimension));
        front.addAll(back);

        frontRDig.addAll(moveManager.backRDigFree(pieces, this, dimension));
        backLDig.addAll(moveManager.frontLDigFree(pieces, this, dimension));
        frontRDig.addAll(backLDig);

        front.addAll(frontRDig);

        return front;
    }
}
