package com.pengjunhan.bs;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddContactServlet")
public class AddContactServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // 创建ContactService实例
        ContactService contactService = new ContactService();

        // 调用添加联系人方法
        contactService.addContact(name, phone, address);

        // 关闭数据库连接
        contactService.closeConnection();

        // 重定向到联系人列表页面
        response.sendRedirect("ListContactsServlet");
    }
}

