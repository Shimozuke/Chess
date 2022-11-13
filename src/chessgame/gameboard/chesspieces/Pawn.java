package chessgame.gameboard.chesspieces;

import chessgame.gameboard.Board;
import chessgame.gameboard.move.Move;
import chessgame.ChessUtil;
import chessgame.gameboard.move.Move.AttackMove;
import chessgame.gameboard.move.Move.NormalMove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static chessgame.gameboard.move.Move.*;

/**
 * class which represents Pawn chess piece
 */

public class Pawn extends Piece
{
    private final int direction;

    public Pawn(final int position, final boolean whitePiece, final boolean isFirstMove)
    {
        super(position, whitePiece, isFirstMove);
        this.direction = isWhite() ? -1 : 1;
    }

    /**
     * method that creates new Pawn piece at new destination
     *
     * @param move contains information about the move that will be done
     * @return new Pawn piece
     */

    @Override
    public Pawn movePiece(Move move)
    {
        return new Pawn(move.getDestination(), move.getMovedPiece().isWhite(), false);
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

        for (int possibleMoveVector : ChessUtil.PAWN_POSSIBLE_MOVES_VECTOR)
        {
            int possibleDestination = this.position + possibleMoveVector * direction;
            if (!ChessUtil.borderCheck(possibleDestination))
                continue;
            if (possibleMoveVector == 8 && !board.getBox(possibleDestination).isBoxOccupied())
            {
                legalMoves.add(new PawnMove(board, this, possibleDestination));
            }
            else if(possibleMoveVector == 16 && isFirstMove() &&
                    ((ChessUtil.isItSecondRow(position) && !isWhite()) ||
                    (ChessUtil.isItSeventhRow(position) && isWhite())))
            {
                final int behindPossibleDestination = this.position + (8 * direction);
                if (!board.getBox(behindPossibleDestination).isBoxOccupied() && !board.getBox(possibleDestination).isBoxOccupied())
                {
                    legalMoves.add(new PawnJump(board, this, possibleDestination));
                }
            }
            else if (possibleMoveVector == 7 &&
                    !((ChessUtil.isItEighthColumn(position) && isWhite()) ||
                      (ChessUtil.isItFirstColumn(position) && !isWhite())))
            {
                if (board.getBox(possibleDestination).isBoxOccupied())
                {
                    final Piece pieceAtDestination = board.getBox(possibleDestination).getPiece();
                    if (this.isWhite() != pieceAtDestination.isWhite())
                    {
                        legalMoves.add(new PawnAttackMove(board,this,possibleDestination,pieceAtDestination));
                    }
                }
            }
            else if (possibleMoveVector == 9 &&
                    !((ChessUtil.isItFirstColumn(position) && isWhite()) ||
                      (ChessUtil.isItEighthColumn(position) && !isWhite())))
            {
                if (board.getBox(possibleDestination).isBoxOccupied())
                {
                    final Piece pieceAtDestination = board.getBox(possibleDestination).getPiece();
                    if (this.isWhite() != pieceAtDestination.isWhite())
                    {
                        legalMoves.add(new PawnAttackMove(board,this,possibleDestination,pieceAtDestination));
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
     * Overridden Object class toString method, converts object of this class to sing of pawn
     *
     * @return String which contains pawn sign
     */

    @Override
    public String toString()
    {
        return "P";
    }
}
