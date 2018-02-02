package ir.atofighi.simple_snake_game.controllers;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;
import ir.atofighi.simple_snake_game.db.MapManager;
import ir.atofighi.simple_snake_game.db.ScoreManager;
import ir.atofighi.simple_snake_game.models.Map;
import ir.atofighi.simple_snake_game.models.Point;
import ir.atofighi.simple_snake_game.models.Snake;
import ir.atofighi.simple_snake_game.models.State;
import ir.atofighi.simple_snake_game.utils.Direction;
import ir.atofighi.simple_snake_game.views.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GameRunner {
    public void run(MapManager mapManager, ScoreManager scoreManager) {
        MainFrame mainFrame = new MainFrame();
        MainPanel mainPanel = new MainPanel();

        mainFrame.setContentPane(mainPanel);

        mainFrame.setVisible(true);

        State state = new State(scoreManager.getScore());
        StatePanel statePanel = new StatePanel(state);
        mainPanel.add(statePanel);
        while (true) {

            System.out.println("run "+state.getLevel());
            Map map = mapManager.getMap(state.getLevel() % mapManager.size());
            map.newApple();
            map.setState(state);
            Snake snake = map.getSnakes().get(0);
            //System.out.println(state.getLevel() + " " + map);

            MapPanel mapPanel = new MapPanel(map, GameConfiguration.boardWidth, GameConfiguration.boardHeight);
            mainPanel.add(mapPanel);

            SnakePanel snakePanel = new SnakePanel(map, GameConfiguration.boardWidth, GameConfiguration.boardHeight);
            mainPanel.add(snakePanel);

            mainPanel.setComponentZOrder(mapPanel, 1);
            mainPanel.setComponentZOrder(snakePanel, 0);

            int neededApples = (map.getWidth() * map.getHeight() / 50) * (state.getLevel() / mapManager.size() + 1);
            state.setNeededEats(neededApples);

            Player player = new Player(mainPanel, map, neededApples);
            player.start();

            mainFrame.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    try {
                        switch (e.getKeyCode()) {
                            case 37:
                                snake.setDirection(Direction.LEFT);
                                break;
                            case 38:
                                snake.setDirection(Direction.UP);
                                break;
                            case 39:
                                snake.setDirection(Direction.RIGHT);
                                break;
                            case 40:
                                snake.setDirection(Direction.DOWN);
                                break;
                        }
                        player.interrupt();
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });


            try {
                player.join();
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
            //System.out.println("finishing!");
            mainPanel.remove(snakePanel);
            mainPanel.remove(mapPanel);
            state.resetCurrentEats();
            if (player.isWinned()) {
                state.levelUp();
            } else {
                state.decreaseHealth();
            }
            scoreManager.setScore(state.getMaxScore());
            scoreManager.write();

        }
    }
}
