package dao;

import util.DBHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserDaoFactory {

    public UserDAO getUserDAO() {
        UserDAO userDAO = null;
        Properties property = new Properties();
        String file = "../webapps/userCRUD_war/WEB-INF/classes/config.properties";
        try (FileInputStream fis = new FileInputStream(new File(file).getAbsolutePath())) {
            property.load(fis);
        } catch (IOException e) {
            System.out.println("Не удалось обнаружить файл");
        }

        switch (property.getProperty("typeDao")) {
            case ("JDBC"):{
                userDAO = new UserJdbcDAO(DBHelper.getInstance().getConnectionForJdbcDAO());
                break;
            }
            case ("Hibernate"):{
                userDAO = new UserHibernateDAO(DBHelper.getInstance().getConfigurationForHibernateDAO());
                break;
            }
        }

        return userDAO;
    }
}
