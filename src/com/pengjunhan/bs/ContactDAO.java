package com.pengjunhan.bs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    private Connection connection;

    public ContactDAO(Connection connection) {
        this.connection = connection;
    }

    public void addContact(Contact contact) throws SQLException {
        String sql = "INSERT INTO contact (name, phone, address) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhone());
            preparedStatement.setString(3, contact.getAddress());
            preparedStatement.executeUpdate();
        }
    }

    public void updateContact(Contact contact) throws SQLException {
        String sql = "UPDATE contact SET name=?, phone=?, address=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhone());
            preparedStatement.setString(3, contact.getAddress());
            preparedStatement.setInt(4, contact.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteContact(int id) throws SQLException {
        String sql = "DELETE FROM contact WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<Contact> getAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contact";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                Contact contact = new Contact(id, name, phone, address);
                contacts.add(contact);
            }
        }
        return contacts;
    }
}
