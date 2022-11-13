package chessgame;

import chessgame.gui.ChessFrame;

import java.awt.*;

/**
 * main class in the project contains main and start thread of the gui
 * here doesn't happen much
 */

public class Chess
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> new ChessFrame());
    }
}
