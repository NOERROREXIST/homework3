package com.pengjunhan.cs3;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ContactServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("服务器已启动，等待客户端连接...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端连接成功");

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/contacts2?useSSL=false", "root", "PENGJUNHAN020303");
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String clientRequest = in.readLine();
                String serverResponse = ContactService.processRequest(connection, clientRequest);
                out.println(serverResponse);

                clientSocket.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
