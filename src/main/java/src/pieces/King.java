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

public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(final Alliance pieceAlliance, final int piecePosition) {
        super(pieceAlliance, piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);

            if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEightColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
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
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == 7) || (candidateOffset == -1) || (candidateOffset == -9));
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && ((candidateOffset == -7) || (candidateOffset == 9) || (candidateOffset == 1));
    }

}
