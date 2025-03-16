package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.IProductService;
import service.ProductServiceImpl;

@WebServlet("/DeleteProduct")
public class DeleteProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            String idParam = request.getParameter("id");

            if (idParam == null || idParam.isEmpty()) {
                session.setAttribute("error", "Missing product ID");
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }

            int productId = Integer.parseInt(idParam);

            boolean isDeleted = productService.deleteProduct(productId);

            if (isDeleted) {
                session.setAttribute("success", "Product deleted successfully");
            } else {
                session.setAttribute("error", "Product not found");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid product ID");
        } catch (Exception e) {
            session.setAttribute("error", "Server error occurred");
        }

        // Redirect to dashboard after processing the request
        response.sendRedirect("dashboard");
    }
}
