package ir.atofighi.simple_snake_game.db;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;

import java.io.*;

public class ServerScoreManager {

    private int score = 0;

    public ServerScoreManager() throws IOException {
        InputStream file = new FileInputStream(GameConfiguration.scoreFile);

        score = file.read();
        file.close();
    }

    public int getScore() {
        return score;
    }

    public void clear() {
        score = 0;
    }


    public void write() throws IOException {
        OutputStream out = new FileOutputStream(GameConfiguration.scoreFile);
        out.write(score);
        out.close();
    }

    public void setScore(int score) {
        if (score > this.score)
            this.score = score;
    }
}
