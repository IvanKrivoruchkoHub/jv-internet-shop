<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Item</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/servlet/addItem" method="post">
    <div class="container">
        <p>Please fill in this form to create an item.</p>
        <hr>

        <label for="name"><b>Name</b></label>
        <input type="text" placeholder="Enter name" name="name" required>
        <br>
        <label for="price"><b>Price</b></label>
        <input type="text" placeholder="Enter price" name="price" required>
        <hr>
        <button type="submit">ADD</button>
    </div>
</form>
<form action="${pageContext.request.contextPath}/menu">
    <button type="submit">MENU</button>
</form>
</body>
</html>
