<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Items</title>
</head>
<body>
All items:
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>DELETED</th>
        <th>ADDED TO BUCKET</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.id}" />
            </td>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteItem?item_id=${item.id}">DELETE</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/addItemToBucket?item_id=${item.id}">ADD</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<form action="${pageContext.request.contextPath}/servlet/bucket">
    <button type="submit">BUCKET</button>
</form>
<form action="${pageContext.request.contextPath}/servlet/menu">
    <button type="submit">MENU</button>
</form>
</body>
</html>
