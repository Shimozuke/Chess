package chessgame.gameboard;

import chessgame.ChessUtil;
import chessgame.gameboard.chesspieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * represents single box of the game board, has id
 */

public abstract class BoardBox
{
    protected final int boxId;
    private static final Map<Integer, EmptyBoardBox> EMPTY_BOXES = createAllBoxes();

    /**
     *
     * @param boxId index of the box on the game board
     */

    public BoardBox(int boxId)
    {
        this.boxId = boxId;
    }

    /**
     * getter of index of the box(where it is on the game board)
     *
     * @return boxId numerical value that represents box on the board
     */

    public int getBoxId()
    {
        return this.boxId;
    }

    /**
     * creates occupied box if there is piece on it or empty box if there is nothing
     *
     * @param boxId numerical position of the box on the board
     * @param piece chess piece that can be placed on the box
     * @return {@code occupiedBoardBox} if there is a piece on it; {@code emptyBoardBox} if there is nothing
     */

    public static BoardBox createBox(final int boxId, final Piece piece)
    {
        return piece != null ? new OccupiedBoardBox(boxId, piece) : EMPTY_BOXES.get(boxId);
    }

    /**
     * check if the box is occupied or empty
     *
     * @return {@code true} if this is occupiedBoardBox; {@code false} otherwise
     */

    public abstract boolean isBoxOccupied();

    /**
     * getter of the piece that is on this box
     *
     * @return {@code piece} that is on the box
     */

    public abstract Piece getPiece();

    /**
     * creates map of all boxes keys are indexes (boxId) and values are boxes(empty or occupied)
     *
     * @return map of all boxes
     */

    private static Map<Integer, EmptyBoardBox> createAllBoxes()
    {
        final Map<Integer, EmptyBoardBox> emptyBoxMap = new HashMap<>();
        for (int i = 0; i < ChessUtil.GAME_BOARD_SIZE; i++)
        {
            emptyBoxMap.put(i, new EmptyBoardBox(i));
        }
        return Collections.unmodifiableMap(emptyBoxMap);
    }
}
