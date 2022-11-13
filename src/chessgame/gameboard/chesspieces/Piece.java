package chessgame.gameboard.chesspieces;

import chessgame.gameboard.Board;
import chessgame.gameboard.move.Move;

import java.util.List;

/**
 * class which represents chess piece
 */

public abstract class Piece
{
    protected final int position;
    protected final boolean whitePiece;
    protected final boolean isFirstMove;

    public Piece(final int position, final boolean whitePiece, final boolean isFirstMove)
    {
        this.position = position;
        this.whitePiece = whitePiece;
        this.isFirstMove = isFirstMove;
    }

    /**
     * overridden object class equals method, checks if pieces have the same parameters
     *
     * @param others object that will be compared
     * @return true if objects have same prototypes and parameters or they are the same obiects
     */

    @Override
    public boolean equals(final Object others)
    {
        if (this == others)
        {
            return true;
        }
        if (!(others instanceof Piece))
        {
            return false;
        }
        final Piece otherPiece = (Piece) others;
        return this.isWhite() == otherPiece.isWhite() && this.getPosition() == otherPiece.getPosition() &&
                this.toString() == otherPiece.toString();
    }

    /**
     * checks if this is a first move of piece
     *
     * @return {@code true} if this is first move; {@code false} otherwise
     */

    public boolean isFirstMove()
    {
        return this.isFirstMove;
    }

    /**
     * getter of piece position
     *
     * @return return position field of the piece
     */

    public int getPosition()
    {
        return this.position;
    }

    /**
     * checks if piece is white
     *
     * @return {@code true} if piece is white; {@code false} otherwise
     */

    public  boolean isWhite()
    {
        return whitePiece;
    }

    /**
     * abstract declaration of move method which creates piece at a destination
     *
     * @param move contains information about the move that will be done
     * @return new piece
     */

    public abstract Piece movePiece(Move move);

    /**
     * calculate all moves that this piece can do
     *
     * @param board contains actual state of the game board e.g. positions of all pieces
     * @return list of available moves for this piece
     */

    public abstract List<Move> logic(Board board);

    /**
     * checks if this piece is a king
     *
     * @return {@code true} true if this is king; {@code false} otherwise
     */

    public abstract boolean isKing();

    /**
     * checks if next move won't end up out of the board
     *
     * @return {@code true} if this is first column and is moving out of the board; {@code false} otherwise
     */

    public abstract boolean isRook();
}
