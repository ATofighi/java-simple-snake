package ir.atofighi.simple_snake_game.controllers;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;
import ir.atofighi.simple_snake_game.exceptions.SnakeEatHimselfException;
import ir.atofighi.simple_snake_game.exceptions.SnakeGoToOutOfMapException;
import ir.atofighi.simple_snake_game.models.Map;
import ir.atofighi.simple_snake_game.models.Snake;
import ir.atofighi.simple_snake_game.utils.Direction;
import ir.atofighi.simple_snake_game.views.MapPanel;
import ir.atofighi.simple_snake_game.views.SnakePanel;

import javax.swing.*;

public class Player extends Thread {

    long sleepTime;

    Map map;

    JPanel panel;

    private boolean winned = false;

    private int neededApples;

    public Player(JPanel panel, Map map, int neededApples) {
        super();
        this.map = map;
        this.panel = panel;
        this.neededApples = neededApples;
    }

    public void run() {
        while (map.getEatedApples() < neededApples) {
            try {
                sleep(GameConfiguration.sleepTime);
            } catch (InterruptedException e) {
                // nothing
                //return;
            }
            for (Snake snake : map.getSnakes()) {
                try {
                    snake.move();
                    if (snake.getPoints().get(0).equals(map.getApple())) {
                        snake.grow();
                        map.newApple();
                    }
                } catch (SnakeGoToOutOfMapException | SnakeEatHimselfException e) {
                    //System.out.println("lose...");
                    panel.revalidate();
                    panel.repaint();

                    return;
                }

            }
            panel.revalidate();
            panel.repaint();

        }
        winned = true;
    }

    public boolean isWinned() {
        return winned;
    }
}
