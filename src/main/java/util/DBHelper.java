package util;

import model.User;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {

    private static DBHelper instance;

    private Properties properties = new Properties();
    private String jbdcDriverMySQL;
    private String connectionURL;
    private String userName;
    private String userPassword;
    private String serverTimeZone;
    private String useSSL;

    private DBHelper() {
    }

    private void init() {
        try (InputStream is = DBHelper.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(is);
            jbdcDriverMySQL = properties.getProperty("jbdcDriverMySQL");
            connectionURL = properties.getProperty("connectionURL");
            userName = properties.getProperty("userName");
            userPassword = properties.getProperty("userPassword");
            serverTimeZone = properties.getProperty("serverTimeZone");
            useSSL = properties.getProperty("useSSL");
        } catch (IOException e) {
            System.out.println("Не удалось обнаружить файл");
        }
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
            instance.init();
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
            return DriverManager.getConnection(connectionURL, properties);
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

        settings.put(Environment.DIALECT, properties.getProperty("DIALECT")); //"hibernate.dialect"
        settings.put(Environment.SHOW_SQL, properties.getProperty("SHOW_SQL")); //"hibernate.show_sql"
        settings.put(Environment.HBM2DDL_AUTO, properties.getProperty("HBM2DDL_AUTO")); //"hibernate.hbm2ddl.auto"
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }
}