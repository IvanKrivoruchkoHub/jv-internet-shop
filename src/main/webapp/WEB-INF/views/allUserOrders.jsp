<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.model.Order>"/>

<html>
<head>
    <title>All Orders</title>
</head>
<body>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>Items</th>
        <th>DELETE</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.orderId}" />
            </td>
            <td>
                <table border="1">
                    <tr>
                        <th>Item Name</th>
                        <th>Price</th>
                    </tr>
                    <c:forEach var="item" items="${order.items}">
                        <tr>
                            <td>
                                <c:out value="${item.name}"/>
                            </td>
                            <td>
                                <c:out value="${item.price}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteOrder?order_id=${order.orderId}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/servlet/menu">
    <button type="submit">MENU</button>
</form>
</body>
</html>
