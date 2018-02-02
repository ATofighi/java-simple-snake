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

public class ScoreManager {

    private int score = 0;

    private GameClient gameClient;

    public ScoreManager(GameClient gameClient) {
        this.gameClient = gameClient;

        score = Integer.parseInt(gameClient.request("getMaxScore"));

    }

    public int getScore() {
        return score;
    }

    public void clear() {
        score = 0;
    }



    public void write() {
        gameClient.request("setMaxScore "+score);
    }

    public void setScore(int score) {
        this.score = score;
    }
}
