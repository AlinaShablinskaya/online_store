package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.CatalogDao;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Optional;

/**
 * DAO class CatalogHibernateDaoImpl
 */
public class CatalogHibernateDaoImpl extends AbstractCrudHibernateDaoImpl<Catalog> implements CatalogDao {
    /**
     * If a value with param name is present in table mapped returns an Optional describing the value,
     * otherwise returns Optional with empty value.
     *
     * @param name name of searching catalog in database
     * @return an Optional describing the value of this Optional,
     * if a value with param name is present in table mapped and the value matches the given predicate,
     * otherwise returns Optional with empty value
     */
    @Override
    public Optional<Catalog> findByGroupName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Optional<Catalog> catalog = session.createQuery
                            ("SELECT c FROM Catalog c WHERE c.groupName = :name", Catalog.class)
                    .setParameter("name", name).uniqueResultOptional();
            session.getTransaction().commit();
            return catalog;
        }
    }
}
