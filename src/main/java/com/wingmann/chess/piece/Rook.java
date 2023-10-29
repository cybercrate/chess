package com.wingmann.chess.piece;

import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.ArrayList;

public class Rook extends Piece implements RookPiece {
    private BoardCoordinates castleCoordinatesRook;

    public Rook(Color color, BoardCoordinates coords) {
        super(ID.ROOK, color, coords);
    }

    private Rook(Rook original) {
        super(original);
    }

    @Override
    public Rook makeCopy() {
        return new Rook(this);
    }

    @Override
    public ArrayList<BoardCoordinates> getRawMoves(PieceState pieces) {
        ArrayList<BoardCoordinates> front = moveManager.frontFree(pieces,this, dimension);
        ArrayList<BoardCoordinates> right = moveManager.rightFree(pieces,this, dimension);
        ArrayList<BoardCoordinates> back = moveManager.backFree(pieces,this, dimension);
        ArrayList<BoardCoordinates> left = moveManager.leftFree(pieces,this, dimension);

        front.addAll(right);
        back.addAll(left);
        front.addAll(back);

        return front;
    }

    @Override
    public BoardCoordinates getCastleCoordinatesRook() {
        return castleCoordinatesRook;
    }

    @Override
    public void setCastleCoordinatesRook(BoardCoordinates castleCoordinatesRook) {
        this.castleCoordinatesRook = castleCoordinatesRook;
    }
}
