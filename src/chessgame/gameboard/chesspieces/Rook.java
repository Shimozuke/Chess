package chessgame.gameboard.chesspieces;

import chessgame.gameboard.*;
import chessgame.ChessUtil;
import chessgame.gameboard.move.Move;
import chessgame.gameboard.move.Move.AttackMove;
import chessgame.gameboard.move.Move.NormalMove;

import java.util.ArrayList;
import java.util.List;

/**
 * class which represents Rook chess piece
 */

public class Rook extends Piece
{

    public Rook(final int position, final boolean whitePiece, final boolean isFirstMove)
    {
        super(position, whitePiece, isFirstMove);
    }

    /**
     * method that creates new Rook piece at new destination
     *
     * @param move contains information about the move that will be done
     * @return new Rook piece
     */

    @Override
    public Rook movePiece(Move move)
    {
        return new Rook(move.getDestination(), move.getMovedPiece().isWhite(), false);
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

        for (int possibleMoveVector : ChessUtil.ROOK_POSSIBLE_MOVES_VECTOR)
        {
            int possibleDestination = this.position;
            while (ChessUtil.borderCheck(possibleDestination))
            {
                if (isFirstColumnPosition(possibleDestination, possibleMoveVector) || isEighthColumnPosition(possibleDestination, possibleMoveVector))
                    break;

                possibleDestination += possibleMoveVector;
                if (ChessUtil.borderCheck(possibleDestination))
                {
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
                        break;
                    }
                }
            }
        }
        return legalMoves;
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
        return true;
    }

    /**
     * checks if next move won't end up out of the board
     *
     * @return {@code true} if this is first column and is moving out of the board; {@code false} otherwise
     */

    private boolean isFirstColumnPosition(int position, int possibleMoveVector)
    {
        return ChessUtil.isItFirstColumn(position) && (possibleMoveVector == -1);
    }

    /**
     * checks if next move won't end up out of the board
     *
     * @return {@code true} if this is eighth column and is moving out of the board; {@code false} otherwise
     */

    private boolean isEighthColumnPosition(int position, int possibleMoveVector)
    {
        return ChessUtil.isItEighthColumn(position) && (possibleMoveVector == 1);
    }

    /**
     * Overridden Object class toString method, converts object of this class to sing of rook
     *
     * @return String which contains rook sign
     */

    @Override
    public String toString()
    {
        return "R";
    }
}