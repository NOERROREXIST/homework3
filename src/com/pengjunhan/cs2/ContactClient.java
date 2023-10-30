package com.pengjunhan.cs2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ContactClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("个人通讯录系统");
                System.out.println("1. 添加联系人");
                System.out.println("2. 修改联系人");
                System.out.println("3. 删除联系人");
                System.out.println("4. 查看联系人列表");
                System.out.println("5. 退出");
                System.out.print("请选择操作：");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                String request = "";
                switch (choice) {
                    case 1:
                        System.out.print("请输入联系人姓名：");
                        String name = scanner.nextLine();
                        System.out.print("请输入联系人电话：");
                        String phone = scanner.nextLine();
                        System.out.print("请输入联系人地址：");
                        String address = scanner.nextLine();
                        request = "add;" + name + ";" + phone + ";" + address; // 构建请求
                        out.println(request); // 将请求发送给服务器
                        break;

                    case 2:
                        System.out.print("请输入要修改的联系人编号：");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("请输入新的联系人姓名：");
                        name = scanner.nextLine();
                        System.out.print("请输入新的联系人电话：");
                        phone = scanner.nextLine();
                        System.out.print("请输入新的联系人地址：");
                        address = scanner.nextLine();
                        request = "update;" + id + ";" + name + ";" + phone + ";" + address;

                        break;
                    case 3:
                        System.out.print("请输入要删除的联系人编号：");
                        id = scanner.nextInt();
                        request = "delete;" + id;
                        break;
                    case 4:
                        request = "list";
                        break;
                    case 5:
                        socket.close();
                        System.out.println("已退出。");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("无效的选项，请重新选择。");
                }

                out.println(request);

                String serverResponse = in.readLine();
                System.out.println("服务器响应: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



