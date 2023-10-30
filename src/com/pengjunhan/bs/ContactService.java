package com.pengjunhan.bs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ContactService {

    private Connection connection;
    private ContactDAO contactDAO;

    public ContactService() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///contacts3?useSSL=false", "root", "your_password");
            contactDAO = new ContactDAO(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addContact(String name, String phone, String address) {
        Contact contact = new Contact(0, name, phone, address);
        try {
            contactDAO.addContact(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContact(int id, String name, String phone, String address) {
        Contact contact = new Contact(id, name, phone, address);
        try {
            contactDAO.updateContact(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(int id) {
        try {
            contactDAO.deleteContact(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = null;
        try {
            contacts = contactDAO.getAllContacts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}

