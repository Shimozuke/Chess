package chessgame.gui;

import chessgame.FileManager;
import chessgame.gameboard.Board;
import chessgame.gameboard.BoardBox;
import chessgame.gameboard.chesspieces.Piece;
import chessgame.gameboard.move.Move;
import chessgame.gameboard.move.Movement;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 * GUI main Class
 */

public class ChessFrame
{
    private final JFrame gameFrame;

    private final BoardPanel boardPanel;
    private final WhitePlayerTimer whitePlayerTimer;
    private final BlackPlayerTimer blackPlayerTimer;

    private Board chessBoard;
    private FileManager fileManager;

    private BoardBox sourceBox;
    private BoardBox destinationBox;
    private Piece humanMovedPiece;

    public static final String DEFAULT_PIECES_IMAGE_PATH = "Pieces/Normal/";

    public ChessFrame()
    {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setSize(new Dimension(800,1000));
        this.gameFrame.setResizable(false);

        chessBoard = Board.createStandardBoard();
        fileManager = new FileManager();

        final JMenuBar chessMenuBar = createMenuBar();
        this.gameFrame.setJMenuBar(chessMenuBar);

        blackPlayerTimer = new BlackPlayerTimer();
        this.gameFrame.add(blackPlayerTimer, BorderLayout.PAGE_START);
        boardPanel = new BoardPanel();
        this.gameFrame.add(boardPanel, BorderLayout.CENTER);
        whitePlayerTimer = new WhitePlayerTimer();
        this.gameFrame.add(whitePlayerTimer, BorderLayout.PAGE_END);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createMenuBar()
    {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;

    }

    private JMenu createFileMenu()
    {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem loadPGN = new JMenuItem("Load PGN File");
        final JMenuItem saveGame = new JMenuItem("save game");
        final JMenuItem exit = new JMenuItem("Exit");

        loadPGN.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("open pgn");
            }
        });

        saveGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fileManager.saveGameState(chessBoard.toString());
            }
        });

        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        fileMenu.add(loadPGN);
        fileMenu.add(saveGame);
        fileMenu.add(exit);

        return fileMenu;
    }

    private class BoardPanel extends JPanel
    {
        private final List<BoxPanel> boxes;

        public BoardPanel()
        {
            super(new GridLayout(8,8));
            this.boxes = new ArrayList<>();
            for (int i = 0; i < 64; i++)
            {
                final BoxPanel boxPanel = new BoxPanel(this, i);
                boxes.add(boxPanel);
                this.add(boxPanel);
            }
            setPreferredSize(new Dimension(800,800));
            validate();
        }

        public void drawBoard(final Board board)
        {
            removeAll();
            for (BoxPanel boxPanel : boxes)
            {
                boxPanel.drawBox(board);
                add(boxPanel);
            }
            validate();
            repaint();
        }
    }


    private class BoxPanel extends JPanel
    {
        private final int boxId;
        public BoxPanel(final BoardPanel boardPanel, final int boxId)
        {
            super(new GridBagLayout());
            this.boxId = boxId;
            setPreferredSize(new Dimension(100,100));
            setBoxColor();
            setBoxPieceIcon(chessBoard);

            addMouseListener(new MouseListener()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (isRightMouseButton(e))
                    {
                        sourceBox = null;
                        destinationBox = null;
                        humanMovedPiece = null;
                    }
                    else if (isLeftMouseButton(e))
                    {
                        if (sourceBox == null)
                        {
                            //first click
                            sourceBox = chessBoard.getBox(boxId);
                            humanMovedPiece = sourceBox.getPiece();
                            if (humanMovedPiece == null || !(humanMovedPiece.isWhite() == chessBoard.getCurrentPlayer().isWhitePlayer()))
                            {
                                sourceBox = null;
                            }
                        }
                        else
                        {
                            //second click
                            destinationBox = chessBoard.getBox(boxId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceBox.getBoxId(), destinationBox.getBoxId());
                            final Movement movement = chessBoard.getCurrentPlayer().makeMove(move);
                            if (movement.isDone())
                            {
                                chessBoard = movement.getNextBoard();
                            }
                            sourceBox = null;
                            destinationBox = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(MouseEvent e)
                {

                }

                @Override
                public void mouseReleased(MouseEvent e)
                {

                }

                @Override
                public void mouseEntered(MouseEvent e)
                {

                }

                @Override
                public void mouseExited(MouseEvent e)
                {

                }
            });

            validate();
        }

        public void drawBox(final Board board)
        {
            setBoxColor();
            setBoxPieceIcon(board);
            validate();
            repaint();
        }

        private void setBoxPieceIcon(final Board board)
        {
            this.removeAll();
            if (board.getBox(this.boxId).isBoxOccupied())
            {
                final BufferedImage image;
                try
                {
                    image = ImageIO.read(new File(ChessFrame.DEFAULT_PIECES_IMAGE_PATH +
                            (board.getBox(this.boxId).getPiece().isWhite() ? "White_" : "Black_") +
                            board.getBox(this.boxId).getPiece().toString() + ".gif"));
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
                this.add(new JLabel(new ImageIcon(image)));
            }
        }

        private void setBoxColor()
        {
            int row = boxId / 8;

            if (row % 2 == 0)
            {
                if (boxId % 2 == 0)
                    setBackground(Color.white);
                else
                    setBackground(Color.gray);
            }
            else
            {
                if ((boxId + 1) % 2 == 0)
                    setBackground(Color.white);
                else
                    setBackground(Color.gray);
            }
        }
    }
}