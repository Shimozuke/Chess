package chessgame.gui;

import javax.swing.*;
import java.awt.*;

/**
 * placeholder for black player timer
 */

public class BlackPlayerTimer extends JPanel
{
    public BlackPlayerTimer()
    {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800,100));

        final JLabel timerLabel = new JLabel();

        this.setVisible(true);
    }
}
