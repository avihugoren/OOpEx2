package gameClient;

import api.game_service;

import javax.swing.*;
import java.awt.*;

public class panelTimer extends JPanel {


    private game_service game;
    public panelTimer(game_service game)
    {

        super();
        this.game=game;
        this.setBackground(new Color(91, 102, 203));
    }
    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        g.clearRect(0, 0, w, h);
        super.paintComponent(g);
        long timeToEnd = game.timeToEnd();
        // this.setBackground(Color.ORANGE);
        this.setBounds(0, 0, 200, 50);
        g.setColor(Color.ORANGE);
        g.setFont(new Font("ARIEL", Font.BOLD, 18));
        g.drawString("time to end: " + timeToEnd / 1000.0, 20, 20);
    }
}
