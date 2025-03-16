<%@ page import="java.util.List, model.Product" %>
<%
List<Product> products = (List<Product>) request.getAttribute("products");
Long totalSize = (Long) request.getAttribute("totalSize");
String username = (String) session.getAttribute("username");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Product Management Tool</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/dashboard.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <!-- Top Navigation Bar -->
    <div class="top-bar">
        <span>Product Management Tool</span>
        <div>
            Hi, <%= (username != null) ? username : "Guest" %>
            <a href="views/logout.jsp" class="logout">Logout</a>
        </div>
    </div>

    <!-- Product Form -->
    <div class="product-form">
        <h3>Enter Product Details</h3>
        <form action="AddProduct" method="post" enctype="multipart/form-data">
            <input type="text" name="title" placeholder="Title" required>
            <input type="number" name="quantity" placeholder="Quantity" required>
            <input type="text" name="size" placeholder="Size" required>
            <input type="file" name="image" required>
            <button type="submit" class="submit-btn">Add Product</button>
        </form>
    </div>

    <!-- Product List Table -->
    <table class="product-table">
        <tr>
            <th>S. No.</th>
            <th>Title</th>
            <th>Quantity</th>
            <th>Size</th>
            <th>Image</th>
            <th>Actions</th>
        </tr>
        <%
        int count = 1;
        if (products != null) {
            for (Product product : products) {
        %>
        <tr>
            <td><%= count++ %></td>
            <td><%= product.getTitle() %></td>
            <td><%= product.getQuantity() %></td>
            <td><%= product.getSize() %></td>
            <td>
                <img src="<%= (product.getImage() != null) ? product.getImage() : "default.png" %>" width="80">
            </td>
            <td class="actions">
                <a href="editProduct.jsp?id=<%= product.getId() %>">
                    <i class="fa fa-pencil edit"></i>
                </a>
                <a href="DeleteProductServlet?id=<%= product.getId() %>">
                    <i class="fa fa-trash delete"></i>
                </a>
            </td>
        </tr>
        <%
            }
        }
        %>
    </table>

    <!-- Display Total Image Size -->
    <div class="total-size">
        Total Uploaded Image Size: <%= totalSize != null ? totalSize : 0 %> KB
    </div>

</body>
</html>
