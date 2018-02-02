package ir.atofighi.simple_snake_game;

import ir.atofighi.simple_snake_game.controllers.GameServer;
import ir.atofighi.simple_snake_game.db.ServerMapManager;
import ir.atofighi.simple_snake_game.db.ServerScoreManager;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {

        // run server
        ServerMapManager mapManager = new ServerMapManager();
        ServerScoreManager scoreManager = new ServerScoreManager();
        GameServer gameServer = new GameServer();
        gameServer.run(mapManager, scoreManager);
    }

}



