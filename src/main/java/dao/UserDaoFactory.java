package dao;

import util.DBHelper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserDaoFactory {

    public UserDAO getUserDAO() {
        UserDAO userDAO = null;
        Properties property = new Properties();
        //String file = new File("../webapps/userCRUD_war/WEB-INF/classes/config.properties").getAbsolutePath();

        try (InputStream is = UserDaoFactory.class.getClassLoader().getResourceAsStream("/config.properties")) {
            property.load(is);
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
