package ir.atofighi.simple_snake_game.models;

import ir.atofighi.simple_snake_game.utils.Direction;

import java.util.Objects;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }

    public void move(Direction direction) {
        x += direction.differenceX();
        y += direction.differenceY();
    }

    public void move(Direction direction, int xMod, int yMod) {
        x += direction.differenceX();
        x %= xMod;
        x += xMod;
        x %= xMod;
        y += direction.differenceY();
        y %= yMod;
        y += yMod;
        y %= yMod;
    }


    public Direction getDirectionWith(Point point) {
        if (x == point.x) {
            if (y == point.y + 1) {
                return Direction.UP;
            } else if (y == point.y - 1) {
                return Direction.DOWN;
            }
        } else if (y == point.y) {
            if (x + 1 == point.x) {
                return Direction.RIGHT;
            } else if (x - 1 == point.x) {
                return Direction.LEFT;
            }
        }
        return null;
    }

    public double getDistance(Point sarPoint) {
        return Math.sqrt(Math.pow(x - sarPoint.x, 2) + Math.pow(y - sarPoint.y, 2));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Point copy() {
        return new Point(x, y);
    }
}
