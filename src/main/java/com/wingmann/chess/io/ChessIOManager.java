package com.wingmann.chess.io;

import com.wingmann.chess.util.Color;
import com.wingmann.chess.util.BoardCoordinates;
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
    public String moveString(PieceState state, BoardCoordinates coords, Piece piece) {
        boolean isCastle = false;

        StringBuilder sb = new StringBuilder();
        PieceState previousBoard = new PieceState(state.getPreviousPieces());

        BoardCoordinates previousCoords = previousBoard.findPiece(piece);
        Piece previousPiece = previousBoard.getPiece(previousCoords);

        if (piece.getName() != ID.KING) {
            sb.append(piece.getName().toString());
        } else {
            King king = (King) piece;
            King previousKing = (King) previousPiece;

            if (coords.equals(king.getCastleCoordinatesKingQ()) && previousKing.canCastleQueen(previousBoard)) {
                sb.append("O-O-O");
                isCastle = true;
            } else if (coords.equals(king.getCastleCoordinatesKingK()) && previousKing.canCastleKing(previousBoard)) {
                sb.append("O-O");
                isCastle = true;
            } else {
                sb.append(piece.getName().toString());
            }
        }
        sb.append(removeAmbiguous(previousBoard, coords, previousPiece));

        if (state.isCapture()) {
            if (piece.getName() == ID.PAWN) {
                assert piece instanceof Pawn;
                Pawn pawn = (Pawn) piece;

                sb.append(pawn.getPreviousCoordinate().getColumn())
                        .append("x");
            } else {
                sb.append("x");
            }
        }

        if (!isCastle) {
            sb.append(coords.toString());
        }

        if (piece.getName() == ID.PAWN) {
            Pawn pawn = (Pawn) piece;

            if (pawn.canPromoteBlack(coords) || pawn.canPromoteWhite(coords)) {
                sb.append("=")
                        .append(pawn.getPromotedPiece().getName().toString());
            }
        }

        if (state.isMate(Color.invert(piece.getColor()))) {
            sb.append("#");
        } else if (state.isCheck(Color.invert(piece.getColor()))) {
            sb.append("+");
        }
        return sb.toString();
    }

    private String removeAmbiguous(PieceState state, BoardCoordinates coords, Piece piece) {
        if (state.pieceToSameCoordinate(coords, piece)) {
            if (state.pieceInSameRow(piece)) {
                return String.valueOf(piece.getFile());
            } else if (state.pieceInSameColumn(piece)) {
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
    public boolean save(String game, Path file) {
        Objects.requireNonNull(game,"You cannot have a null game!");
        Objects.requireNonNull(file,"You cannot save a game to a null path!");

        String path = String.valueOf(file);
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
