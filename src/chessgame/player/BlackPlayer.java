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
 * class which represents black player. Player is an object that can make move and have king.
 */

public class BlackPlayer extends Player
{

    /**
     *
     * @param board represents current board state
     * @param avaliableWhiteMoves contains collection of available moves that player can make
     * @param avaliableBlackMoves contains collection of available moves that opponent can make
     */

    public BlackPlayer(Board board, Collection<Move> avaliableWhiteMoves, Collection<Move> avaliableBlackMoves)
    {
        super(board, avaliableBlackMoves, avaliableWhiteMoves);
    }

    /**
     * getter of collection with all alive pieces that player can make
     *
     * @return collection of pieces that belongs to the player
     */

    @Override
    public Collection<Piece> getAlivePieces()
    {
        return this.board.getBlackPieces();
    }

    /**
     * check if player is white
     *
     * @return {@code true} if player is white; {@code false} otherwise
     */

    @Override
    public boolean isWhitePlayer()
    {
        return false;
    }

    /**
     * getter of opponent of the player e.g. if it is white player round it will return blackPlayer
     *
     * @return player opponent
     */

    @Override
    public Player getOpponent()
    {
        return this.board.getWhitePlayer();
    }

    /**
     * calculates possible castle moves
     *
     * @param playerAvaliableMoves collection of moves that can be done by player
     * @param opponentAvaliableMoves collection of moves that can be done by player's opponent
     * @return collection of available castle moves
     */

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerAvaliableMoves, Collection<Move> opponentAvaliableMoves)
    {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck())
        {
            //black king side castle
            if (!this.board.getBox(5).isBoxOccupied() && !this.board.getBox(6).isBoxOccupied())
            {
                final BoardBox rookBoardBox = this.board.getBox(7);

                if (rookBoardBox.isBoxOccupied() && rookBoardBox.getPiece().isFirstMove())
                {
                    if (Player.calculateAttackOnBox(5, opponentAvaliableMoves).isEmpty() &&
                            Player.calculateAttackOnBox(6, opponentAvaliableMoves).isEmpty() &&
                            rookBoardBox.getPiece().isRook())
                    {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6,
                                                                (Rook) rookBoardBox.getPiece(), rookBoardBox.getBoxId(), 5));
                    }
                }
            }
            //black queen side castle
            if (!this.board.getBox(1).isBoxOccupied() &&
                    !this.board.getBox(2).isBoxOccupied() &&
                    !this.board.getBox(3).isBoxOccupied())
            {
                final BoardBox rookBoardBox = this.board.getBox(0);

                if (rookBoardBox.isBoxOccupied() && rookBoardBox.getPiece().isFirstMove())
                {
                    if (Player.calculateAttackOnBox(3, opponentAvaliableMoves).isEmpty() &&
                            Player.calculateAttackOnBox(2, opponentAvaliableMoves).isEmpty() &&
                            rookBoardBox.getPiece().isRook())
                    {
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2,
                                                                (Rook) rookBoardBox.getPiece(), rookBoardBox.getBoxId(), 3));
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }
}
