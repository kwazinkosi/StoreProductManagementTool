package servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("username") == null) {
			
			response.sendRedirect(request.getContextPath() + "/login.jsp?error=Please login first");
			return;
		}
		List<Product> products = productDAO.getAllProducts();
		// Calculate total image size
		long totalSizeKB = 0;
		for (Product product : products) {
			totalSizeKB += product.getSize();
		}

		
		System.out.println("totalSize = " + (totalSizeKB / 1024));
		request.setAttribute("products", products);
		request.setAttribute("totalSize", totalSizeKB / 1024); // Convert to KB
		request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}
}
