package chessgame.gameboard.chesspieces;

import chessgame.gameboard.*;
import chessgame.ChessUtil;
import chessgame.gameboard.move.Move;
import chessgame.gameboard.move.Move.AttackMove;
import chessgame.gameboard.move.Move.NormalMove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * class which represents knight chess piece
 */

public class Knight extends Piece
{

    public Knight(final int position, final boolean whitePiece, final boolean isFirstMove)
    {
        super(position, whitePiece, isFirstMove);
    }

    /**
     * method that creates new Knight piece at new destination
     *
     * @param move contains information about the move that will be done
     * @return new Knight piece
     */

    @Override
    public Knight movePiece(Move move)
    {
        return new Knight(move.getDestination(), move.getMovedPiece().isWhite(), false);
    }

    /**
     * calculate all moves that this piece can do
     *
     * @param board contains actual state of the game board e.g. positions of all pieces
     * @return list of available moves for this piece
     */

    @Override
    public List<Move> logic(Board board)
    {
        final List<Move> legalMoves = new ArrayList<>();
        dupa:
        for (int possibleMove : ChessUtil.KNIGHT_POSSIBLE_MOVES)
        {
            int possibleDestination = this.position + possibleMove;
            if (ChessUtil.borderCheck(possibleDestination))
            {
                if (isFirstColumnPosition(this.position, possibleMove) ||
                        isSecondColumnPosition(this.position, possibleMove) ||
                        isSeventhColumnPosition(this.position, possibleMove) ||
                        isEighthColumnPosition(this.position, possibleMove))
                    continue;

                final BoardBox boardBox = board.getBox(possibleDestination);
                if (!boardBox.isBoxOccupied())
                {
                    legalMoves.add(new NormalMove(board, this, possibleDestination));
                }
                else
                {
                    final Piece pieceAtDestination = boardBox.getPiece();
                    if (this.whitePiece != pieceAtDestination.isWhite())
                    {
                        legalMoves.add(new AttackMove(board, this, possibleDestination, pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    /**
     * checks if this piece is a king
     *
     * @return {@code true} true if this is king; {@code false} otherwise
     */

    @Override
    public boolean isKing()
    {
        return false;
    }

    /**
     * checks if this piece is a rook
     *
     * @return {@code true} true if this is rook; {@code false} otherwise
     */

    @Override
    public boolean isRook()
    {
        return false;
    }

    /**
     * checks if next move won't end up out of the board
     *
     * @return {@code true} if this is first column and is moving out of the board; {@code false} otherwise
     */

    private boolean isFirstColumnPosition(int position, int possibleMove)
    {
        return ChessUtil.isItFirstColumn(position) && (possibleMove == -10 || possibleMove == -17 || possibleMove == 6 || possibleMove == 15);
    }

    /**
     * checks if next move won't end up out of the board
     *
     * @return {@code true} if this is second column and is moving out of the board; {@code false} otherwise
     */

    private boolean isSecondColumnPosition(int position, int possibleMove)
    {
        return ChessUtil.isItSecondColumn(position) && (possibleMove == -10 || possibleMove == 6);
    }

    /**
     * checks if next move won't end up out of the board
     *
     * @return {@code true} if this is seventh column and is moving out of the board; {@code false} otherwise
     */

    private boolean isSeventhColumnPosition(int position, int possibleMove)
    {
        return ChessUtil.isItSeventhColumn(position) && (possibleMove == -6 || possibleMove == 10);
    }

    /**
     * checks if next move won't end up out of the board
     *
     * @return {@code true} if this is eighth column and is moving out of the board; {@code false} otherwise
     */

    private boolean isEighthColumnPosition(int position, int possibleMove)
    {
        return ChessUtil.isItEighthColumn(position) && (possibleMove == 10 || possibleMove == 17 || possibleMove == -6 || possibleMove == -15);
    }

    /**
     * Overridden Object class toString method, converts object of this class to sing of knight
     *
     * @return String which contains knight sign
     */

    @Override
    public String toString()
    {
        return "N";
    }
}
