package chessgame.gameboard.move;

import chessgame.gameboard.Board;

/**
 * class which represents real movement (change at the game board)
 */

public class Movement
{
    private final Board nextBoard;
    private final Move move;
    private final boolean moveStatus;

    /**
     *
     * @param nextBoard contains next board status, all pieces and informations about the game board
     * @param move contains move thar has been made
     * @param moveStatus declarate status of the move (move can annulled if after move your king is checkmated then move status is false ect)
     */

    public  Movement(final Board nextBoard, final Move move, final boolean moveStatus)
    {
        this.nextBoard = nextBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    /**
     * check if the move has been done or not
     *
     * @return {@code true} if move was done; {@code false} otherwise
     */

    public boolean isDone()
    {
        return moveStatus;
    }

    /**
     * getter of new board object from after the move
     *
     * @return nextBoard after move
     */

    public Board getNextBoard()
    {
        return this.nextBoard;
    }
}
