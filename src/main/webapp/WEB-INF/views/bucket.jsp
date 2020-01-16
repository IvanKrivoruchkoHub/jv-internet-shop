<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="bucket" scope="request" type="mate.academy.internetshop.model.Bucket"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
</head>
<body>
bucket id : ${bucket.bucketId}
<table border="1">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>DELETED</th>
    </tr>
    <c:forEach var="item" items="${bucket.items}">
        <tr>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/servlet/deleteItemFromBucket?item_id=${item.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<form action="${pageContext.request.contextPath}/servlet/completeOrder">
    <button type="submit">CHECKOUT</button>
</form>
<form action="${pageContext.request.contextPath}/menu">
    <button type="submit">MENU</button>
</form>
</html>
