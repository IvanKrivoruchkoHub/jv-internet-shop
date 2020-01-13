<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/servlet/registration" method="post">
    <div class="container">
        <p>Please fill in this form to create an account.</p>
        <hr>

        <label for="login"><b>Email</b></label>
        <input type="text" placeholder="Enter login" name="login" required>

        <label for="name"><b>Name</b></label>
        <input type="text" placeholder="Enter Name" name="name" required>

        <label for="surname"><b>Surname</b></label>
        <input type="text" placeholder="Enter Surname" name="surname" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" required>

        <label for="psw-repeat"><b>Repeat Password</b></label>
        <input type="password" placeholder="Repeat Password" name="psw-repeat" required>
        <hr>

        <button type="submit">Register</button>
    </div>
</form>
<form action="${pageContext.request.contextPath}/servlet/menu">
    <button type="submit">MENU</button>
</form>
</body>
</html>
