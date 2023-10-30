package com.pengjunhan.cs3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDataAccess {
    public static int addContact(Connection connection, String name, String phone, String address) throws SQLException {
        String sql = "INSERT INTO contact_info (name, phone, address) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);
            return preparedStatement.executeUpdate();
        }
    }

    public static int updateContact(Connection connection, String id, String name, String phone, String address) throws SQLException {
        String sql = "UPDATE contact_info SET name = ?, phone = ?, address = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, address);
            preparedStatement.setInt(4, Integer.parseInt(id));
            return preparedStatement.executeUpdate();
        }
    }

    public static int deleteContact(Connection connection, String id) throws SQLException {
        String sql = "DELETE FROM contact_info WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            return preparedStatement.executeUpdate();
        }
    }

    public static String listContacts(Connection connection) throws SQLException {
        String sql = "SELECT * FROM contact_info";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
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
