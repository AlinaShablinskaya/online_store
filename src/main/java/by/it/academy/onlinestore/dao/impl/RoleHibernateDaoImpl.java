package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.RoleDao;
import by.it.academy.onlinestore.entities.Role;
import by.it.academy.onlinestore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Optional;

/**
 * DAO class RoleHibernateDaoImpl
 */
public class RoleHibernateDaoImpl extends AbstractCrudHibernateDaoImpl<Role> implements RoleDao {

    /**
     * If a value with param name is present in table mapped by this class type,
     * returns an Optional describing the value, otherwise returns Optional with empty value.
     *
     * @param name name of searching catalog in database
     * @return an Optional describing the value of this Optional,
     * if a value with param name is present in table mapped and the value matches the given predicate,
     * otherwise returns Optional with empty value
     */
    public Optional<Role> findByRoleName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Optional<Role> role = session.createQuery
                            ("SELECT r FROM Role r WHERE r.roleName = :name", Role.class)
                    .setParameter("name", name).uniqueResultOptional();
            session.getTransaction().commit();
            return role;
        }
    }
}
