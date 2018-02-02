package ir.atofighi.simple_snake_game.controllers;

import ir.atofighi.simple_snake_game.configs.GameConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClient extends Thread {
    public void run() {

        String hostName = "127.0.0.1";
        int portNumber = GameConfiguration.serverPort;
        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        ) {
            String fromServer;
            String fromUser;
            fromUser = getCommand();

            if (fromUser != null) {
                out.println(fromUser);
            }
            while ((fromServer = in.readLine()) != null) {

                if (fromServer.equals("bye!"))
                    break;
                setResponse(fromServer);

                fromUser = getCommand();

                if (fromUser != null) {
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName + ":" + portNumber);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName + ":" + portNumber);
            System.exit(1);
        }

    }

    private String command = null;
    private String response = null;

    private synchronized String getCommand() {
        while (command == null) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        String result = command;
        command = null;
        return result;
    }

    private synchronized void setResponse(String response) {
        this.response = response;

        notifyAll();
    }

    public synchronized String request(String command) {
        this.command = command;
        notifyAll();
        while (response == null) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        String result = response;
        response = null;
        return result;
    }
}
