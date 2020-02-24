package service;

import dao.UserDAO;
import dao.UserDaoFactory;
import exception.DBException;
import model.User;

import java.util.List;

public class ServiceUser {

    private static ServiceUser serviceUser;

    private final UserDAO userDAO;

    private ServiceUser(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static ServiceUser getInstance() {
        if (serviceUser == null) {
            UserDAO createUserDAO = new UserDaoFactory().getUserDAO();
            serviceUser = new ServiceUser(createUserDAO);
        }
        return serviceUser;
    }

    public List<User> getAllUserService() {
        List<User> users = null;
        try {
            users = userDAO.getAllUser();
        } catch (DBException e) {
            //ignore
        }
        return users;
    }

    public User getUserByIdService(long id) {
        User user = null;
        try {
            user = userDAO.getUserById(id);
        } catch (DBException e) {

        }
        return user;
    }

    public User getUserWithNameAndPasswordService(String name, String password) {
        User user = null;
        try {
            if (name != null && password != null)
                user = userDAO.getUserWithNameAndPassword(name, password);
        } catch (DBException e) {

        }
        return user;
    }

    public void updateUserByIdService(long id, User user) {
        try {
            if (user != null) {
                userDAO.updateUserById(id, user);
            }
        } catch (DBException e) {

        }
    }

    public void addUserService(User user) {
        try {
            if (user != null) {
                userDAO.addUser(user);
            }
        } catch (DBException e) {

        }
    }

    public void deleteUserById(long id) {
        try {
            userDAO.deleteUserById(id);
        } catch (DBException e) {

        }
    }

}
