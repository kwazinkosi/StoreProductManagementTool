package service;

import java.util.List;

import dao.IProductDAO;
import dao.ProductDAOImpl;
import exceptions.ServiceException;
import intefaces.IProductValidator;
import model.Product;
//import utils.LoggingManager;


public class ProductServiceImpl implements IProductService {
//	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	private final IProductDAO productDAO;

	private final IProductValidator validator;

	// Constructor injection
	public ProductServiceImpl() {
		
		this.productDAO = new ProductDAOImpl();
		this.validator = new ProductValidatorImpl();
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

	public boolean deleteProduct(int productId) {
		
		try {
			Product product = productDAO.getProductById(productId);
	        if (product != null) {
	            productDAO.deleteProduct(productId);
	            return true;
	        }
		} catch (Exception e) {
//			LoggingManager.error("Error deleting product: "+ e.getMessage(), e);
			throw new ServiceException("Failed to delete product", e);
		}
	    return false;
	}
}
