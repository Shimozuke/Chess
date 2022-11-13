package chessgame.gameboard;

import chessgame.gameboard.chesspieces.Piece;

/**
 * represents single occupied box of the game board, has id
 */

public final class OccupiedBoardBox extends BoardBox
{
    private final Piece piece;

    /**
     *
     * @param boxId index of the box on the game board
     * @param piece piece that is placed on this box
     */

    public OccupiedBoardBox(int boxId, Piece piece)
    {
        super(boxId);
        this.piece = piece;
    }

    /**
     * Overridden Object class toString method to convert object of this class to sing of piece present on this box
     *
     * @return String which contains sign of the piece (if piece is whire sign will be uppercase, otherwise lowercase)
     */

    @Override
    public String toString()
    {
        return this.piece.isWhite() ? piece.toString() : piece.toString().toLowerCase();
    }

    /**
     * check if the box is occupied(if it is empty or occupied box)
     *
     * @return {@code true} if that is occupied box; {@code false} otherwise
     */

    @Override
    public boolean isBoxOccupied()
    {
        return true;
    }

    /**
     * getter of the piece that is on this box
     *
     * @return {@code piece} that is on the box
     */

    @Override
    public Piece getPiece()
    {
        return piece;
    }
}
