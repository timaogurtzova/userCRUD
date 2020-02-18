<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Registration Cat</title>
</head>
<body>
    <p> Do you want to register? Here goes! <p>
    <p> But only admin can register an admin. User-cats are registered here</p>
    <form action="${pageContext.request.contextPath}/user/registration" method="post">
        Name <input name="name" type="text" minlength="1" />
        Password <input name="password" type="password" min=1 />
        Age: <input name="age" type="number" min=1 />
        City: <input name="city" type="text" />
        <input type="submit" value="register me" />
    </form>
</body>
</html>
