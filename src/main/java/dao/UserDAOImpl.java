// dao/impl/UserDAOImpl.java
package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import config.HibernateUtil;
import model.User;

public class UserDAOImpl implements IUserDAO {
	
	 private final SessionFactory factory = HibernateUtil.getSessionFactory();
	
	 @Override
	 public User findByUsername(String username) {
	     try (Session session = factory.openSession()) {
	         Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
	         query.setParameter("username", username);
	         return query.uniqueResult();
	     } catch (Exception e) {
	         throw new RuntimeException("Failed to fetch user", e);
	     }
	 }

}