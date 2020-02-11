<%@ page import="service.ServiceUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Admin Cat</title>
</head>
<body>
<p>Hello, admin cat! <p>
<table border="5" cellspacing="0" cellpadding="20">
    <tr>
        <td>ID</td>
        <td>Name</td>
        <td>Age</td>
        <td>Password</td>
        <td>City</td>
        <td>Role</td>
    </tr>

    <c:forEach items="${ServiceUser.getInstance().getAllUserService()}" var="user">
        <tr>
            <td>${user.getId()}</td>
            <td>${user.getName()}</td>
            <td>${user.getAge()}</td>
            <td>${user.getPassword()}</td>
            <td>${user.getCity()}</td>
            <td>${user.getRole()}</td>
        </tr>
    </c:forEach>
</table>
</p>
<form action="${pageContext.request.contextPath}/admin/add" method="post">
    Name: <input name="name" type="text" minlength="1" />
    Age: <input name="age" type="number" min=1 />
    Password: <input name="password" type="password" min=1 />
    City: <input name="city" type="text" />
    Role: <input name="role" type="radio" value="user" />user
    <input name="role" type="radio" value="admin" />admin<br>
    <p>
        <input type="submit" value="addDB" />
</form>
</p>
<br><br>
<form action="${pageContext.request.contextPath}/admin/update" method="post">
    Id: <input name="id" type=number minlength="1" />
    <br></br>
    Old name: <input name="oldname" type="text" minlength="1" />
    New name: <input name="name" type="text" minlength="1" />
    Old password:<input name="oldpassword" type="password" min=1 />
    New password: <input name="password" type="password" min=1 />
    <br></br>
    New age: <input name="age" type="number" min=1 />
    New city: <input name="city" type="text" />
    New role: <input name="role" type="radio" value="user" />user
    <input name="role" type="radio" value="admin" />admin<br>
    <input type="submit" value="updateDB" />
</form>
</p>
<br><br>
<form action="${pageContext.request.contextPath}/admin/delete" method="post">
    Id: <input name="id" type=number minlength="1" />
    <input type="submit" value="delete" />
</form>
</body>
</html>
