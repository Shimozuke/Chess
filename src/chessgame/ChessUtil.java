package chessgame;

/**
 * Abstract utility class contains information about a game and methods that are usefull in all the project.
 * Mustn't be build!!!!
 */

public abstract class ChessUtil
{
    public static final int GAME_BOARD_SIZE = 64;
    public static final int NUMBER_OF_COLUMNS = 8;
    public static final int NUMBER_OF_ROWS = 8;
    public static final int[] KNIGHT_POSSIBLE_MOVES = {6, 10, 15, 17, -6, -10, -15, -17};
    public static final int[] BISHOP_POSSIBLE_MOVES_VECTOR = {7, 9, -7, -9};
    public static final int[] ROOK_POSSIBLE_MOVES_VECTOR = {1, 8, -1, -8};
    public static final int[] QUEEN_POSSIBLE_MOVES_VECTOR = {1, 7, 8, 9, -1, -7, -8, -9};
    public static final int[] PAWN_POSSIBLE_MOVES_VECTOR = {8, 16, 7, 9};
    public static final int[] KING_POSSIBLE_MOVES_VECTOR = {1, 7, 8, 9, -1, -7, -8, -9};

    /**
     * method that checks if index doesn't go out of boundaries of the board. Board exist on indexes from 0 to 63
     *
     * @param position index of box on the board
     * @return {@code true} if position is less than 0 or more than 63; {@code false} otherwise
     */

    public static boolean borderCheck(int position)
    {
        return position < GAME_BOARD_SIZE && position >= 0;
    }

    /**
     * check if the position is in the first column
     *
     * @param position index of box on the board
     * @return {@code true} if position is in the first column; {@code false} otherwise
     */

    public static boolean isItFirstColumn(int position)
    {
        return position % 8 == 0;
    }

    /**
     * check if the position is in the second column
     *
     * @param position index of box on the board
     * @return {@code true} if position is in the second column; {@code false} otherwise
     */

    public static boolean isItSecondColumn(int position)
    {
        return position % 8 == 1;
    }

    /**
     * check if the position is in the seventh column
     *
     * @param position index of box on the board
     * @return {@code true} if position is in the seventh column; {@code false} otherwise
     */

    public static boolean isItSeventhColumn(int position)
    {
        return position % 8 == 6;
    }

    /**
     * check if the position is in the eighth column
     *
     * @param position index of box on the board
     * @return {@code true} if position is in the eighth column; {@code false} otherwise
     */

    public static boolean isItEighthColumn(int position)
    {
        return position % 8 == 7;
    }

    /**
     * check if the position is in the second row
     *
     * @param position index of box on the board
     * @return {@code true} if position is in the second row; {@code false} otherwise
     */

    public static boolean isItSecondRow(int position)
    {
        return position > 7 && position < 16;
    }

    /**
     * check if the position is in the seventh row
     *
     * @param position index of box on the board
     * @return {@code true} if position is in the seventh row; {@code false} otherwise
     */


    public static boolean isItSeventhRow(int position)
    {
        return position > 47 && position < 56;
    }
}
