package com.pengjunhan.cs2;

import java.io.*;
import java.net.*;
import java.sql.*;

public class ContactServer {

    private static final String DB_URL = "jdbc:mysql:///contacts?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "PENGJUNHAN020303";

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

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
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String clientRequest = in.readLine();
                System.out.println("收到客户端请求: " + clientRequest); // 添加这行来检查请求是否被接收

                String serverResponse = processRequest(connection, clientRequest);
                System.out.println("服务器响应: " + serverResponse); // 添加这行来检查响应

                out.println(serverResponse);

                clientSocket.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }


        private String processRequest(Connection connection, String clientRequest) throws SQLException {
            String[] requestParts = clientRequest.split(";");
            String action = requestParts[0];

            if ("add".equals(action)) {
                return addContact(connection, requestParts[1], requestParts[2], requestParts[3]);
            } else if ("update".equals(action)) {
                return updateContact(connection, requestParts[1], requestParts[2], requestParts[3], requestParts[4]);
            } else if ("delete".equals(action)) {
                return deleteContact(connection, requestParts[1]);
            } else if ("list".equals(action)) {
                return listContacts(connection);
            } else {
                return "无效的请求";
            }
        }

        private String addContact(Connection connection, String name, String phone, String address) throws SQLException {
            // 执行插入联系人的操作并返回结果
            String sql = "INSERT INTO contact_info (name, phone, address) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return "联系人已成功添加";
            } else {
                return "添加联系人失败";
            }
        }

        private String updateContact(Connection connection, String id, String name, String phone, String address) throws SQLException {
            // 执行更新联系人的操作并返回结果
            String sql = "UPDATE contact_info SET name = ?, phone = ?, address = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);
            preparedStatement.setInt(4, Integer.parseInt(id));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return "联系人已成功修改";
            } else {
                return "修改联系人失败";
            }
        }

        private String deleteContact(Connection connection, String id) throws SQLException {
            // 执行删除联系人的操作并返回结果
            String sql = "DELETE FROM contact_info WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return "联系人已成功删除";
            } else {
                return "删除联系人失败";
            }
        }

        private String listContacts(Connection connection) throws SQLException {
            // 查询联系人并返回结果
            String sql = "SELECT * FROM contact_info";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder result = new StringBuilder();
            result.append("联系人列表：\n");
            result.append("编号\t姓名\t电话\t地址\n");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                result.append(id).append("\t").append(name).append("\t").append(phone).append("\t").append(address).append("\n");
            }

            return result.toString();
        }
    }
}

