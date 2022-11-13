package chessgame.player;

import chessgame.gameboard.BoardBox;
import chessgame.gameboard.chesspieces.Piece;
import chessgame.gameboard.Board;
import chessgame.gameboard.chesspieces.Rook;
import chessgame.gameboard.move.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static chessgame.gameboard.move.Move.*;

/**
 * class which represents white player. Player is an object that can make move and have king.
 */

public class WhitePlayer extends Player
{

    /**
     *
     * @param board represents current board state
     * @param avaliableWhiteMoves contains collection of available moves that player can make
     * @param avaliableBlackMoves contains collection of available moves that opponent can make
     */

    public WhitePlayer(Board board, Collection<Move> avaliableWhiteMoves, Collection<Move> avaliableBlackMoves)
    {
        super(board, avaliableWhiteMoves, avaliableBlackMoves);
    }

    /**
     * getter of collection with all alive pieces that player can make
     *
     * @return collection of pieces that belongs to the player
     */

    @Override
    public Collection<Piece> getAlivePieces()
    {
        return this.board.getWhitePieces();
    }

    /**
     * check if player is white
     *
     * @return {@code true} if player is white; {@code false} otherwise
     */

    @Override
    public boolean isWhitePlayer()
    {
        return true;
    }

    /**
     * getter of opponent of the player e.g. if it is white player round it will return blackPlayer
     *
     * @return player opponent
     */

    @Override
    public Player getOpponent()
    {
        return this.board.getBlackPlayer();
    }

    /**
     * calculates possible castle moves
     *
     * @param playerAvaliableMoves collection of moves that can be done by player
     * @param opponentAvaliableMoves collection of moves that can be done by player's opponent
     * @return collection of available castle moves
     */

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerAvaliableMoves, final Collection<Move> opponentAvaliableMoves)
    {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck())
        {
            //white king side castle
            if (!this.board.getBox(61).isBoxOccupied() &&
                    !this.board.getBox(62).isBoxOccupied())
            {
                final BoardBox rookBoardBox = this.board.getBox(63);

                if (rookBoardBox.isBoxOccupied() && rookBoardBox.getPiece().isFirstMove())
                {
                    if (Player.calculateAttackOnBox(61, opponentAvaliableMoves).isEmpty() &&
                            Player.calculateAttackOnBox(62, opponentAvaliableMoves).isEmpty() &&
                            rookBoardBox.getPiece().isRook())
                    {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62,(Rook) rookBoardBox.getPiece(), rookBoardBox.getBoxId(), 61));
                    }
                }
            }
            //white queen side castle
            if (!this.board.getBox(57).isBoxOccupied() &&
                    !this.board.getBox(58).isBoxOccupied() &&
                    !this.board.getBox(59).isBoxOccupied())
            {
                final BoardBox rookBoardBox = this.board.getBox(56);

                if (rookBoardBox.isBoxOccupied() && rookBoardBox.getPiece().isFirstMove())
                {
                    if (Player.calculateAttackOnBox(59, opponentAvaliableMoves).isEmpty() &&
                            Player.calculateAttackOnBox(58, opponentAvaliableMoves).isEmpty() &&
                            rookBoardBox.getPiece().isRook())
                    {
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58,
                                (Rook) rookBoardBox.getPiece(), rookBoardBox.getBoxId(), 59));
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }
}
