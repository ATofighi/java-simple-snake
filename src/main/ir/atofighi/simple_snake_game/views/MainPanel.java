package ir.atofighi.simple_snake_game.views;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        setLayout(null);
        setBackground(Color.decode("#578a35"));
        setSize(GameConfiguration.frameWidth, GameConfiguration.frameHeight);
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Component component: getComponents()) {
            component.repaint();
        }
    }*/
}
