package ir.atofighi.simple_snake_game.db;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;
import ir.atofighi.simple_snake_game.models.Map;
import ir.atofighi.simple_snake_game.models.Point;
import ir.atofighi.simple_snake_game.models.Snake;
import ir.atofighi.simple_snake_game.utils.Direction;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ServerMapManager {

    private int mapCount = 0;
    Map[] maps;

    public ServerMapManager() throws IOException {
        InputStream file = new FileInputStream(GameConfiguration.mapsFile);

        mapCount = file.read();
        maps = new Map[mapCount];
        for (int i = 0; i < mapCount; i++) {

            int width = file.read();
            int height = file.read();
            Map map = new Map(width, height);

            int snakeX = file.read();
            int snakeY = file.read();
            int snakeDir = file.read();
            Direction snakeDirection = Direction.values()[snakeDir];

            Snake snake = new Snake(new Point(snakeX, snakeY), snakeDirection, GameConfiguration.snakeColor);
            map.addSnake(snake);

            int bombCount = file.read();
            for (int j = 0; j < bombCount; j++) {
                int x = file.read();
                int y = file.read();
                map.addBlockedPoint(new Point(x, y));
            }

            maps[i] = map;
        }
        file.close();
    }

    @Override
    public String toString() {
        return "MapManager{" +
                ", mapCount=" + mapCount +
                ", maps=" + Arrays.toString(maps) +
                '}';
    }

    public Map getMap(int i) {
        return maps[i].copy();
    }

    public void clear() {
        mapCount = 0;
        maps = new Map[0];
    }

    public void addMap(Map map) {
        Map[] maps1 = new Map[mapCount + 1];
        for (int i = 0; i < mapCount; i++) {
            maps1[i] = maps[i];
        }
        maps1[mapCount++] = map;
        maps = maps1;
    }

    public void write() throws IOException {
        OutputStream out = new FileOutputStream(GameConfiguration.mapsFile);
        out.write(mapCount);
        for (Map map : maps) {
            out.write(map.getWidth());
            out.write(map.getHeight());
            Snake snake = map.getSnakes().get(0);
            out.write(snake.getPoints().get(0).getX());
            out.write(snake.getPoints().get(0).getY());
            for (int i = 0; i < Direction.values().length; i++) {
                if (snake.getDirection() == Direction.values()[i]) {
                    out.write(i);
                }
            }

            List<Point> bombs = map.getBlockedPoints();
            out.write(bombs.size());
            for (Point bomb : bombs) {
                out.write(bomb.getX());
                out.write(bomb.getY());
            }
        }
        out.close();
    }

    public int size() {
        return mapCount;
    }
}
