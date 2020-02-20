package dao;

import exception.DBException;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import java.util.List;

public class UserHibernateDAO implements UserDAO {

    private SessionFactory sessionFactory;

    UserHibernateDAO(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public List<User> getAllUser() throws DBException {
        List<User> users;

        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("SELECT user FROM User user");
            users = query.list();
        } catch (HibernateException e) {
          throw new DBException(e);
        }
        return users;
    }

    @Override
    public User getUserById(long id) throws DBException {
        User user;

        try (Session session = sessionFactory.openSession()) {
            user = session.get(User.class, id);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
        return user;
    }

    @Override
    public User getUserWithNameAndPassword (String name, String password) throws DBException {
        User user;
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    "SELECT u FROM User u WHERE u.name =:nameUser AND u.password =:passwordUser ")
                    .setParameter("nameUser", name)
                    .setParameter("passwordUser", password)
                    .setMaxResults(1);
            user = (User) query.uniqueResult();
        } catch (HibernateException e) {
            throw new DBException(e);
        }

        return user;
    }

    @Override
    public void updateUserById(long id, User newParameterUser) throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            user.setName(newParameterUser.getName());
            user.setAge(newParameterUser.getAge());
            user.setPassword(newParameterUser.getPassword());
            user.setCity(newParameterUser.getCity());
            user.setRole(newParameterUser.getRole());
            transaction.commit();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void addUser(User user) throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void deleteUserById(long id) throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id =:param");
            query.setParameter("param", id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

}

