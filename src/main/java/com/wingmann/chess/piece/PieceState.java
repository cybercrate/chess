package com.wingmann.chess.piece;

import com.wingmann.chess.board.*;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;

import java.util.*;

public class PieceState implements State {
    private Map<Coordinate, Piece> pieces;
    private Map<Coordinate, Piece> previousPieces;
    private boolean isCapture;
    private ArrayList<Map<Coordinate, Piece>> gameProgress = new ArrayList<>();
    private BoardManager boardManager;

    public PieceState(BoardManager boardManager) {
        this.boardManager = boardManager;
        this.pieces = boardManager.getBoard();
        this.previousPieces = copyMap(pieces);

        gameProgress.add(copyMap(pieces));
        updatePotentials();
    }

    public PieceState(Map<Coordinate, Piece> newBoard) {
        pieces = newBoard;
        previousPieces = copyMap(pieces);
        gameProgress.add(copyMap(pieces));
        updatePotentials();
    }

    public PieceState(PieceState original) {
        this.pieces = copyMap(original.getPieces());
        this.previousPieces = original.previousPieces;
        this.isCapture = original.isCapture;
        this.gameProgress = copyArrayHash(original.getGameProgress());
        this.boardManager = original.boardManager;
    }

    @Override
    public BoardManager getBoardManager() {
        return boardManager;
    }

    private Map<Coordinate, Piece> copyMap(Map<Coordinate, Piece> original) {
        Map<Coordinate, Piece> copyMap = new HashMap<>();

        for (Coordinate key : original.keySet()) {
            Coordinate newKey = new Coordinate(key);
            Piece newPiece = original.get(key).makeCopy();
            copyMap.put(newKey,newPiece);
        }
        return copyMap;
    }

    private ArrayList<Map<Coordinate,Piece>> copyArrayHash(ArrayList<Map<Coordinate,Piece>> original) {
        ArrayList<Map<Coordinate,Piece>> copyList = new ArrayList<>();

        for (Map<Coordinate, Piece> game : original) {
            copyList.add(copyMap(game));
        }
        return copyList;
    }

    @Override
    public Map<Coordinate, Piece> getPieces() {
        return pieces;
    }

    @Override
    public void setPieces(Map<Coordinate, Piece> pieces) {
        this.pieces = pieces;
    }

    @Override
    public boolean getIsCapture() {
        return isCapture;
    }

    private void setIsCapture() {
        this.isCapture = true;
    }

    @Override
    public Map<Coordinate, Piece> getPreviousPieces() {
        return previousPieces;
    }

    private void setPreviousPieces(Map<Coordinate, Piece> previousPieces) {
        this.previousPieces = copyMap(previousPieces);
    }

    private ArrayList<Map<Coordinate, Piece>> getGameProgress() {
        return gameProgress;
    }

    private void addPiece(Coordinate coordinate, Piece piece) {
        pieces.put(coordinate,piece);
    }

    @Override
    public Coordinate findPiece(Piece piece) {
        Objects.requireNonNull(piece, "Provide an existing piece. It can't be null.");

        for (Coordinate key : pieces.keySet()) {
            if (pieces.get(key).equals(piece)) {
                return key;
            }
        }
        System.err.println(piece.getName().toFullString() +" not found.");
        return Coordinate.emptyCoordinate;
    }

    @Override
    public Coordinate findKing(Color colour) {
        for (Coordinate key : pieces.keySet()) {
            Piece potentialKing = pieces.get(key);

            if (potentialKing.getName().equals(ID.KING) && (potentialKing.getColor() == colour)) {
                return key;
            }
        }
        String pieceNotInBoard = "King not found. Assuming it isn't in board. Empty coordinate provided.";
        System.err.println(pieceNotInBoard);
        return Coordinate.emptyCoordinate;
    }

    @Override
    public Piece getPiece(Coordinate coordinate) {
        Objects.requireNonNull(coordinate, "Provide an existing coordinate. It can't be null.");

        for (Coordinate key : pieces.keySet()) {
            if (key.equals(coordinate)) {
                return pieces.get(key);
            }
        }
        System.err.println("There is no piece in this coordinate. Empty piece provided.");
        return Piece.emptyPiece;
    }

