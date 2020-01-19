<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MENU</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login">
    <button type="submit">Login</button>
</form>
<br>

<form action="${pageContext.request.contextPath}/logout">
    <button type="submit">Logout</button>
</form>
<br>

<form action="${pageContext.request.contextPath}/registration">
    <button type="submit">Registration</button>
</form>
<br>

<form action="${pageContext.request.contextPath}/servlet/addItem">
    <button type="submit">Add Item</button>
</form>
<br>

<form action="${pageContext.request.contextPath}/servlet/allItems">
    <button type="submit">Show all items</button>
</form>
<br>

<form action="${pageContext.request.contextPath}/servlet/allUsers">
    <button type="submit">Show all users</button>
</form>
<br>

<form action="${pageContext.request.contextPath}/servlet/allUserOrders">
    <button type="submit">show all Orders</button>
</form>
<br>

<form action="${pageContext.request.contextPath}/servlet/bucket">
    <button type="submit">Show bucket</button>
</form>
</body>
</html>
