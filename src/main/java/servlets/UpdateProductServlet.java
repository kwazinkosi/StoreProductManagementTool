//package servlets;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//
//import java.io.IOException;
//import model.Product;
//import dao.IProductDAO;
//import dao.ProductDAOImpl;
//
//@WebServlet("/updateProduct")
//@MultipartConfig(maxFileSize = 1024 * 1024) // 1MB limit
//public class UpdateProductServlet extends HttpServlet {
//    
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        String title = request.getParameter("title");
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        String size = request.getParameter("size");
//
//        Part filePart = request.getPart("image");
//        String fileName = filePart.getSubmittedFileName();
//        String imagePath = "uploads/" + fileName;
//
//        IProductDAO productDAO = new ProductDAOImpl();
//        Product product = productDAO.getProductById(id);
//        
//        if (product == null) {
//            response.sendRedirect("dashboard.jsp");
//            return;
//        }
//
//        product.setTitle(title);
//        product.setQuantity(quantity);
//        int s = Integer.parseInt(size);
//        product.setSize(s);
//
//        // If a new image is uploaded, update it
//        if (filePart.getSize() > 0) {
//            filePart.write(getServletContext().getRealPath("/") + imagePath);
//            product.setImage(imagePath);
//        }
//
//        productDAO.updateProduct(product);
//        response.sendRedirect("dashboard.jsp");
//    }
//}
//
