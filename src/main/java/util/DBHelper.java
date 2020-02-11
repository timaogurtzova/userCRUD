package util;

import model.User;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {

    private static DBHelper instance;
    private String jbdcDriverMySQL = "com.mysql.cj.jdbc.Driver";
    private String connectionURL = "jdbc:mysql://localhost:3306/db_user"; //db type, host name, port, db name
    private String userName = "root";
    private String userPassword = "1234";
    private String serverTimeZone = "UTC";
    private String useSSL = "false";

    private DBHelper(){}

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    public Connection getConnectionForJdbcDAO() {
        Properties properties = new Properties();
        properties.setProperty("user", userName);
        properties.setProperty("password", userPassword);
        properties.setProperty("serverTimezone", serverTimeZone);
        properties.setProperty("useSSL", useSSL);
        try {
            Class.forName(jbdcDriverMySQL);
            return  DriverManager.getConnection(connectionURL, properties);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public Configuration getConfigurationForHibernateDAO() {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.setProperty("useSSL", useSSL);
        settings.put(Environment.DRIVER, jbdcDriverMySQL); //"hibernate.connection.driver_class"
        settings.put(Environment.URL, connectionURL); //"hibernate.connection.url"
        settings.put(Environment.USER, userName); //"hibernate.connection.username"
        settings.put(Environment.PASS, userPassword); //"hibernate.connection.password"
        settings.put(Environment.JDBC_TIME_ZONE, serverTimeZone); //"serverTimezone"

        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect"); //"hibernate.dialect"
        settings.put(Environment.SHOW_SQL, "true"); //"hibernate.show_sql"
        settings.put(Environment.HBM2DDL_AUTO, "create"); //"hibernate.hbm2ddl.auto"
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }
}
