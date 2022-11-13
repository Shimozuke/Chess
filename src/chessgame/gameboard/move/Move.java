package chessgame.gameboard.move;

import chessgame.gameboard.Board.Builder;
import chessgame.gameboard.chesspieces.Pawn;
import chessgame.gameboard.chesspieces.Piece;
import chessgame.gameboard.Board;
import chessgame.gameboard.chesspieces.Rook;

/**
 * Abstract class which define move. Move is one of a lot of possible position changes that can be done during player turn.
 */

public abstract class Move
{
    final Board board;
    final Piece movedPiece;
    final int destination;

    public static final Move NULL_MOVE = new NullMove();
    /**
     *
     * @param board represents current board state
     * @param movedPiece represents which piece was moved
     * @param destination represents index of box no the board which was choosen
     */

    private Move(final Board board, final Piece movedPiece, final int destination)
    {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destination = destination;
    }

    /**
     * overridden definition of Object class equals metod
     *
     * @param other object that will be compared with original object
     * @return true if objects are equal or if objects have same prototypes, destinations and moved pieces.
     */

    @Override
    public boolean equals(final Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (!(other instanceof Move))
        {
            return false;
        }
        final Move otherMove = (Move) other;
        return this.getDestination() == otherMove.getDestination() &&
                this.getMovedPiece() == otherMove.getMovedPiece();
    }

    /**
     * getter of piece position
     *
     * @return position of the moved piece
     */

    public int getPosition()
    {
        return this.getMovedPiece().getPosition();
    }

    /**
     * getter of destination field
     *
     * @return value of destination field
     */

    public int getDestination()
    {
        return this.destination;
    }

    /**
     * getter of moved piece
     *
     * @return Piece type obiect of moved piece
     */

    public Piece getMovedPiece()
    {
        return movedPiece;
    }

    /**
     * template metod, to check if this is attack move or not, for all classes that extends this class
     *
     * @return false for standard implementation of this metod
     */

    public boolean isAttackMove()
    {
        return false;
    }

    /**
     * template metod, to check if it is castling move or not, for all classes that extends this class
     *
     * @return false for standard implementation of this metod
     */

    public boolean isCastlingMove()
    {
        return false;
    }

    /**
     * template metod, to get the piece that was attacked, for all classes that extends this class
     *
     * @return null for standard implementation of this metod
     */

    public Piece getAttackedPiece()
    {
        return null;
    }

    /**
     * Method that execute move, which means creating(building) new board with pieces position after the move.
     * Doesn't affect current board. Modified for CastleMove class.
     *
     * @return newly build board
     */

    public Board execute()
    {
        final Builder builder = new Builder();
        for (Piece piece : this.board.getCurrentPlayer().getAlivePieces())
        {
            if (!this.movedPiece.equals(piece))
            {
                builder.setPiece(piece);
            }
        }
        for (Piece piece : this.board.getCurrentPlayer().getOpponent().getAlivePieces())
        {
            builder.setPiece(piece);
        }
        // move the piece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setPlayerTurn(this.board.getCurrentPlayer().getOpponent().isWhitePlayer());
        return builder.build();
    }

    /**
     * NormalMove class which extends Move. Represents normal move e.g. move of knight which is not attacking is a normal move.
     */

    public static final class NormalMove extends Move
    {
        public NormalMove(final Board board, final Piece movedPiece, final int destination)
        {
            super(board, movedPiece, destination);
        }
    }

    /**
     * AttackMove class which extends Move. Represents attacking move e.g. move of knight which is attacking a pawn is an attack move.
     */

    public static class AttackMove extends Move
    {
        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece, final int destination, final Piece attackedPiece)
        {
            super(board, movedPiece, destination);
            this.attackedPiece = attackedPiece;
        }

        /**
         * overridden definition of Object class equals metod
         *
         * @param other object that will be compared with original object
         * @return {@code false} if objects are equal or if objects have same prototypes, destinations, moved pieces and attacked piece; {@code false} otherwise
         */

        @Override
        public boolean equals(final Object other)
        {
            if (this == other)
            {
                return true;
            }
            if (!(other instanceof AttackMove))
            {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && this.getAttackedPiece().equals(otherAttackMove.getMovedPiece());
        }

        /**
         * Overridden isAttackMove method of parent class
         *
         * @return always {@code true} (because this is AttackMove)
         */

        @Override
        public boolean isAttackMove()
        {
            return true;
        }

        /**
         * Overridden getAttackedPiece method of parent class
         *
         * @return {@code Piece} that was attacked
         */

        @Override
        public Piece getAttackedPiece()
        {
            return this.attackedPiece;
        }
    }

    /**
     * PawnMove class which extends Move. Represents one box forward move of a pawn.
     */

    public static final class PawnMove extends Move
    {
        public PawnMove(final Board board, final Piece movedPiece, final int destination)
        {
            super(board, movedPiece, destination);
        }
    }

    /**
     * PawnAttackMove class which extends AttackMove. Represents attack move of a pawn.
     */

    public static class PawnAttackMove extends AttackMove
    {
        public PawnAttackMove(final Board board, final Piece movedPiece, final int destination, final Piece attackedPiece)
        {
            super(board, movedPiece, destination, attackedPiece);
        }
    }

    /**
     * PawnEnPassantAttackMove class which extends PawnAttackMove. Represents en passant attack move of a pawn.
     */

