package servlets;

import java.io.IOException;
import intefaces.IFileStorage;
import service.ProductService;
import service.ProductValidatorImpl;
import service.RequestParser;

import exceptions.ValidationException;
import intefaces.IProductValidator;
import service.LocalFileStorageImpl;
import dao.IProductDAO;
import dao.ProductDAOImpl;
import model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/AddProduct")
@MultipartConfig(
    maxFileSize = 1024 * 1024,   // 1MB per file
    maxRequestSize = 1024 * 1024 * 5,  // 5MB total request
    fileSizeThreshold = 1024 * 512  // Store files in memory if less than 512KB
)
public class ProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProductService productService;
    private IFileStorage fileStorage;
    private RequestParser requestParser;

    @Override
    public void init() throws ServletException {
        IProductDAO productDAO = new ProductDAOImpl();
        IProductValidator validator = new ProductValidatorImpl();
        this.productService = new ProductService(productDAO, validator);
        
        // Use an external upload directory
        String uploadDir = System.getProperty("catalina.base") + "/uploads";
        this.fileStorage = new LocalFileStorageImpl(uploadDir);

        this.requestParser = new RequestParser();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Product product = requestParser.parseProductRequest(request);

            // Handle file upload
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = requestParser.getSubmittedFileName(filePart);
                if (!fileStorage.isValidFileType(fileName)) {
                    throw new ValidationException("Invalid image file type");
                }
                String imagePath = fileStorage.saveFile(filePart.getInputStream(), fileName);
                product.setImage(imagePath);
            }

            productService.addProduct(product);
            response.sendRedirect("dashboard"); // Redirect to a servlet, not JSP

        } catch (ValidationException e) {
            handleError(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            handleError(response, "Server error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleError(HttpServletResponse response, String message, int statusCode)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
