package com.wingmann.chess.io;

import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.Coordinate;
import com.wingmann.chess.piece.ID;
import com.wingmann.chess.piece.King;
import com.wingmann.chess.piece.Pawn;
import com.wingmann.chess.piece.Piece;
import com.wingmann.chess.piece.PieceState;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class ChessIOManager implements IOManager {
    @Override
    public String moveString(PieceState pieces, Coordinate coordinate, Piece piece) {
        boolean isCastle = false;

        StringBuilder str = new StringBuilder();
        PieceState previousBoard = new PieceState(pieces.getPreviousPieces());
        Coordinate previousCoordinate = previousBoard.findPiece(piece);
        Piece previousPiece = previousBoard.getPiece(previousCoordinate);

        if (piece.getName() != ID.KING) {
            str.append(piece.getName().toString());
        } else {
            King king = (King) piece;
            King previousKing = (King) previousPiece;

            if (coordinate.equals(king.getCastleCoordinatesKingQ())
                    && previousKing.canCastleQueen(previousBoard)) {
                str.append("O-O-O");
                isCastle = true;
            } else if (coordinate.equals(king.getCastleCoordinatesKingK())
                    && previousKing.canCastleKing(previousBoard)) {
                str.append("O-O");
                isCastle = true;
            } else {
                str.append(piece.getName().toString());
            }
        }
        str.append(removeAmbiguous(previousBoard, coordinate, previousPiece));

        if (pieces.getIsCapture()) {
            if (piece.getName() == ID.PAWN) {
                assert piece instanceof Pawn;
                Pawn pawn = (Pawn) piece;
                str.append(pawn.getPreviousCoordinate().getFile()).append("x");
            } else {
                str.append("x");
            }
        }

        if (!isCastle) {
            str.append(coordinate.toString());
        }

        if (piece.getName() == ID.PAWN) {
            Pawn pawn = (Pawn) piece;

            if (pawn.canPromoteBlack(coordinate) || pawn.canPromoteWhite(coordinate)) {
                str.append("=").append(pawn.getPromotedPiece().getName().toString());
            }
        }

        if (pieces.isMate(Color.not(piece.getColor()))) {
            str.append("#");
        } else if (pieces.isCheck(Color.not(piece.getColor()))) {
            str.append("+");
        }
        return str.toString();
    }

    private String removeAmbiguous(PieceState pieces, Coordinate coordinate, Piece piece) {
        if (pieces.pieceToSameCoordinate(coordinate, piece)) {
            if (pieces.pieceInSameRank(piece)) {
                return String.valueOf(piece.getFile());
            } else if (pieces.pieceInSameFile(piece)) {
                return String.valueOf(piece.getRank());
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    @Override
    public Path fileQuery(Scanner in) {
        System.out.print("Enter file path: ");
        String filePath = in.nextLine();
        return Paths.get(filePath);
    }

    @Override
    public boolean saveGame(String game, Path saveFile) {
        Objects.requireNonNull(game,"You can't have a null game!");
        Objects.requireNonNull(saveFile,"You can't save a game to a null path!");

        String path = String.valueOf(saveFile);
        File gameFile = new File(path);

        if (!gameFile.exists()) {
            try {
                FileWriter writer = new FileWriter(path);
                writer.write(game);
                writer.close();
                return true;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return false;
    }
}
