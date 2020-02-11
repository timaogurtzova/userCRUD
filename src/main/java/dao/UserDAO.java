package dao;

import exception.DBException;
import model.User;
import java.util.List;

public interface UserDAO {

    List<User> getAllUser() throws DBException;

    User getUserWithNameAndPassword(String name, String password) throws DBException;

    void updateUserById(long id, User newParameterUser) throws DBException;

    void addUser(User user) throws DBException;

    void deleteUserById(long id) throws DBException;
}
