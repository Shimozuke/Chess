package chessgame.gui;

import javax.swing.*;
import java.awt.*;

/**
 * placeholder for white player timer
 */

public class WhitePlayerTimer extends JPanel
{
    public WhitePlayerTimer()
    {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800,100));

        final JLabel timerLabel = new JLabel();

        this.setVisible(true);
    }
}
