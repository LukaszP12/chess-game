package src.pieces;

import src.Alliance;
import src.board.Board;
import src.board.BoardUtils;
import src.board.Move;
import src.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Rook extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-8, -1, 1, 8};

    public Rook(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEightColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                    }
                    break;
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == 1);
    }
}