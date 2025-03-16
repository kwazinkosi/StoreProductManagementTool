//package servlets;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import dao.IProductDAO;
//import dao.ProductDAOImpl;
//
//
//@WebServlet("/deleteProduct")
//public class DeleteProductServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String idParam = request.getParameter("id");
//
//        if (idParam != null) {
//            try {
//                int productId = Integer.parseInt(idParam);
//                IProductDAO productDAO = new ProductDAOImpl();
//                
//                // Delete the product
//                productDAO.deleteProduct(productId);
//                
//                // Redirect back to dashboard after deletion
//                response.sendRedirect("dashboard.jsp?message=Product Deleted Successfully");
//            } catch (NumberFormatException e) {
//                response.sendRedirect("dashboard.jsp?error=Invalid Product ID");
//            }
//        } else {
//            response.sendRedirect("dashboard.jsp?error=Missing Product ID");
//        }
//    }
//}
//
