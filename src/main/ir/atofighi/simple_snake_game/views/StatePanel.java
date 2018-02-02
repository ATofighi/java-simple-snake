package ir.atofighi.simple_snake_game.views;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;
import ir.atofighi.simple_snake_game.models.Map;
import ir.atofighi.simple_snake_game.models.Point;
import ir.atofighi.simple_snake_game.models.State;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StatePanel extends JPanel {
    State state;

    public StatePanel(State state) {
        setBounds(0, 0,
                GameConfiguration.frameWidth,
                GameConfiguration.frameHeight - GameConfiguration.boardHeight - 2 * GameConfiguration.boardMargin - 20);
        setBackground(new Color(0, 0, 0, 0));
        this.state = state;
    }


    private void showOption(Graphics2D g2, BufferedImage image, String text, int x, int y) {
        g2.drawImage(image.getScaledInstance(32, 32, Image.SCALE_SMOOTH),
                x, y,
                new Color(0, 0, 0, 0), null);
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        g2.setColor(Color.white);
        g2.drawString(text, x + 40, y + 22);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        BufferedImage appleImage;
        BufferedImage trophyImage;
        BufferedImage heartImage;
        BufferedImage gameImage;
        try {
            appleImage = ImageIO.read(new File("src/resources/apple.png").toURI().toURL());
            trophyImage = ImageIO.read(new File("src/resources/trophy.png").toURI().toURL());
            heartImage = ImageIO.read(new File("src/resources/heart.png").toURI().toURL());
            gameImage = ImageIO.read(new File("src/resources/game.png").toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int x = 20 - 80;
        showOption(g2, gameImage, (state.getLevel() + 1) + "", x += 80, 20);
        showOption(g2, heartImage, state.getHealth() + "", x += 80, 20);
        showOption(g2, appleImage, state.getScore() + "  (" + state.getCurrentEats() + " / " + state.getNeededEats() + ")", x += 80, 20);
        showOption(g2, trophyImage, state.getMaxScore() + "", x += 120, 20);

    }
}
