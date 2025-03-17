package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import model.Product;
import service.IProductService;
import service.LocalFileStorageImpl;
import service.ProductServiceImpl;
import service.RequestParser;
import exceptions.ValidationException;
import intefaces.IFileStorage;

@WebServlet("/updateProduct")
@MultipartConfig(maxFileSize = 1024 * 1024, // 1MB per file
    maxRequestSize = 1024 * 1024 * 5, // 5MB total request
    fileSizeThreshold = 1024 * 512 // Store files in memory if less than 512KB
)
public class UpdateProductServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IProductService productServiceImpl;
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
        	
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = productServiceImpl.getProductById(id);

            // Update product fields
            product.setTitle(request.getParameter("title"));
            product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            product.setSize(Integer.parseInt(request.getParameter("size")));

            // Handle file upload (if a new image is provided)
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                if (!fileStorage.isValidFileType(filePart)) {
                    throw new ValidationException("Invalid image file type");
                }
                // Delete the old image (if it exists)
                if (product.getImage() != null && !product.getImage().isEmpty()) {
                    fileStorage.deleteFile(product.getImage());
                }
                // Save the new image
                String fileName = requestParser.getSubmittedFileName(filePart);
                String uniqueFileName = fileStorage.saveFile(filePart.getInputStream(), fileName);
                product.setImage(uniqueFileName);
            }

            // Update the product in the database
            productServiceImpl.updateProduct(product);
            response.sendRedirect("dashboard"); // Redirect to dashboard after update

        } catch (ValidationException e) {
        	
        	request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
        	
        	request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        }
    }
}
