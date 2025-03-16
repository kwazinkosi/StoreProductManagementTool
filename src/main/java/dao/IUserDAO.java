package dao;

import model.User;

public interface IUserDAO {

//	User findByUsernameAndPassword(String username, String password);

	User findByUsername(String username);

}
