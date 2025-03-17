<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!-- <!DOCTYPE html> -->
<html>
<head>
<title>Product Management Tool</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/dashboard.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<!-- add link to script file -->
<%-- <script src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script> --%>
<body>
	<c:if test="${empty sessionScope.username}">
		<%
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		%>
	</c:if>


	<!-- Top Navigation Bar -->
	<div class="top-bar">
		<span>Product Management Tool</span>
		<div>
			Hi,
			<c:out value="${sessionScope.username}" />
			<a class="logout" href="${pageContext.request.contextPath}/logout">Logout</a>
		</div>
	</div>

	<!-- Error Display Section -->
	<c:if test="${not empty error}">
		<div class="error-message">
			<c:out value="${error}" />
		</div>
	</c:if>
	<c:if test="${not empty param.error}">
		<div class="error-message">
			<c:out value="${param.error}" />
		</div>
	</c:if>
	
	<!-- Product Form -->
	<div class="product-form">
		<h3 id="formTitle">Enter Product Details</h3>
		<form id="productForm"
			action="${pageContext.request.contextPath}/addProduct" method="post"
			enctype="multipart/form-data">
			<input type="hidden" id="productId" name="id"> <input
				type="text" id="productTitle" name="title" placeholder="Title"
				required> <input type="number" id="productQuantity"
				name="quantity" placeholder="Quantity" required> <input
				type="number" id="productSize" name="size" placeholder="Size"
				required> <input type="file" id="productImage" name="image"
				accept="image/*">
			<button type="submit" id="submitButton" class="submit-btn">Add
				Product</button>
			<button type="button" id="cancelButton" class="cancel-btn"
				onclick="resetForm()" style="display: none;">Cancel</button>
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
		<c:forEach items="${products}" var="product" varStatus="loop">
			<tr>
				<td>${loop.index + 1}</td>
				<td><c:out value="${product.title}" /></td>
				<td><c:out value="${product.quantity}" /></td>
				<td><c:out value="${product.size}" /></td>
				<td><c:set var="imagePath"
						value="${pageContext.request.contextPath}/uploads/product-images/${product.image}" />
					<c:set var="defaultImage"
						value="${pageContext.request.contextPath}/images/default.png" />
					<img src="${not empty product.image ? imagePath : defaultImage}"
					width="100"></td>

				<td class="actions">
					<button type="button" class="icon-btn edit-btn"
						data-id="${product.id}"
						data-title="<c:out value='${product.title}'/>"
						data-quantity="${product.quantity}" data-size="${product.size}"
						data-image="${product.image}">

						<!-- 					<button type="button" class="icon-btn" -->
						<%-- 						onclick="editProduct('${product.id}', '${product.title}', '${product.quantity}', '${product.size}', '${product.image}')"> --%>
						<i class="fa fa-pencil edit"></i>
					</button>
					<form action="${pageContext.request.contextPath}/DeleteProduct"
						method="POST" style="display: inline;">
						<input type="hidden" name="id" value="${product.id}">
						<button type="submit" class="icon-btn">
							<i class="fa fa-trash delete"></i>
						</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

	<!-- Display Total Image Size -->
	<div class="total-size">
		Total Uploaded Image Size:
		<fmt:formatNumber value="${totalSizeKB}" pattern="#,###" />
		KB
	</div>

	<script>
	document.addEventListener('DOMContentLoaded', function() {
	    // Handle edit buttons
	    document.querySelectorAll('.edit-btn').forEach(button => {
	        button.addEventListener('click', function() {
	            const product = {
	                id: this.dataset.id,
	                title: this.dataset.title,
	                quantity: this.dataset.quantity,
	                size: this.dataset.size,
	                image: this.dataset.image
	            };
	            editProduct(product);
	        });
	    });

	    // Handle cancel button
	    document.getElementById("cancelButton").addEventListener('click', resetForm);
	});

	function editProduct(product) {
	    const form = document.getElementById("productForm");
	    console.log("Product: ", product);
	    // Update form action for updates
	    form.action = "${pageContext.request.contextPath}/updateProduct";

	    // Update form fields
	    document.getElementById("productId").value = product.id;
	    document.getElementById("productTitle").value = product.title;
	    document.getElementById("productQuantity").value = product.quantity;
	    document.getElementById("productSize").value = product.size;

	    // Update form title and button
	    document.getElementById("formTitle").innerText = "Update Product Details";
	    document.getElementById("submitButton").innerText = "Update Product";
	    document.getElementById("cancelButton").style.display = "inline-block";

	    // Handle image preview
	    const imageContainer = document.getElementById("productImage").parentNode;
	    const existingPreview = document.getElementById("imagePreview");
	    if (existingPreview) existingPreview.remove();

	    if (product.image) {
	        const imgPreview = document.createElement("img");
	        imgPreview.id = "imagePreview";
	        imgPreview.src = "${pageContext.request.contextPath}/uploads/product-images/" + product.image;
	        imgPreview.width = 80;
	        imgPreview.style.margin = "10px 0";
	        imageContainer.insertBefore(imgPreview, document.getElementById("productImage"));
	    }
	}

	function resetForm() {
	    const form = document.getElementById("productForm");
	    
	    // Reset form fields
	    form.reset();
	    document.getElementById("productId").value = ""; // Clear hidden ID field

	    // Reset form title and button
	    document.getElementById("formTitle").innerText = "Enter Product Details";
	    document.getElementById("submitButton").innerText = "Add Product";
	    document.getElementById("cancelButton").style.display = "none";

	    // Reset form action for new products
	    form.action = "${pageContext.request.contextPath}/addProduct";

	    // Remove image preview
	    const preview = document.getElementById("imagePreview");
	    if (preview) preview.remove();
	}
	</script>

</body>
</html>