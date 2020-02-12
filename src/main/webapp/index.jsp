<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Welcome cat page</title>
</head>
<body>
<p>Hello, meow! <p>
<form action="${pageContext.request.contextPath}/roleFilter" method="post">
    <td>Name <input name="name" type="text" minlength="1" /> </td>
    <td>Password <input name="password" type="password" min=1 /> </td>
    <input type="submit" value="authorization" />
</form>
</body>
</html>
