package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.UserDao;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Optional;

/**
 * DAO class UserHibernateDaoImpl
 */
public class UserHibernateDaoImpl extends AbstractCrudHibernateDaoImpl<User> implements UserDao {
    /**
     * If a value with param name is present in table mapped by this class type,
     * returns an Optional describing the value, otherwise returns Optional with empty value.
     *
     * @param email of searching catalog in database
     * @return an Optional describing the value of this Optional,
     * if a value with param name is present in table mapped and the value matches the given predicate,
     * otherwise returns Optional with empty value
     */
    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Optional<User> user = session.createQuery
                            ("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email).uniqueResultOptional();
            session.getTransaction().commit();
            return user;
        }
    }
}
