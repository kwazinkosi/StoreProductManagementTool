package dao;

import java.util.List;

import model.Product;


public interface IProductDAO {
	
    void addProduct(Product product);
    List<Product> getAllProducts();
    void deleteProduct(int productId);
	Product getProductById(int productId);
	void updateProduct(Product product);
}
