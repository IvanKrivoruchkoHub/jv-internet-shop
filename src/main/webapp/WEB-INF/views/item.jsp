<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="item" scope="request" type="mate.academy.internetshop.model.Item"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Added item</title>
</head>
<body>
ADDED ITEM:
<br><br>Item Name : <c:out value="${item.name}" />
<br>Item Price: <c:out value="${item.price}" />
<br><br>
<form action="${pageContext.request.contextPath}/servlet/addItem">
    <button type="submit">Add item</button>
</form>
<form action="${pageContext.request.contextPath}/servlet/menu">
    <button type="submit">menu</button>
</form>
</body>
</html>
