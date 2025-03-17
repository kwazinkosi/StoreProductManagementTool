package servlets;

import java.io.IOException;
import intefaces.IFileStorage;
import service.ProductServiceImpl;
import service.RequestParser;

import exceptions.ValidationException;
import service.LocalFileStorageImpl;
import model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/addProduct")
@MultipartConfig(maxFileSize = 1024 * 1024, // 1MB per file
    maxRequestSize = 1024 * 1024 * 5, // 5MB total request
    fileSizeThreshold = 1024 * 512 // Store files in memory if less than 512KB
)
public class ProductServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductServiceImpl productServiceImpl;
    private IFileStorage fileStorage;
    private RequestParser requestParser;

    @Override
    public void init() throws ServletException {
        this.productServiceImpl = new ProductServiceImpl();
        this.fileStorage = new LocalFileStorageImpl(getServletContext());
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
                if (!fileStorage.isValidFileType(filePart)) {
                    throw new ValidationException("Invalid image file type");
                }
                String fileName = requestParser.getSubmittedFileName(filePart);
                String uniqueFileName = fileStorage.saveFile(filePart.getInputStream(), fileName);
                product.setImage(uniqueFileName); // Store only the filename
            }

            productServiceImpl.addProduct(product);
            response.sendRedirect("dashboard"); // Redirect to a servlet, not JSP

        } catch (ValidationException e) {
        	request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
        	request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        }
    }

    
}