    public static final class PawnEnPassantAttackMove extends PawnAttackMove
    {

        public PawnEnPassantAttackMove(final Board board, final Piece movedPiece, final int destination, final Piece attackedPiece)
        {
            super(board, movedPiece, destination, attackedPiece);
        }
    }

    /**
     * PawnJump class which extends Move. Represents two box forward move of a pawn.
     */

    public static final class PawnJump extends Move
    {
        public PawnJump(final Board board, final Piece movedPiece, final int destination)
        {
            super(board, movedPiece, destination);
        }

        /**
         * Method that execute move, which means creating(building) new {@link Board} with pieces position after the move.
         * Doesn't affect current board. Modified for PawnJump class.
         *
         * @return newly build board
         */

        @Override
        public Board execute()
        {
            final Builder builder = new Builder();
            for (Piece piece : this.board.getCurrentPlayer().getAlivePieces())
            {
                if (!this.movedPiece.equals(piece))
                {
                    builder.setPiece(piece);

                }
            }
            for (Piece piece : this.board.getCurrentPlayer().getOpponent().getAlivePieces())
            {
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassant(movedPawn);
            builder.setPlayerTurn(this.board.getCurrentPlayer().getOpponent().isWhitePlayer());
            return builder.build();
        }
    }

    /**
     * CastleMove abstract class which extends Move. Represents combined castling move of king and rook.
     */

    public static abstract class CastleMove extends Move
    {
        private final Rook castleRook;
        protected final int castleRookPosition;
        protected final int castleRookDestination;

        public CastleMove(final Board board, final Piece movedPiece, final int destination,
                          final Rook castleRook, final int castleRookPosition, final int castleRookDestination)
        {
            super(board, movedPiece, destination);
            this.castleRook = castleRook;
            this.castleRookPosition = castleRookPosition;
            this.castleRookDestination = castleRookDestination;
        }

        /**
         * getter of rook which is castled
         *
         * @return rook that is being castled
         */

        public Rook getCastleRook()
        {
            return castleRook;
        }

        /**
         * method that informate user that it is castling move
         *
         * @return always true because this is CastleMove class
         */

        @Override
        public boolean isCastlingMove()
        {
            return true;
        }

        /**
         * Method that execute move, which means creating(building) new {@link Board} with pieces position after the move.
         * Doesn't affect current board. Modified for CastleMove class.
         *
         * @return newly build board
         */

        @Override
        public Board execute()
        {
            final Builder builder = new Builder();
            for (Piece piece : this.board.getCurrentPlayer().getAlivePieces())
            {
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece))
                {
                    builder.setPiece(piece);
                }
            }
            for (Piece piece : this.board.getCurrentPlayer().getOpponent().getAlivePieces())
            {
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.isWhite(), false));
            builder.setPlayerTurn(this.board.getCurrentPlayer().getOpponent().isWhitePlayer());
            return builder.build();
        }
    }

    /**
     * KingSideCastleMove class which extends CastleMove. Represents combinated castling move of king and rook on the kong side.
     */

    public static final class KingSideCastleMove extends CastleMove
    {
        public KingSideCastleMove(Board board, Piece movedPiece, int destination,
                                  Rook castleRook, int castleRookPosition, int castleRookDestination)
        {
            super(board, movedPiece, destination, castleRook, castleRookPosition, castleRookDestination);
        }

        /**
         * Overridden Object class toString method to convert object of this class to sing of castling
         *
         * @return String which contains castling sign
         */

        @Override
        public String toString()
        {
            return "O-O";
        }
    }

    /**
     * QueenSideCastleMove class which extends CastleMove. Represents combinated castling move of king and rook on the queen castle.
     */

    public static final class QueenSideCastleMove extends CastleMove
    {
        public QueenSideCastleMove(Board board, Piece movedPiece, int destination, Rook castleRook, int castleRookPosition, int castleRookDestination)
        {
            super(board, movedPiece, destination, castleRook, castleRookPosition, castleRookDestination);
        }

        /**
         * Overridden Object class toString method, converts object of this class to sing of castling
         *
         * @return String which contains castling sign
         */

        @Override
        public String toString()
        {
            return "O-O-O";
        }
    }

    /**
     * NullMove class which extends Move. Represents move that can't be done(when the player try making illegal move).
     */

    public static final class NullMove extends Move
    {
        public NullMove()
        {
            super(null, null, -1);
        }

        /**
         * Overridden execute method creating RuntimeException, because you cant make null move
         *
         * @return can't return anything
         * @throws RuntimeException to inform that there is something wrong while executing move.
         */

        @Override
        public Board execute()
        {
            throw new RuntimeException("can not execute the null move");
        }
    }

    /**
     * MoveFactory abstract class which creates move that player made.
     */

    public abstract static class MoveFactory
    {
        private MoveFactory()
        {
            throw new RuntimeException("not instantionable");
        }

        /**
         * this method is checking if chosen position and destination exist on board and are registrated
         *
         * @param board actual game board with all pieces, their positions and avaliable moves
         * @param position chosen position on the board
         * @param destination chosen destination on the board
         * @return {@code move} if there is registered move that have this position and destination; {@code NULL_MOVE} if there aren't any move like this registered
         */

        public static Move createMove(final Board board, final int position, final int destination)
        {
            for (Move move : board.getAllAvaliableMoves())
            {
                if (move.getPosition() == position && move.getDestination() == destination)
                {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
