package ir.atofighi.simple_snake_game.models;

import ir.atofighi.simple_snake_game.exceptions.SnakeEatHimselfException;
import ir.atofighi.simple_snake_game.exceptions.SnakeGoToOutOfMapException;
import ir.atofighi.simple_snake_game.utils.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<Point> points = new ArrayList<>();
    // 0 is the head of snak

    private Map map = null;

    private Direction direction;

    private Color color;

    private boolean lockDirection = false;

    public Snake(Point point, Direction direction, Color color) {
        points.add(point);
        this.direction = direction;
        this.color = color;
    }

    public synchronized boolean hasPosition(Point position) {
        for (Point point : points) {
            if (point.equals(position))
                return true;
        }
        return false;
    }

    public synchronized void move() throws SnakeEatHimselfException, SnakeGoToOutOfMapException {
        lockDirection = false;
        int oldX = -1, oldY = -1;
        for (Point point : points) {
            int x = point.getX();
            int y = point.getY();
            if (oldX == -1) {
                point.move(direction);
                if (point.getX() < 0 || point.getX() >= map.getWidth()
                        || point.getY() < 0 || point.getY() > map.getHeight()) {
                    point.move(direction.getOpposite());
                    throw new SnakeGoToOutOfMapException();
                }
            } else {
                point.setX(oldX);
                point.setY(oldY);
            }


            oldX = x;
            oldY = y;
        }
        {
            Point point1 = this.points.get(0);
            if (map.isBlocked(point1)) {
                throw new SnakeGoToOutOfMapException();
            }
            for (Point point2 : points) {
                if (point1 != point2 && point1.equals(point2)) {
                    throw new SnakeEatHimselfException();
                }
            }
        }
    }

    public synchronized void grow() {
        Direction d = direction;
        Point prevPoint = null;
        for (Point point : points) {
            if (prevPoint != null)
                d = prevPoint.getDirectionWith(point);
            prevPoint = point;
        }
        Point newPoint = new Point(prevPoint.getX(), prevPoint.getY());
        newPoint.move(d.getOpposite(), map.getWidth(), map.getHeight());
        points.add(newPoint);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public synchronized List<Point> getPoints() {
        return points;
    }

    public synchronized Direction getDirection() {
        return direction;
    }

    public synchronized void setDirection(Direction direction) {
        if (this.direction.isOppositeOf(direction) && points.size() > 1) {
            throw new IllegalArgumentException("You can't move to opposite direction");
        }
        if (this.direction == direction) {
            throw new IllegalArgumentException("You are in this direction now");
        }
        if (this.direction != direction && !lockDirection) {
            this.direction = direction;
            lockDirection = true;
        }
    }


    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "points=" + points +
                ", direction=" + direction +
                ", color=" + color +
                ", lockDirection=" + lockDirection +
                '}';
    }

    public Snake copy() {
        Snake newSnake = new Snake(points.get(0).copy(), direction, color);
        for (int i = 1; i < points.size(); i++) {
            newSnake.points.add(points.get(i).copy());
        }
        return newSnake;
    }
}