package ir.atofighi.simple_snake_game.views;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;
import ir.atofighi.simple_snake_game.models.Map;
import ir.atofighi.simple_snake_game.models.Point;
import ir.atofighi.simple_snake_game.models.Snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapPanel extends JPanel {
    Map map;

    public MapPanel(Map map, int width, int height) {
        setBounds(GameConfiguration.boardMargin, GameConfiguration.frameHeight-GameConfiguration.boardHeight-2*GameConfiguration.boardMargin-20,
                width, height);
        this.map = map;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / map.getWidth();
        int cellHeight = getHeight() / map.getHeight();
        Graphics2D g2 = (Graphics2D) g;
        BufferedImage appleImage = null;
        BufferedImage bombImage = null;
        try {

            appleImage = ImageIO.read(new File("src/resources/apple.png").toURI().toURL());
            bombImage = ImageIO.read(new File("src/resources/bomb.png").toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if ((i + j) % 2 == 0) {
                    g2.setColor(new Color(170, 215, 80));
                } else {
                    g2.setColor(new Color(162, 210, 73));
                }
                g2.fillRect(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
            }
        }

        g2.drawImage(appleImage.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH),
                map.getApple().getX() * cellWidth , map.getApple().getY() * cellHeight ,
                new Color(0, 0, 0, 0), null);

        for(Point bomb: map.getBlockedPoints()) {
            g2.drawImage(bombImage.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH),
                    bomb.getX() * cellWidth , bomb.getY() * cellHeight ,
                    new Color(0, 0, 0, 0), null);
        }
    }
}
