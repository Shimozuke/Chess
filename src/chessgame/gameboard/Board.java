package chessgame.gameboard;

import chessgame.ChessUtil;
import chessgame.gameboard.chesspieces.*;
import chessgame.gameboard.move.Move;
import chessgame.player.BlackPlayer;
import chessgame.player.Player;
import chessgame.player.WhitePlayer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * class that represent actual state of the board: how are pieces placed, who is making move, what pieces are available, how player can move, and a lot more.
 */

public class Board
{
    private List<BoardBox> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;

    private final Player currentPlayer;

    /**
     * constructor of this class creates game board,
     * calculates alive pieces for white and black player,
     * calculates available moves for white and black player,
     * creates white and black player and set actual round player
     *
     * @param builder builder of board and the only way to create object of Board class
     */

    private Board(Builder builder)
    {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = caltulateAlivePieces(this.gameBoard, true);
        this.blackPieces = caltulateAlivePieces(this.gameBoard, false);

        final Collection<Move> avaliableWhiteMoves = calculateAvaliableMoves(this.whitePieces);
        final Collection<Move> avaliableBlackMoves = calculateAvaliableMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, avaliableWhiteMoves, avaliableBlackMoves);
        this.blackPlayer = new BlackPlayer(this, avaliableWhiteMoves, avaliableBlackMoves);

