<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Add Contact</title>
</head>
<body>
<h1>Add Contact</h1>
<form action="AddContactServlet" method="post">
    <label for="name">Name:</label>
<input type="text" id="name" name="name"><br>
    <label for="phone">Phone:</label>
<input type="text" id="phone" name="phone"><br>
    <label for="address">Address:</label>
<input type="text" id="address" name="address"><br>
    <input type="submit" value="Add Contact">
    </form>
    </body>
    </html>
