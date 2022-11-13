package chessgame.player;

import chessgame.gameboard.chesspieces.King;
import chessgame.gameboard.chesspieces.Piece;
import chessgame.gameboard.Board;
import chessgame.gameboard.move.Move;
import chessgame.gameboard.move.Movement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * class which represents player, can be white or black. Player is an object that can make move and have king.
 */

public abstract class Player
{
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> availableMoves;
    private final boolean isInCheck;

    /**
     * 
     * @param board represents current board state
     * @param availableMoves contains collection of available moves that player can make
     * @param opponentMoves contains collection of available moves that opponent can make
     */

    Player(final Board board, final Collection<Move> availableMoves, final Collection<Move> opponentMoves)
    {
        this.board = board;
        this.playerKing = trackKing();
        this.availableMoves = Stream.concat(availableMoves.stream(), calculateKingCastles(availableMoves, opponentMoves).stream()).collect(Collectors.toUnmodifiableList());
        this.isInCheck = !Player.calculateAttackOnBox(this.playerKing.getPosition(), opponentMoves).isEmpty();
    }

    /**
     * getter of king piece
     * 
     * @return player king piece
     */
    
    public King getKing()
    {
        return this.playerKing;
    }

    /**
     * getter of all available moves
     *
     * @return collection of available moves
     */
    
    public Collection<Move> getAvailableMoves()
    {
        return this.availableMoves;
    }

    /**
     * check if the move is legal(physicly possible to make), that means that it is contained in collection availableMoves
     *
     * @param move move that will be checked
     * @return {@code true} if move can be done;{@code false} otherwise
     */

    public  boolean isMoveLegal(final Move move)
    {
        return this.availableMoves.contains(move);
    }

    /**
     * check if the king is in check
     *
     * @return {@code true} if king is in check; {@code false} otherwise
     */

    public boolean isInCheck()
    {
        return this.isInCheck;
    }

    /**
     * check if the king is in checkmate
     *
     * @return {@code true} if king is in checkmate; {@code false} otherwise
     */

    public boolean isInCheckMate()
    {
        return this.isInCheck && !hasEscapeMoves();
    }

    /**
     * check if the king is in stalemate
     *
     * @return {@code true} if king is in stalemate; {@code false} otherwise
     */

    public boolean isInStaleMate()
    {
        return !this.isInCheck && !hasEscapeMoves();
    }

//    public boolean isCastled()
//    {
//        return false;
//    }

    /**
     * making move means creating movement. After move is done it can be annulled if e.g. your king is checkmated
     *
     * @param move contains move thar has been made
     * @return movement with new board, move and move status {@code true} if move is legal and doesn't end up in checkmate of movemaker; movement with {@code false} otherwise
     */

    public Movement makeMove(final Move move)
    {
        if (!isMoveLegal(move))
        {
            return new Movement(this.board, move, false);
        }
        final Board nextBoard = move.execute();

        final Collection<Move> kingAttacks = Player.calculateAttackOnBox(nextBoard.getCurrentPlayer().getOpponent().getKing().getPosition(), nextBoard.getCurrentPlayer().getAvailableMoves());

        if (!kingAttacks.isEmpty())
        {
            return new Movement(this.board, move, false);
        }
        return new Movement(nextBoard, move, true);
    }

    /**
     * getter of collection with all alive pieces that player can make
     *
     * @return collection of pieces that belongs to the player
     */

    public abstract Collection<Piece> getAlivePieces();

    /**
     * check if player is white
     *
     * @return {@code true} if player is white; {@code false} otherwise
     */

    public abstract boolean isWhitePlayer();

    /**
     * getter of opponent of the player e.g. if it is white player round it will return blackPlayer
     *
     * @return player opponent
     */

    public abstract Player getOpponent();

    /**
     * calculates possible castle moves
     *
     * @param playerAvaliableMoves collection of moves that can be done by player
     * @param opponentAvaliableMoves collection of moves that can be done by player's opponent
     * @return collection of available castle moves
     */

    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerAvaliableMoves, Collection<Move> opponentAvaliableMoves);

    /**
     * calculates all opponent's moves no given position (box)
     *
     * @param position intex of the box on the game board
     * @param opponentMoves colection of opponents available moves
     * @return collection of opponent available moves on given position
     */

    protected static Collection<Move> calculateAttackOnBox(int position, Collection<Move> opponentMoves)
    {
        final List<Move> attackMoves = new ArrayList<>();
        for (Move move : opponentMoves)
        {
            if (position == move.getDestination())
            {
                attackMoves.add(move);
            }
        }
        return Collections.unmodifiableList(attackMoves);
    }

    /**
     * finds players king from all pieces
     *
     * @return king piece that belongs to the player
     * @throws RuntimeException if there is no player king on the game board
     */

    private King trackKing()
    {
        for (Piece piece : getAlivePieces())
        {
            if (piece.isKing())
            {
                return (King) piece;
            }
        }
        throw new RuntimeException("Na planszy nie ma króla!!!!!!!!");
    }

    /**
     * check if there is an escape move for the checked king
     *
     * @return {@code true} if there is escape road for the checked king; {@code false} otherwise
     */

    private boolean hasEscapeMoves()
    {
        for (Move move : this.availableMoves)
        {
            final Movement movement = makeMove(move);
            if (movement.isDone())
            {
                return true;
            }
        }
        return false;
    }
}
