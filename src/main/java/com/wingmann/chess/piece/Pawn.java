package com.wingmann.chess.piece;

import com.wingmann.chess.util.BoardLimit;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Pawn extends Piece implements PawnPiece {
    private boolean hasMovedTwo = false;
    private boolean enPassantLeft = false;
    private boolean enPassantRight = false;
    private BoardCoordinates previousCoordinate = new BoardCoordinates();
    private Piece promotedPiece;

    public Pawn(Color colour, BoardCoordinates coordinate) {
        super(ID.PAWN, colour, coordinate);
    }

    private Pawn(Pawn original) {
        super(original);
    }

    @Override
    public Pawn makeCopy() {
        return new Pawn(this);
    }

    @Override
    public ArrayList<BoardCoordinates> getRawMoves(PieceState pieces) {
        ArrayList<BoardCoordinates> pawnMoves = new ArrayList<>();

        if (canEatLeftDig(pieces)) {
            pawnMoves.addAll(moveManager.frontLDigFree(pieces, this, 1));
        }
        pawnMoves.addAll(pawnForward(pieces));

        if (canEatRightDig(pieces)) {
            pawnMoves.addAll(moveManager.frontRDigFree(pieces, this, 1));
        }
        pawnMoves.addAll(enPassant(pieces));
        return pawnMoves;
    }

    @Override
    public void setPreviousCoordinate(BoardCoordinates previous) {
        previousCoordinate = previous;
    }

    @Override
    public BoardCoordinates getPreviousCoordinate() {
        return previousCoordinate;
    }

    @Override
    public void setHasMovedTwo() {
        hasMovedTwo = true;
    }

    private boolean getHasMovedTwo() {
        return hasMovedTwo;
    }

    @Override
    public boolean getEnPassantLeft() {
        return enPassantLeft;
    }

    @Override
    public boolean getEnPassantRight() {
        return enPassantRight;
    }

    private boolean canEatLeftDig(PieceState pieces) {
        boolean isBlack = getColor().equals(Color.BLACK);
        int factorV = isBlack ? -1 : 1;
        int factorH = isBlack ? 1 : -1;

        char newFile = (char) (getFile() + factorH);
        int newRank = getRank() + factorV;
        BoardCoordinates leftDig = new BoardCoordinates(newFile, newRank);

        return moveManager.tileFull(pieces, leftDig)
                && moveManager.isNotTileColor(pieces, leftDig, getColor());
    }

    private boolean canEatRightDig(PieceState pieces) {
        int factorV;
        int factorH;

        factorV = factorH = getColor().equals(Color.BLACK) ? -1 : 1;

        char newFile = (char) (getFile() + factorH);
        int newRank = getRank() + factorV;
        BoardCoordinates rightDig = new BoardCoordinates(newFile, newRank);

        return moveManager.tileFull(pieces, rightDig)
                && moveManager.isNotTileColor(pieces, rightDig, getColor());
    }

    private ArrayList<BoardCoordinates> pawnForward(PieceState pieces) {
        ArrayList<BoardCoordinates> potentialForward = moveManager.frontFree(pieces, this, 2);
        ArrayList<BoardCoordinates> actualForward = new ArrayList<>();

        if (!potentialForward.isEmpty()) {
            BoardCoordinates front1 = potentialForward.get(0);
            BoardCoordinates front2 = BoardCoordinates.EMPTY_COORDS;

            if (potentialForward.size() == 2) {
                front2 = potentialForward.get(1);
            }

            if (moveManager.tileFull(pieces, front1)) {
                return actualForward;
            } else {
                actualForward.add(front1);
            }

            if (pieces.findPiece(this).equals(getCoordinate()) && (front2 != BoardCoordinates.EMPTY_COORDS)) {
                if (moveManager.tileFull(pieces, front2)) {
                    return actualForward;
                } else {
                    actualForward.add(front2);
                }
            }
        }
        return actualForward;
    }

    private ArrayList <BoardCoordinates> enPassant (PieceState pieces) {
        ArrayList<BoardCoordinates> enPassantMoves = new ArrayList<>();
        ArrayList<BoardCoordinates> left = moveManager.leftFree(pieces,this,1);
        ArrayList<BoardCoordinates> right = moveManager.rightFree(pieces,this,1);

        if (left.size() == 1) {
            BoardCoordinates leftTile = left.get(0);

            boolean correctPiece = moveManager.tileFull(pieces,leftTile)
                    && pieces.getPiece(leftTile).getName() == ID.PAWN
                    && pieces.getPiece(leftTile).getColor() == Color.invert(getColor());

            if (correctPiece) {
                Pawn potentialPawn = (Pawn) pieces.getPiece(leftTile);

                boolean correctPassantContext = potentialPawn.getHasMovedTwo()
                        && potentialPawn.getPreviousCoordinate().equals(potentialPawn.getCoordinate())
                        && !potentialPawn.getPreviousCoordinate().equals(potentialPawn.getCoords());

                if (correctPassantContext) {
                    enPassantMoves.addAll(moveManager.frontLDigFree(pieces, this, 1));
                    enPassantLeft = true;
                }
            }
        }

        if (right.size() == 1) {
            BoardCoordinates rightTile = right.get(0);

            boolean correctPiece = moveManager.tileFull(pieces,rightTile)
                    && pieces.getPiece(rightTile).getName() == ID.PAWN
                    && pieces.getPiece(rightTile).getColor() == Color.invert(getColor());

            if (correctPiece) {
                Pawn potentialPawn = (Pawn) pieces.getPiece(rightTile);

                boolean correctPassantContext = potentialPawn.getHasMovedTwo()
                        && potentialPawn.getPreviousCoordinate().equals(potentialPawn.getCoordinate())
                        && !potentialPawn.getPreviousCoordinate().equals(potentialPawn.getCoords());

                if (correctPassantContext) {
                    enPassantMoves.addAll(moveManager.frontRDigFree(pieces, this, 1));
                    enPassantRight = true;
                }
            }
        }

        return enPassantMoves;
    }

    @Override
    public Piece promotionQuery(BoardCoordinates promotionSquare) {
        Piece promotee = null;
        Scanner scanner = new Scanner(System.in);
        boolean correctInput = false;

        System.out.print("Your pawn can be promoted. To what piece? ");

        while (!correctInput) {
            System.out.println("""
                    You can choose between:\s
                    · Queen (Q)\s
                    · Rook (R)\s
                    · Bishop (B)\s
                    · Knight (N)""");

            String promoted = scanner.next();

            switch (promoted) {
                case "Queen", "Q" -> {
                    promotee = new Queen(getColor(), promotionSquare);
                    correctInput = true;
                }
                case "Rook", "R" -> {
                    promotee = new Rook(getColor(), promotionSquare);
                    correctInput = true;
                }
                case "Bishop", "B" -> {
                    promotee = new Bishop(getColor(), promotionSquare);
                    correctInput = true;
                }
                case "Knight", "N" -> {
                    promotee = new Knight(getColor(), promotionSquare);
                    correctInput = true;
                }
                default -> System.out.println("Incorrect piece entered. Please try again");
            }
        }
        scanner.close();

        Objects.requireNonNull(promotee);
        promotedPiece = promotee;

        return promotee;
    }

    @Override
    public boolean canPromoteBlack(BoardCoordinates coords) {
        return (getColor() == Color.BLACK)
                && (coords.getRow() == BoardLimit.FIRST_ROW.getRow());
    }

    @Override
    public boolean canPromoteWhite(BoardCoordinates coords) {
        return (getColor() == Color.WHITE)
                && (coords.getRow() == BoardLimit.LAST_ROW.getRow());
    }

    @Override
    public Piece getPromotedPiece() {
        return promotedPiece;
    }
}
