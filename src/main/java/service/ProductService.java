package service;

import java.util.List;

import dao.IProductDAO;
import exceptions.ServiceException;
import intefaces.IProductValidator;
import model.Product;
//import utils.LoggingManager;


public class ProductService {
//	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	private final IProductDAO productDAO;

	private final IProductValidator validator;

	// Constructor injection
	public ProductService(IProductDAO productDAO, IProductValidator validator) {
		this.productDAO = productDAO;
		this.validator = validator;
	}

	public void addProduct(Product product) {
		try {

			validator.validate(product);
			productDAO.addProduct(product);
//			LoggingManager.info("Product added successfully: "+ product.getTitle());
		} catch (Exception e) {
//			LoggingManager.error("Error adding product: "+ e.getMessage(), e);
			throw new ServiceException("Failed to add product", e);
		}
	}

	public List<Product> getAllProducts() {
		try {
			return productDAO.getAllProducts();
		} catch (Exception e) {
//			LoggingManager.error("Error fetching products: "+ e.getMessage(), e);
			throw new ServiceException("Failed to fetch products", e);
		}
	}

	public void deleteProduct(int productId) {
		try {
			productDAO.deleteProduct(productId);
//			LoggingManager.info("Product deleted successfully: ID "+ productId);
		} catch (Exception e) {
//			LoggingManager.error("Error deleting product: "+ e.getMessage(), e);
			throw new ServiceException("Failed to delete product", e);
		}
	}
}