        currentPlayer = builder.isWhiteTurn ? whitePlayer : blackPlayer;
    }

    /**
     * getter of current player, that is making move
     *
     * @return {@code player} - move maker
     */

    public Player getCurrentPlayer()
    {
        return  this.currentPlayer;
    }

    /**
     * getter of white player
     *
     * @return {@code player} - white player
     */

    public Player getWhitePlayer()
    {
        return this.whitePlayer;
    }

    /**
     * getter of black player
     *
     * @return {@code player} - black player
     */

    public Player getBlackPlayer()
    {
        return this.blackPlayer;
    }

    /**
     * getter of white player pieces
     *
     * @return {@code Collection<Piece>} - pieces that belong to the white player
     */

    public  Collection<Piece> getWhitePieces()
    {
        return this.whitePieces;
    }

    /**
     * getter of black player pieces
     *
     * @return {@code Collection<Piece>} - pieces that belong to the black player
     */

    public  Collection<Piece> getBlackPieces()
    {
        return this.blackPieces;
    }

    /**
     * getter of the box which contains boxId
     *
     * @return {@code boardBox} - box on given ID
     */

    public BoardBox getBox(final int boxId)
    {
        return gameBoard.get(boxId);
    }

    /**
     * creator of standard starting game board, creator is using builder to create board
     *
     * @return {@code board} created by the builder
     */

    public static Board createStandardBoard()
    {
        final Builder builder = new Builder();
        //Black pieces
        builder.setPiece(new Rook(0, false, true));
        builder.setPiece(new Knight(1, false, true));
        builder.setPiece(new Bishop(2, false, true));
        builder.setPiece(new Queen(3, false, true));
        builder.setPiece(new King(4, false, true));
        builder.setPiece(new Bishop(5, false, true));
        builder.setPiece(new Knight(6, false, true));
        builder.setPiece(new Rook(7, false, true));
        builder.setPiece(new Pawn(8, false, true));
        builder.setPiece(new Pawn(9, false, true));
        builder.setPiece(new Pawn(10, false, true));
        builder.setPiece(new Pawn(11, false, true));
        builder.setPiece(new Pawn(12, false, true));
        builder.setPiece(new Pawn(13, false, true));
        builder.setPiece(new Pawn(14, false, true));
        builder.setPiece(new Pawn(15, false, true));
        //White pieces
        builder.setPiece(new Pawn(48, true, true));
        builder.setPiece(new Pawn(49, true, true));
        builder.setPiece(new Pawn(50, true, true));
        builder.setPiece(new Pawn(51, true, true));
        builder.setPiece(new Pawn(52, true, true));
        builder.setPiece(new Pawn(53, true, true));
        builder.setPiece(new Pawn(54, true, true));
        builder.setPiece(new Pawn(55, true, true));
        builder.setPiece(new Rook(56, true, true));
        builder.setPiece(new Knight(57, true, true));
        builder.setPiece(new Bishop(58, true, true));
        builder.setPiece(new Queen(59, true, true));
        builder.setPiece(new King(60, true, true));
        builder.setPiece(new Bishop(61, true, true));
        builder.setPiece(new Knight(62, true, true));
        builder.setPiece(new Rook(63, true, true));
        //Whose is this board(move)
        builder.setPlayerTurn(true);

        return builder.build();
    }

    /**
     * Overridden Object class toString method to convert object of this class to specified output data (type String)
     *
     * @return String which creates sign of whole board
     */

    @Override
    public String toString()
    {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ChessUtil.GAME_BOARD_SIZE; i++)
        {
            stringBuilder.append(String.format("%3s", gameBoard.get(i).toString()));
            if ((i+1) % ChessUtil.NUMBER_OF_COLUMNS == 0)
            {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * creator of game board boxes, create all boxes on the box
     *
     * @param builder builder of Board class, that creates game board
     * @return list of all board boxes
     */

    private static List<BoardBox> createGameBoard(final Builder builder)
    {
        final List<BoardBox> boardBoxes = new ArrayList<>();
        for (int i = 0; i < ChessUtil.GAME_BOARD_SIZE; i++)
        {
            boardBoxes.add(BoardBox.createBox(i, builder.boardConfig.get(i)));
        }
        return Collections.unmodifiableList(boardBoxes);
    }

    /**
     * caltulates all available moves that player can make based on moves that were calculated by logic of every piece
     *
     * @param pieces piece on the board that belong to the player
     * @return list of all avaliable moves that player can make
     */

    private Collection<Move> calculateAvaliableMoves(final Collection<Piece> pieces)
    {
        final List<Move> avaliableMoves = new ArrayList<>();
        for (Piece piece : pieces)
        {
            avaliableMoves.addAll(piece.logic(this));
        }
        return Collections.unmodifiableList(avaliableMoves);
    }

    /**
     * calculates all pieces that belong to the player and are alive
     *
     * @param gameBoard contains status of whole board
     * @param isWhite defines if calculated pieces belong to white or black player
     * @return list of all alive pieces that belongs to the player
     */

    private static Collection<Piece> caltulateAlivePieces(final List<BoardBox> gameBoard, final boolean isWhite)
    {
        final List<Piece> alivePieces = new ArrayList<>();
        for (final BoardBox boardBox : gameBoard)
        {
            if (boardBox.isBoxOccupied())
            {
                final Piece piece = boardBox.getPiece();
                if (piece.isWhite() == isWhite)
                {
                    alivePieces.add(piece);
                }
            }
        }
        return Collections.unmodifiableList(alivePieces);
    }

    /**
     * calculates all moves that both players can make, it is just sum of all available moves
     *
     * @return sum of all moves that can be made by both players
     */

    public Collection<Move> getAllAvaliableMoves()
    {
        return Stream.concat(this.whitePlayer.getAvailableMoves().stream(), this.blackPlayer.getAvailableMoves().stream()).collect(Collectors.toUnmodifiableList());
    }

    public static class Builder
    {
        Map<Integer, Piece> boardConfig;
        boolean isWhiteTurn;
        Pawn enPassantPawn;


        public Builder()
        {
            this.boardConfig = new HashMap<>();
        }

        /**
         * add piece to the gameboard
         *
         * @param piece chess piece that can be placed on the box
         * @return builder with added piece
         */

        public Builder setPiece(final Piece piece)
        {
            this.boardConfig.put(piece.getPosition(), piece);
            return this;
        }

        /**
         * defines to whom this board belongs(who is move maker)
         *
         * @param isWhiteTurn field that defines move maker on the new board
         * @return builder with defined move maker
         */

        public Builder setPlayerTurn(final boolean isWhiteTurn)
        {
            this.isWhiteTurn = isWhiteTurn;
            return this;
        }

        /**
         * register en passant pawn, which means pawn that was moved in the previous round
         *
         * @param enPassantPawn pawn that was moved in previous round
         */

        public void setEnPassant(Pawn enPassantPawn)
        {
            this.enPassantPawn = enPassantPawn;
        }

        /**
         * build() method of the builder
         *
         * @return Board that was created by this builder
         */

        public Board build()
        {
            return new Board(this);
        }
    }
}
