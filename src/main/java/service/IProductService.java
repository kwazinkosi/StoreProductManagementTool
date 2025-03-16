package service;

import java.util.List;

import model.Product;

public interface IProductService {

	void addProduct(Product product);

	List<Product> getAllProducts();

	boolean deleteProduct(int productId);

}
