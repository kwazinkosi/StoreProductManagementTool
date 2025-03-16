package service;

import dao.UserDAOImpl;
import model.User;
import dao.IUserDAO;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements IUserService {
    private final IUserDAO userDAO =  new UserDAOImpl();

    @Override
    public boolean authenticate(String username, String password) {
    	
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return false;
        }
        return BCrypt.checkpw(password, user.getPasswordHash());
    }
}