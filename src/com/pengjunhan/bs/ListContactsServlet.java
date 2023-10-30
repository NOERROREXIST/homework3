package com.pengjunhan.bs;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ListContactsServlet")
public class ListContactsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 创建ContactService实例
        ContactService contactService = new ContactService();

        // 获取联系人列表
        List<Contact> contacts = contactService.getAllContacts();

        // 设置联系人列表为请求属性
        request.setAttribute("contacts", contacts);

        // 关闭数据库连接
        contactService.closeConnection();

        // 转发到联系人列表页面
        request.getRequestDispatcher("listContacts.jsp").forward(request, response);
    }
}
