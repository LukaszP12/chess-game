package src.player;

import src.Alliance;
import src.board.Board;
import src.board.Move;
import src.pieces.Piece;

import java.util.Collection;

public class WhitePlayer extends Player {

    public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves, isInCheck);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Alliance getOpponent() {
        return Alliance.BLACK;
    }
}
