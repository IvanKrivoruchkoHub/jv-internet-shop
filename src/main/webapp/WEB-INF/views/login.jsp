<jsp:useBean id="errorMsg" scope="request" class="java.lang.String"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <div>${errorMsg}</div>
    <div class="container">
        <h1>Login</h1>
        <hr>
        <label for="login"><b>Login</b></label>
        <input type="text" placeholder="Enter login" name="login" required>

        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>
        <hr>

        <button type="submit" class="registerbtn">Login</button>
    </div>
</form>
<div class="container signin">
    <p>Don`t have an account? <a href="${pageContext.request.contextPath}/registration">Sign up</a>.</p>
</div>
<form action="${pageContext.request.contextPath}/menu">
    <button type="submit">MENU</button>
</form>
</body>
</html>
