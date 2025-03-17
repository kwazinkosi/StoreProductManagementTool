/**
 * Dashboard JS - Product Management Functions
 */

// Product Form Management
const ProductFormManager = {
  // DOM element references
  elements: {
    form: () => document.getElementById("productForm"),
    formTitle: () => document.getElementById("formTitle"),
    idField: () => document.getElementById("productId"),
    titleField: () => document.getElementById("productTitle"),
    quantityField: () => document.getElementById("productQuantity"),
    sizeField: () => document.getElementById("productSize"),
    imageInput: () => document.getElementById("productImage"),
    submitButton: () => document.getElementById("submitButton")
  },
  
  // Configure form for creating a new product
  setupForCreate: function() {
    this.elements.formTitle().innerText = "Add New Product";
    this.elements.form().reset();
    this.elements.idField().value = "";
    this.elements.submitButton().innerText = "Add Product";
    this.elements.form().action = "addProduct";
    
    // Remove any existing preview image
    this.removePreviewImage();
  },
  
  // Configure form for editing an existing product
  setupForEdit: function(product) {
    // Set form title and action
    this.elements.formTitle().innerText = "Update Product Details";
    this.elements.form().action = "updateProduct";
    
    // Populate form fields
    this.elements.idField().value = product.id;
    this.elements.titleField().value = product.title;
    this.elements.quantityField().value = product.quantity;
    this.elements.sizeField().value = product.size;
    this.elements.submitButton().innerText = "Update Product";
    
    // Remove any existing preview before adding a new one
    this.removePreviewImage();
    
    // Add image preview if available
    if (product.image && product.image !== "null") {
      this.addImagePreview(product.image);
    }
  },
  
  // Add image preview to the form
  addImagePreview: function(imageUrl) {
    const imgPreview = document.createElement("img");
    imgPreview.src = imageUrl;
    imgPreview.width = 80;
    imgPreview.style.display = "block";
    imgPreview.id = "previewImage";
    imgPreview.classList.add("mt-2", "mb-2");
    
    // Insert before the image input field
    const imageField = this.elements.imageInput();
    if (imageField) {
      imageField.parentNode.insertBefore(imgPreview, imageField);
    } else {
      // Fallback to appending to the form
      this.elements.form().appendChild(imgPreview);
    }
  },
  
  // Remove existing preview image
  removePreviewImage: function() {
    const existingPreview = document.getElementById("previewImage");
    if (existingPreview) {
      existingPreview.parentNode.removeChild(existingPreview);
    }
  }
};

/**
 * Edit Product Function - Called from the UI
 */
function editProduct(id, title, quantity, size, image) {
  ProductFormManager.setupForEdit({
    id: id,
    title: title,
    quantity: quantity,
    size: size,
    image: image
  });
}

/**
 * Reset form to create new product
 */
function newProduct() {
  ProductFormManager.setupForCreate();
}

// Initialize when DOM is fully loaded
document.addEventListener("DOMContentLoaded", function() {
  // Any initialization code can go here
  console.log("Dashboard product management initialized");
});