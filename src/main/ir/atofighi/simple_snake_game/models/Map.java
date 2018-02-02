package ir.atofighi.simple_snake_game.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map {

    enum CellType {
        EMPTY,
        BOMB,
        APPLE
    }

    private State state;
    private int width;
    private int height;
    private List<Snake> snakes = new ArrayList<>();
    private List<Point> blockedPoints = new ArrayList<>();
    private CellType[][] map;
    Point apple = null;
    private int eatedApples = -1;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        map = new CellType[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < width; j++) {
                map[i][j] = CellType.EMPTY;
            }
        }
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public void addSnake(Snake snake) {
        snake.setMap(this);
        snakes.add(snake);
    }

    public Snake getSnake(Point point) {
        for (Snake snake : snakes) {
            if (snake.hasPosition(point)) {
                return snake;
            }
        }
        return null;
    }

    public boolean hasSnake(Point point) {
        if (getSnake(point) == null)
            return false;
        return true;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public void addBlockedPoint(Point point) {
        if (map[point.getX()][point.getY()] == CellType.EMPTY) {
            map[point.getX()][point.getY()] = CellType.BOMB;
            blockedPoints.add(point);
        }
    }

    public List<Point> getBlockedPoints() {
        return blockedPoints;
    }

    public boolean isBlocked(Point p) {
        if(p.getY() >= height || p.getX() >= width || p.getX() < 0 || p.getY() < 0)
            return true;
        return map[p.getX()][p.getY()] == CellType.BOMB;
    }


    public void newApple() {
        eatedApples++;
        if (apple != null) {
            map[apple.getX()][apple.getY()] = CellType.EMPTY;
            state.increaseScore();
        }
        List<Point> freePoints = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!hasSnake(new Point(i, j)) && map[i][j] == CellType.EMPTY) {
                    freePoints.add(new Point(i, j));
                }

            }
        }
        apple = freePoints.get((int) Math.floor(Math.random() * freePoints.size()));
        map[apple.getX()][apple.getY()] = CellType.APPLE;
    }

    public Point getApple() {
        return apple;
    }

    public int getEatedApples() {
        return eatedApples;
    }

    @Override
    public String toString() {
        return "Map{" +
                "width=" + width +
                ", height=" + height +
                ", snakes=" + snakes.toString() +
                ", blockedPoints=" + blockedPoints +
                ", map=" + Arrays.toString(map) +
                ", apple=" + apple +
                ", eatedApples=" + eatedApples +
                '}';
    }


    public Map copy() {
        Map newMap = new Map(width, height);
        for (Snake snake : snakes) {
            newMap.addSnake(snake.copy());
        }
        for (Point p : blockedPoints) {
            newMap.addBlockedPoint(p);
        }
        return newMap;
    }


    public void setState(State state) {
        this.state = state;
    }
}
