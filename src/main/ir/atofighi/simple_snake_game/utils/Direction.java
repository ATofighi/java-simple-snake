package ir.atofighi.simple_snake_game.utils;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public int differenceX() {
        switch (this) {
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
            default:
                return 0;
        }
    }

    public int differenceY() {
        switch (this) {
            case UP:
                return -1;
            case DOWN:
                return 1;
            default:
                return 0;
        }
    }

    public boolean isOppositeOf(Direction direction) {
        return this.differenceX() == -direction.differenceX() &&
                this.differenceY() == -direction.differenceY();
    }

    public Direction getOpposite() {
        switch (this){
            case LEFT:
                return RIGHT;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case RIGHT:
                return LEFT;
        }
        return null;
    }
}
