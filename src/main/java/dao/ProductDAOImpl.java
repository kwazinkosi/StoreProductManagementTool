package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import config.HibernateUtil;
import model.Product;

public class ProductDAOImpl implements IProductDAO {
    
    private final SessionFactory factory = HibernateUtil.getSessionFactory();

    @Override
    public void addProduct(Product product) {
        
    	Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.persist(product);  // Using persist() instead of save() 
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to add product", e);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        try (Session session = factory.openSession()) {
            // Using modern JPA criteria API
            return session.createQuery("SELECT p FROM Product p", Product.class)
                         .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products", e);
        }
    }

    @Override
    public void deleteProduct(int productId) {
        
    	Transaction tx = null;
        try (Session session = factory.openSession()) {
            
        	tx = session.beginTransaction();
            Product product = session.get(Product.class, productId);
            if (product != null) {
                session.remove(product);  // Using remove() instead of delete()
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to delete product", e);
        }
    }


    @Override
    public void updateProduct(Product product) {
        
    	Transaction tx = null;
        try (Session session = factory.openSession()) {
            
        	tx = session.beginTransaction();
            session.merge(product);  // Using merge() instead of update()
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to update product", e);
        }
    }

    @Override
    public Product getProductById(int productId) {
    	
        try (Session session = factory.openSession()) {
            return session.get(Product.class, productId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch product with id: " + productId, e);
        }
    }
    
}