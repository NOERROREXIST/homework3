package com.pengjunhan.cs3;

import java.sql.Connection;
import java.sql.SQLException;

public class ContactService {
    public static String processRequest(Connection connection, String clientRequest) {
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

    private static String addContact(Connection connection, String name, String phone, String address) {
        try {
            int result = ContactDataAccess.addContact(connection, name, phone, address);
            if (result > 0) {
                return "联系人已成功添加";
            } else {
                return "添加联系人失败";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "添加联系人失败";
        }
    }

    private static String updateContact(Connection connection, String id, String name, String phone, String address) {
        try {
            int result = ContactDataAccess.updateContact(connection, id, name, phone, address);
            if (result > 0) {
                return "联系人已成功修改";
            } else {
                return "修改联系人失败";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "修改联系人失败";
        }
    }

    private static String deleteContact(Connection connection, String id) {
        try {
            int result = ContactDataAccess.deleteContact(connection, id);
            if (result > 0) {
                return "联系人已成功删除";
            } else {
                return "删除联系人失败";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "删除联系人失败";
        }
    }

    private static String listContacts(Connection connection) {
        try {
            return ContactDataAccess.listContacts(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            return "获取联系人列表失败";
        }
    }
}
