package chessgame.gameboard;

import chessgame.gameboard.chesspieces.Piece;

/**
 * represents single empty box of the game board, has id
 */

public  final class EmptyBoardBox extends BoardBox
{

    /**
     *
     * @param boxId index of the box on the game board
     */

    public EmptyBoardBox(int boxId)
    {
        super(boxId);
    }

    /**
     * Overridden Object class toString method to convert object of this class to sing of empty box
     *
     * @return String which contains sign of empty box (-)
     */

    @Override
    public String toString()
    {
        return "-";
    }

    /**
     * check if the box is occupied(if it is empty or occupied box)
     *
     * @return {@code true} if that is occupied box; {@code false} otherwise
     */

    @Override
    public boolean isBoxOccupied()
    {
        return false;
    }

    /**
     * getter of the piece that is on this box
     *
     * @return {@code null} because this is empty box and there is no piece
     */

    @Override
    public Piece getPiece()
    {
        return null;
    }
}