    @Override
    public Map<Coordinate, Piece> getColourPieces(Color colour) {
        Map<Coordinate,Piece> colours = new HashMap<>();

        for (Coordinate key : pieces.keySet()) {
            Piece piece = pieces.get(key);

            if (piece.getColor() == colour) {
                colours.put(key, piece);
            }
        }
        return colours;
    }

    private Set<Coordinate> allColouredPotentials (Color colour) {
        Set<Coordinate> allMoves = new HashSet<>();
        Map<Coordinate, Piece> allColoured = getColourPieces(colour);

        for (Piece piece : allColoured.values()){
            allMoves.addAll(piece.getPotentialMoves());
        }
        return allMoves;
    }

    @Override
    public Set<Coordinate> allColouredRaws(Color colour) {
        Set<Coordinate> allMoves = new HashSet<>();
        Map<Coordinate, Piece> allColoured = getColourPieces(colour);

        for (Piece piece : allColoured.values()){
            allMoves.addAll(piece.getRawMoves(this));
        }
        return allMoves;
    }

    private void updatePreviousMovePawns () {
        for (Piece potentialPawn : pieces.values()){
            if (potentialPawn.getName() == ID.PAWN) {
                Pawn pawn = (Pawn) potentialPawn;
                pawn.setPreviousCoordinate(pawn.getCoords());
            }
        }
    }

