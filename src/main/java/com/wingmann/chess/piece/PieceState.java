package com.wingmann.chess.piece;

import com.wingmann.chess.board.*;
import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;

import java.util.*;

public class PieceState implements State {
    private Map<BoardCoordinates, Piece> pieces;
    private Map<BoardCoordinates, Piece> previousPieces;
    private boolean isCapture;
    private ArrayList<Map<BoardCoordinates, Piece>> gameProgress = new ArrayList<>();
    private BoardManager boardManager;

    public PieceState(BoardManager boardManager) {
        this.boardManager = boardManager;
        this.pieces = boardManager.get();
        this.previousPieces = copyMap(pieces);

        gameProgress.add(copyMap(pieces));
        updatePotentials();
    }

    public PieceState(Map<BoardCoordinates, Piece> newBoard) {
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

    private Map<BoardCoordinates, Piece> copyMap(Map<BoardCoordinates, Piece> original) {
        Map<BoardCoordinates, Piece> copyMap = new HashMap<>();

        for (BoardCoordinates key : original.keySet()) {
            BoardCoordinates newKey = new BoardCoordinates(key);
            Piece newPiece = original.get(key).makeCopy();
            copyMap.put(newKey,newPiece);
        }
        return copyMap;
    }

    private ArrayList<Map<BoardCoordinates,Piece>> copyArrayHash(ArrayList<Map<BoardCoordinates, Piece>> original) {
        ArrayList<Map<BoardCoordinates,Piece>> copyList = new ArrayList<>();

        for (Map<BoardCoordinates, Piece> game : original) {
            copyList.add(copyMap(game));
        }
        return copyList;
    }

    @Override
    public Map<BoardCoordinates, Piece> getPieces() {
        return pieces;
    }

    @Override
    public void setPieces(Map<BoardCoordinates, Piece> pieces) {
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
    public Map<BoardCoordinates, Piece> getPreviousPieces() {
        return previousPieces;
    }

    private void setPreviousPieces(Map<BoardCoordinates, Piece> previousPieces) {
        this.previousPieces = copyMap(previousPieces);
    }

    private ArrayList<Map<BoardCoordinates, Piece>> getGameProgress() {
        return gameProgress;
    }

    private void addPiece(BoardCoordinates coords, Piece piece) {
        pieces.put(coords,piece);
    }

    @Override
    public BoardCoordinates findPiece(Piece piece) {
        Objects.requireNonNull(piece, "Provide an existing piece. It can't be null.");

        for (BoardCoordinates key : pieces.keySet()) {
            if (pieces.get(key).equals(piece)) {
                return key;
            }
        }
        System.err.println(piece.getName().toFullString() +" not found.");
        return BoardCoordinates.EMPTY_COORDS;
    }

    @Override
    public BoardCoordinates findKing(Color color) {
        for (BoardCoordinates key : pieces.keySet()) {
            Piece potentialKing = pieces.get(key);

            if (potentialKing.getName().equals(ID.KING) && (potentialKing.getColor() == color)) {
                return key;
            }
        }
        String pieceNotInBoard = "King not found. Assuming it isn't in board. Empty coordinate provided.";
        System.err.println(pieceNotInBoard);
        return BoardCoordinates.EMPTY_COORDS;
    }

    @Override
    public Piece getPiece(BoardCoordinates coords) {
        Objects.requireNonNull(coords, "Provide an existing coordinate. It can't be null.");

        for (BoardCoordinates key : pieces.keySet()) {
            if (key.equals(coords)) {
                return pieces.get(key);
            }
        }
        System.err.println("There is no piece in this coordinate. Empty piece provided.");
        return Piece.emptyPiece;
    }

    @Override
    public Map<BoardCoordinates, Piece> getColourPieces(Color color) {
        Map<BoardCoordinates,Piece> colours = new HashMap<>();

        for (BoardCoordinates key : pieces.keySet()) {
            Piece piece = pieces.get(key);

            if (piece.getColor() == color) {
                colours.put(key, piece);
            }
        }
        return colours;
    }

    private Set<BoardCoordinates> allColouredPotentials (Color color) {
        Set<BoardCoordinates> allMoves = new HashSet<>();
        Map<BoardCoordinates, Piece> allColoured = getColourPieces(color);

        for (Piece piece : allColoured.values()){
            allMoves.addAll(piece.getPotentialMoves());
        }
        return allMoves;
    }

    @Override
    public Set<BoardCoordinates> allColouredRaws(Color color) {
        Set<BoardCoordinates> allMoves = new HashSet<>();
        Map<BoardCoordinates, Piece> allColoured = getColourPieces(color);

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
        Map<BoardCoordinates, Piece> coloured = getColourPieces(piece.getColor());

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
        Map<BoardCoordinates, Piece> coloured = getColourPieces(piece.getColor());

        for (Piece value : coloured.values()) {
            if (value.getName() == piece.getName() && value.getRank() == piece.getRank() && !value.equals(piece)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pieceToSameCoordinate (BoardCoordinates coords, Piece piece) {
        assert piece.getPotentialMoves().contains(coords);

        if (piece.getName() == ID.KING) {
            return false;
        }
        Map<BoardCoordinates, Piece> coloured = getColourPieces(piece.getColor());

        for (Piece value : coloured.values()) {
            if (value.getName() == piece.getName()
                    && value.getPotentialMoves().contains(coords)
                    && !value.equals(piece)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCheck(Color color) {
        BoardCoordinates kingPosition = findKing(color);

        if (kingPosition.equals(BoardCoordinates.EMPTY_COORDS)) {
            throw new IllegalArgumentException("There is no king in the board. This is an illegal game!");
        }
        Set<BoardCoordinates> dangerMoves = allColouredPotentials(Color.not(color));
        return (dangerMoves.contains(kingPosition));
    }

    @Override
    public boolean isMate(Color color) {
        Set<BoardCoordinates> allMoves = allColouredPotentials(color);
        return isCheck(color) && (allMoves.isEmpty());
    }

    @Override
    public boolean isDraw() {
        int size = gameProgress.size();

        boolean twoKings = !findKing(Color.BLACK).equals(BoardCoordinates.EMPTY_COORDS)
                && !findKing(Color.WHITE).equals(BoardCoordinates.EMPTY_COORDS);

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
            for (Map<BoardCoordinates, Piece> currentGame : gameProgress) {
                int counter = 0;

                for (Map<BoardCoordinates, Piece> checkGame : gameProgress) {
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
                && (bishopB.getCoordinate().getColumn() != bishopW.getCoordinate().getColumn());
    }

    @Override
    public boolean isStalemate(Color color) {
        Set<BoardCoordinates> allMoves = allColouredPotentials(Color.not(color));
        return allMoves.isEmpty() && !isCheck(Color.not(color));
    }

    @Override
    public void pieceMove(BoardCoordinates coords, Piece piece) {
        BoardCoordinates pieceCoord = findPiece(piece);
        addPiece(coords, piece);
        piece.setCoords(coords);
        piece.setHasMoved();
        pieces.remove(pieceCoord);
    }

    @Override
    public void makeMove(BoardCoordinates coords, Piece piece) {
        if (piece.isValidMove(coords, piece.getColor())) {
            setPreviousPieces(this.getPieces());

            isCapture = Piece.moveManager.tileFull(this, coords)
                    && Piece.moveManager.isNotTileColor(this, coords, piece.getColor());

            if (piece.getName() == ID.KING) {
                King castleKing = (King) piece;

                if (castleKing.canCastleQueen(this)
                        && coords.equals(castleKing.getCastleCoordinatesKingQ())
                        && !isCheck(castleKing.getColor())) {
                    assert !findPiece(castleKing.getRookQueen()).equals(BoardCoordinates.EMPTY_COORDS);
                    pieceMove(coords,castleKing);
                    pieceMove(castleKing.getRookQueen().getCastleCoordinatesRook(),castleKing.getRookQueen());
                } else if (castleKing.canCastleKing(this)
                        && coords.equals(castleKing.getCastleCoordinatesKingK())
                        && !isCheck(castleKing.getColor())) {
                    assert !findPiece(castleKing.getRookKing()).equals(BoardCoordinates.EMPTY_COORDS);
                    pieceMove(coords,castleKing);
                    pieceMove(castleKing.getRookKing().getCastleCoordinatesRook(),castleKing.getRookKing());
                } else {
                    pieceMove(coords, castleKing);
                }
            } else if (piece.getName() == ID.PAWN) {
                Pawn pawn = (Pawn) piece;
                updatePreviousMovePawns();

                if (Math.abs(coords.getRow() - pawn.getRank()) == 2) {
                    pawn.setHasMovedTwo();
                }

                if (pawn.canPromoteBlack(coords) || pawn.canPromoteWhite(coords)) {
                    Piece toPromote;
                    toPromote = pawn.promotionQuery(coords);
                    BoardCoordinates pieceCoord = findPiece(piece);
                    addPiece(coords, toPromote);
                    pieces.remove(pieceCoord);
                } else if (pawn.getEnPassantLeft()) {
                    BoardCoordinates left = Piece.moveManager.leftFree(this,pawn,1).get(0);
                    setIsCapture();
                    pieces.remove(left);
                    pieceMove(coords,pawn);
                } else if (pawn.getEnPassantRight()) {
                    BoardCoordinates right = Piece.moveManager.rightFree(this,pawn,1).get(0);
                    setIsCapture();
                    pieces.remove(right);
                    pieceMove(coords,pawn);
                } else {
                    pieceMove(coords, pawn);
                }
            } else {
                pieceMove(coords, piece);
            }
        } else {
            System.err.printf(
                    "%s to %s is an invalid move.",
                    piece.getName().toFullString(),
                    coords.toString());
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
