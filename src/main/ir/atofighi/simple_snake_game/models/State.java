package ir.atofighi.simple_snake_game.models;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;

public class State {
    private int health;
    private int level;
    private int maxScore;
    private int score;
    private int currentEats;
    private int neededEats;

    public State(int maxScore) {
        this.health = GameConfiguration.initialHealth;
        level = 0;
        this.maxScore = maxScore;
        currentEats = score = 0;
    }

    public synchronized void increaseHealth() {
        health++;
    }

    public synchronized void decreaseHealth() {
        health--;
        currentEats = 0;
        if(health < 0) {
            health = GameConfiguration.initialHealth;
            level = 0;
            score = 0;
        }
    }

    public synchronized void increaseScore() {
        score++;
        currentEats++;
        if(score > maxScore)
            maxScore = score;
    }

    public synchronized void levelUp() {
        level++;
    }

    public synchronized int getHealth() {
        return health;
    }

    public synchronized int getLevel() {
        return level;
    }

    public synchronized int getMaxScore() {
        return maxScore;
    }

    public synchronized int getScore() {
        return score;
    }

    public void resetCurrentEats() {
        currentEats = 0;
    }

    public void setNeededEats(int neededEats) {
        this.neededEats = neededEats;
    }

    public int getCurrentEats() {
        return currentEats;
    }

    public int getNeededEats() {
        return neededEats;
    }
}