    @Override
    public boolean pieceInSameFile (Piece piece) {
        if (piece.getName() == ID.KING) {
            return false;
        }
        Map<Coordinate, Piece> coloured = getColourPieces(piece.getColor());

        for (Piece value : coloured.values()) {
            if (value.getName() == piece.getName() && value.getFile() == piece.getFile() && !value.equals(piece)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pieceInSameRank (Piece piece) {
        if (piece.getName() == ID.KING) {
            return false;
        }
        Map<Coordinate, Piece> coloured = getColourPieces(piece.getColor());

        for (Piece value : coloured.values()) {
            if (value.getName() == piece.getName() && value.getRank() == piece.getRank() && !value.equals(piece)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pieceToSameCoordinate (Coordinate coordinate, Piece piece) {
        assert piece.getPotentialMoves().contains(coordinate);

        if (piece.getName() == ID.KING) {
            return false;
        }
        Map<Coordinate, Piece> coloured = getColourPieces(piece.getColor());

        for (Piece value : coloured.values()) {
            if (value.getName() == piece.getName()
                    && value.getPotentialMoves().contains(coordinate)
                    && !value.equals(piece)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCheck(Color colour) {
        Coordinate kingPosition = findKing(colour);

        if (kingPosition.equals(Coordinate.emptyCoordinate)) {
            throw new IllegalArgumentException("There is no king in the board. This is an illegal game!");
        }
        Set<Coordinate> dangerMoves = allColouredPotentials(Color.not(colour));
        return (dangerMoves.contains(kingPosition));
    }

    @Override
    public boolean isMate(Color colour) {
        Set<Coordinate> allMoves = allColouredPotentials(colour);
        return isCheck(colour) && (allMoves.isEmpty());
    }

    @Override
    public boolean isDraw() {
        int size = gameProgress.size();

        boolean twoKings = !findKing(Color.BLACK).equals(Coordinate.emptyCoordinate)
                && !findKing(Color.WHITE).equals(Coordinate.emptyCoordinate);

        if (getPieces().size() == 2) {
            return twoKings;
        } else if (getPieces().size() == 3) {
            int counter = 0;

            for (Piece piece : this.getPieces().values()) {
                if (piece.getName() == ID.BISHOP || piece.getName() == ID.KNIGHT) {
                    ++counter;
                }
            }
            return twoKings && counter == 1;
        } else if (getPieces().size() == 4) {
            boolean sameColourBishops = isSameColourBishops();
            return twoKings && sameColourBishops;
        } else if (size >= 3) {
            for (Map<Coordinate, Piece> currentGame : gameProgress) {
                int counter = 0;

                for (Map<Coordinate, Piece> checkGame : gameProgress) {
                    if (currentGame.equals(checkGame)) {
                        counter++;
                    }
                }

                if (counter == 3) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSameColourBishops() {
        int counterB = 0;
        Bishop bishopB = null;
        int counterW = 0;
        Bishop bishopW = null;

        for (Piece piece : getPieces().values()) {
            if (piece.getName() == ID.BISHOP) {
                if (piece.getColor() == Color.BLACK) {
                    bishopB = (Bishop) piece;
                    counterB++;
                } else {
                    bishopW = (Bishop) piece;
                    counterW++;
                }
            }
        }

        return (counterB == 1)
                && (counterW == 1)
                && (bishopB.getCoordinate().getFile() != bishopW.getCoordinate().getFile());
    }

    @Override
    public boolean isStalemate(Color colour) {
        Set<Coordinate> allMoves = allColouredPotentials(Color.not(colour));
        return allMoves.isEmpty() && !isCheck(Color.not(colour));
    }

    @Override
    public void pieceMove(Coordinate coordinate, Piece piece) {
        Coordinate pieceCoord = findPiece(piece);
        addPiece(coordinate, piece);
        piece.setCoords(coordinate);
        piece.setHasMoved();
        pieces.remove(pieceCoord);
    }

    @Override
    public void makeMove(Coordinate coordinate, Piece piece) {
        if (piece.isValidMove(coordinate, piece.getColor())) {
            setPreviousPieces(this.getPieces());

            isCapture = Piece.moveManager.tileFull(this, coordinate)
                    && Piece.moveManager.isNotTileColor(this,coordinate, piece.getColor());

            if (piece.getName() == ID.KING) {
                King castleKing = (King) piece;

                if (castleKing.canCastleQueen(this)
                        && coordinate.equals(castleKing.getCastleCoordinatesKingQ())
                        && !isCheck(castleKing.getColor())) {
                    assert !findPiece(castleKing.getRookQueen()).equals(Coordinate.emptyCoordinate);
                    pieceMove(coordinate,castleKing);
                    pieceMove(castleKing.getRookQueen().getCastleCoordinatesRook(),castleKing.getRookQueen());
                } else if (castleKing.canCastleKing(this)
                        && coordinate.equals(castleKing.getCastleCoordinatesKingK())
                        && !isCheck(castleKing.getColor())) {
                    assert !findPiece(castleKing.getRookKing()).equals(Coordinate.emptyCoordinate);
                    pieceMove(coordinate,castleKing);
                    pieceMove(castleKing.getRookKing().getCastleCoordinatesRook(),castleKing.getRookKing());
                } else {
                    pieceMove(coordinate, castleKing);
                }
            } else if (piece.getName() == ID.PAWN) {
                Pawn pawn = (Pawn) piece;
                updatePreviousMovePawns();

                if (Math.abs(coordinate.getRank() - pawn.getRank()) == 2) {
                    pawn.setHasMovedTwo();
                }

                if (pawn.canPromoteBlack(coordinate) || pawn.canPromoteWhite(coordinate)) {
                    Piece toPromote;
                    toPromote = pawn.promotionQuery(coordinate);
                    Coordinate pieceCoord = findPiece(piece);
                    addPiece(coordinate, toPromote);
                    pieces.remove(pieceCoord);
                } else if (pawn.getEnPassantLeft()) {
                    Coordinate left = Piece.moveManager.leftFree(this,pawn,1).get(0);
                    setIsCapture();
                    pieces.remove(left);
                    pieceMove(coordinate,pawn);
                } else if (pawn.getEnPassantRight()) {
                    Coordinate right = Piece.moveManager.rightFree(this,pawn,1).get(0);
                    setIsCapture();
                    pieces.remove(right);
                    pieceMove(coordinate,pawn);
                } else {
                    pieceMove(coordinate, pawn);
                }
            } else {
                pieceMove(coordinate, piece);
            }
        } else {
            System.err.printf(
                    "%s to %s is an invalid move.",
                    piece.getName().toFullString(),
                    coordinate.toString());
        }
        gameProgress.add(copyMap(pieces));
        updatePotentials();
    }

    private void updatePotentials() {
        for (Piece value : pieces.values()) {
            value.clearMoves();
            value.updatePotentialMoves(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        pieces.forEach((coords, piece) -> sb
                .append(piece.getPieceID())
                .append(" is at ")
                .append(coords.toString())
                .append("\n"));

        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        PieceState pieces1 = (PieceState) object;
        return Objects.equals(pieces, pieces1.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces);
    }
}
