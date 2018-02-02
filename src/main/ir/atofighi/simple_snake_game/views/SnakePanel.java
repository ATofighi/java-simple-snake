package ir.atofighi.simple_snake_game.views;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;
import ir.atofighi.simple_snake_game.models.Map;
import ir.atofighi.simple_snake_game.models.Point;
import ir.atofighi.simple_snake_game.models.Snake;
import ir.atofighi.simple_snake_game.utils.Direction;

import javax.swing.*;
import java.awt.*;

public class SnakePanel extends JPanel {
    Map map;

    public SnakePanel(Map map, int width, int height) {
        setBounds(GameConfiguration.boardMargin, GameConfiguration.frameHeight-GameConfiguration.boardHeight-2*GameConfiguration.boardMargin-20,
                width, height);
        setBackground(new Color(0, 0, 0, 0));
        this.map = map;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellWidth = getWidth() / map.getWidth();
        int cellHeight = getHeight() / map.getHeight();


        Graphics2D g2 = (Graphics2D) g;

        for (Snake snake : map.getSnakes()) {
            Point prevPoint = null;
            //Direction prevDirection = snake.getDirection();
            Point sarPoint = null;
            for (Point point : snake.getPoints()) {
                int x = point.getX();
                int y = point.getY();




              /*  Direction direction = snake.getDirection();
                if (prevPoint != null) {
                    direction = prevPoint.getDirectionWith(point);
                }
*/
                g2.setColor(snake.getColor());
                int x1 = x * cellWidth,//(int) ((x + .1) * cellWidth),
                        y1 = y * cellHeight,//(int) ((y + 0.1) * cellHeight),
                        w = cellWidth,//(int) (cellWidth * .8),
                        h = cellHeight;//(int) (cellHeight * 0.8);

  //              if (direction.differenceX() == 0) {
                    g2.fillRect(x1, y * cellHeight, w, cellHeight);
    //            } else if (direction.differenceY() == 0) {
//                    g2.fillRect(x * cellWidth, y1, cellWidth, h);
//                }

                if (prevPoint == null) {
                    sarPoint = point;
                }

                prevPoint = point;
  //              prevDirection = direction;
            }
            {
                int x = sarPoint.getX();
                int y = sarPoint.getY();
                int x1 = x * cellWidth,//(int) ((x + .1) * cellWidth),
                        y1 = y * cellHeight,//(int) ((y + 0.1) * cellHeight),
                        w = cellWidth,//(int) (cellWidth * .8),
                        h = cellHeight;//(int) (cellHeight * 0.8);

                g2.setColor(GameConfiguration.snakeEyeColor);
                int snakeEyeRadius = GameConfiguration.snakeEyeRadius;
                int snakeMouthHeight = GameConfiguration.snakeMouthHeight;
                int snakeMouthWidth = w;

                if(map.getApple().getDistance(sarPoint) <= GameConfiguration.bigHeadDistance) {
                    snakeEyeRadius *= GameConfiguration.bigHeadScale;
                    snakeMouthHeight *= GameConfiguration.bigHeadScale;
                    //snakeMouthWidth *= GameConfiguration.bigHeadScale;
                }
                if (snake.getDirection() == Direction.RIGHT || snake.getDirection() == Direction.LEFT) {
                    g2.fillRoundRect(x1 + w / 2 - snakeEyeRadius, y1 - snakeEyeRadius,
                            2*snakeEyeRadius, 2*snakeEyeRadius, 2*snakeEyeRadius, 2*snakeEyeRadius);
                    g2.fillRoundRect(x1 + w / 2 - snakeEyeRadius, y1 + h - snakeEyeRadius,
                            2*snakeEyeRadius, 2*snakeEyeRadius, 2*snakeEyeRadius, 2*snakeEyeRadius);

                } else {
                    g2.fillRoundRect(x1 - snakeEyeRadius, y1 + h / 2 - snakeEyeRadius,
                            2*snakeEyeRadius, 2*snakeEyeRadius, 2*snakeEyeRadius, 2*snakeEyeRadius);
                    g2.fillRoundRect(x1 + w - snakeEyeRadius, y1 + h / 2 - snakeEyeRadius,
                            2*snakeEyeRadius, 2*snakeEyeRadius, 2*snakeEyeRadius, 2*snakeEyeRadius);
                }

                g2.setColor(GameConfiguration.snakeMouthColor);
                if(snake.getDirection() == Direction.LEFT) {
                    g2.fillArc(x1-snakeMouthHeight/2, y1+h/2 - snakeMouthWidth/2, snakeMouthHeight, snakeMouthWidth, 90, 180);
                } else if(snake.getDirection() == Direction.RIGHT) {
                    g2.fillArc(x1+w-snakeMouthHeight/2, y1+h/2 - snakeMouthWidth/2, snakeMouthHeight, snakeMouthWidth, 270, 180);
                }
                else if(snake.getDirection() == Direction.UP) {
                    g2.fillArc(x1+w/2-snakeMouthWidth/2, y1 - snakeMouthHeight/2, snakeMouthWidth, snakeMouthHeight, 0, 180);
                } else {
                    g2.fillArc(x1+w/2-snakeMouthWidth/2, y1+h-snakeMouthHeight/2, snakeMouthWidth, snakeMouthHeight, 180, 180);
                }
            }
        }
    }
}