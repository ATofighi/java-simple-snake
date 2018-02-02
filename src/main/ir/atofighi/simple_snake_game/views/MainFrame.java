package ir.atofighi.simple_snake_game.views;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Snake Game");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setResizable(false);
        setSize(GameConfiguration.frameWidth, GameConfiguration.frameHeight);
        this.setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
