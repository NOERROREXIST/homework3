<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact List</title>
</head>
<body>
<h1>Contact List</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Phone</th>
        <th>Address</th>
    </tr>
    <c:forEach items="${contacts}" var="contact">
        <tr>
            <td>${contact.id}</td>
            <td>${contact.name}</td>
            <td>${contact.phone}</td>
            <td>${contact.address}</td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="addContact.jsp">Add Contact</a>
</body>
</html>
