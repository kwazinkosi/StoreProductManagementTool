package servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.IProductDAO;
import dao.ProductDAOImpl;
import model.Product;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IProductDAO productDAO = new ProductDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDAO.getAllProducts();

        // Calculate total image size
        long totalSize = 0;
        for (Product product : products) {
            totalSize += product.getSize();
        }

        request.setAttribute("products", products);
        request.setAttribute("totalSize", totalSize / 1024); // Convert to KB
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}
