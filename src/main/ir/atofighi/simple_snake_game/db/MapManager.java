package ir.atofighi.simple_snake_game.db;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;
import ir.atofighi.simple_snake_game.controllers.GameClient;
import ir.atofighi.simple_snake_game.models.Map;
import ir.atofighi.simple_snake_game.models.Point;
import ir.atofighi.simple_snake_game.models.Snake;
import ir.atofighi.simple_snake_game.utils.Direction;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class MapManager {

    private GameClient gameClient;
    private int mapCount;
    private Map[] maps;

    public MapManager(GameClient gameClient) {
        this.gameClient = gameClient;
        mapCount = Integer.parseInt(gameClient.request("getMapCount"));

        maps = new Map[mapCount];
        for(int index = 0; index < mapCount; index++) {
            String details[] = gameClient.request("getMap " + index).split(" ");

            int i = 0;

            int width = Integer.parseInt(details[i++]);
            int height = Integer.parseInt(details[i++]);
            Map map = new Map(width, height);

            int snakeX = Integer.parseInt(details[i++]);
            int snakeY = Integer.parseInt(details[i++]);
            int snakeDir = Integer.parseInt(details[i++]);
            Direction snakeDirection = Direction.values()[snakeDir];

            Snake snake = new Snake(new Point(snakeX, snakeY), snakeDirection, GameConfiguration.snakeColor);
            map.addSnake(snake);

            int bombCount = Integer.parseInt(details[i++]);
            for (int j = 0; j < bombCount; j++) {
                int x = Integer.parseInt(details[i++]);
                int y = Integer.parseInt(details[i++]);
                map.addBlockedPoint(new Point(x, y));
            }

            maps[index] = map;
        }
    }


    public Map getMap(int index) {
        return maps[index].copy();
    }

    public int size() {
        return mapCount;
    }
}
