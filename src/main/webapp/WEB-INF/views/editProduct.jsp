<%@ page import="model.Product, dao.IProductDAO, dao.ProductDAOImpl" %>
<%
    String idParam = request.getParameter("id");
    int productId = (idParam != null) ? Integer.parseInt(idParam) : -1;

    IProductDAO productDAO = new ProductDAOImpl();
    Product product = productDAO.getProductById(productId);

    if (product == null) {
        response.sendRedirect("dashboard.jsp"); // Redirect if product not found
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
    <link rel="stylesheet" type="text/css" href="css/dashboard.css">
</head>
<body>

    <!-- Top Navigation Bar -->
    <div class="top-bar">
        <span>Edit Product</span>
        <div>
            Hi, <%= session.getAttribute("username") %>
            <a href="logout.jsp" class="logout">Logout</a>
        </div>
    </div>

    <!-- Edit Product Form -->
    <div class="product-form">
        <h3>Update Product Details</h3>
        <form action="UpdateProductServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="<%= product.getId() %>">

            <label>Title:</label>
            <input type="text" name="title" value="<%= product.getTitle() %>" required>

            <label>Quantity:</label>
            <input type="number" name="quantity" value="<%= product.getQuantity() %>" required>

            <label>Size:</label>
            <input type="text" name="size" value="<%= product.getSize() %>" required>

            <label>Current Image:</label>
            <img src="<%= product.getImage() %>" width="100">
            
            <label>Upload New Image (optional, max 1MB):</label>
            <input type="file" name="image">

            <button type="submit" class="submit-btn">Update Product</button>
        </form>
    </div>

</body>
</html>
