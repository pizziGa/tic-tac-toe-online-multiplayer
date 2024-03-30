package org.example;

import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) {
        int port = 3000;
        try {
            ServerSocket srv = new ServerSocket(port);
            System.out.println("Waiting for connection...");

            Socket sck = srv.accept();
            Socket sck2 = srv.accept();
            threadServer thread = new threadServer(sck, sck2);
            thread.start();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
