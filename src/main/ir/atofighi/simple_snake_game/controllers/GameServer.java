package ir.atofighi.simple_snake_game.controllers;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;
import ir.atofighi.simple_snake_game.db.ServerMapManager;
import ir.atofighi.simple_snake_game.db.ServerScoreManager;
import ir.atofighi.simple_snake_game.models.Map;
import ir.atofighi.simple_snake_game.models.Point;
import ir.atofighi.simple_snake_game.models.Snake;
import ir.atofighi.simple_snake_game.utils.Direction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class GameServer {
    public void run(ServerMapManager mapManager, ServerScoreManager scoreManager) {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(GameConfiguration.serverPort);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + GameConfiguration.serverPort + " or listening for a connection");
            System.out.println(e.getMessage());
        }

        while (true) {

            try {
                clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                new Thread(() -> {
                    try {
                        String inputLine, outputLine;

//                    out.println("hello!");


                        while ((inputLine = in.readLine()) != null) {
                            System.out.println(inputLine);
                            if (inputLine.equals("exit")) {
                                out.println("bye!");
                                break;
                            } else if (inputLine.equals("getMapCount")) {
                                out.println(mapManager.size());
                            } else if (inputLine.startsWith("getMap ")) {
                                int mapNumber = Integer.parseInt(inputLine.substring(7));
                                if (mapNumber >= mapManager.size()) {
                                    out.println("arrayOutOfBoundException");
                                } else {
                                    Map map = mapManager.getMap(mapNumber);
                                    out.print(map.getWidth() + " ");
                                    out.print(map.getHeight() + " ");
                                    Snake snake = map.getSnakes().get(0);
                                    out.print(snake.getPoints().get(0).getX() + " ");
                                    out.print(snake.getPoints().get(0).getY() + " ");
                                    for (int i = 0; i < Direction.values().length; i++) {
                                        if (snake.getDirection() == Direction.values()[i]) {
                                            out.print(i + " ");
                                        }
                                    }

                                    List<Point> bombs = map.getBlockedPoints();
                                    out.print(bombs.size() + " ");
                                    for (Point bomb : bombs) {
                                        out.print(bomb.getX() + " ");
                                        out.print(bomb.getY() + " ");
                                    }
                                    out.println();
                                }
                            } else if (inputLine.equals("getMaxScore")) {
                                out.println(scoreManager.getScore());
                            } else if (inputLine.startsWith("setMaxScore ")) {
                                int score = Integer.parseInt(inputLine.substring(12));
                                scoreManager.setScore(score);
                                scoreManager.write();
                                out.println("newScore " + scoreManager.getScore());
                            } else {
                                out.println("unknownCommand");
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port "
                        + GameConfiguration.serverPort + " or listening for a connection");
                System.out.println(e.getMessage());

            }

//                break;
        }


    }
}
