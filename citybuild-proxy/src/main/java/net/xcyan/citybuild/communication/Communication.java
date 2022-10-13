package net.xcyan.citybuild.communication;

import net.xcyan.citybuild.Citybuild;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Communication {

    private final Citybuild citybuild;

    private ServerSocket serverSocket;

    private Map<String, Socket> socketMap;

    public Communication(Citybuild citybuild) {
        this.citybuild = citybuild;

        this.socketMap = new HashMap<>();
    }

    public void startServer() {
        try {
            this.serverSocket = new ServerSocket(25576);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Socket socket = serverSocket.accept();

            citybuild.logger().info("Registered Socket " + socket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
