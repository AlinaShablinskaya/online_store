package by.it.academy.onlinestore.dao.impl;

import java.lang.reflect.ParameterizedType;
import by.it.academy.onlinestore.dao.CrudDao;
import by.it.academy.onlinestore.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Optional;

/**
 * An abstract base class for Hibernate DAO classes.
 *
 * @param <E> the type of by.it.academy.onlinestore.entities package classes
 */
public abstract class AbstractCrudHibernateDaoImpl<E> implements CrudDao<E, Integer> {
    /**
     * GenericType for Object casting
     */
    private final Class<E> clazz;

     @SuppressWarnings("unchecked")
    protected AbstractCrudHibernateDaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.clazz = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
    }

    /**
     * Returns created object in table mapped by value class type.
     *
     * @param entity entity to save
     * @return an Optional describing the value of this Optional,
     * if an entity is present in table mapped by this class type, and the entity
     * matches the given predicate, otherwise returns Optional with empty value
     */
    @Override
    public Optional<E> save(E entity) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return Optional.ofNullable(entity);
        }
    }

    /**
     * Saves all given entities in mapped table of a database.
     *
     * @param entities to save
     */
    @Override
    public void saveAll(List<E> entities) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            for (E entity : entities) {
                session.save(entity);
            }
            session.getTransaction().commit();
        }
    }

    /**
     * If a value with param id is present in table mapped by this class type,
     * returns an Optional describing the value, otherwise returns Optional with empty value.
     *
     * @param id id of searching in database object
     * @return an Optional describing the value of this Optional,
     * if a value with param id is present in table mapped by this class type,
     * and the value matches the given predicate, otherwise returns Optional with empty value
     */
    @Override
    public Optional<E> findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Optional<E> entity = Optional.ofNullable(session.get(clazz, id));
            session.getTransaction().commit();
            return entity;
        }
    }

    /**
     * Returns a list of all values of type described this Dao in mapped table of a database
     *
     * @param page the current page index to display for this pagination control
     * @param itemsPerPage the maximum number of page indicators to use for this pagination control
     * @return a list of all values of type described this Dao in mapped table of a database
     */
    @Override
    public List<E> findAll(int page, int itemsPerPage) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<E> entities = session.createQuery("From " + clazz.getSimpleName(), clazz)
                    .setFirstResult((page - 1) * itemsPerPage)
                    .setMaxResults(itemsPerPage)
                    .getResultList();
            session.getTransaction().commit();
            return entities;
        }
    }

    /**
     * Returns a list of all values of type described this Dao in mapped table of a database
     *
     * @return a list of all values of type described this Dao in mapped table of a database
     */
    @Override
    public List<E> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<E> entities = session.createQuery("From " + clazz.getSimpleName(), clazz).getResultList();
            session.getTransaction().commit();
            return entities;
        }
    }

    /**
     * Updates object in table mapped by class type if object is present,
     * otherwise throws OptimisticLockException.
     *
     * @param entity entity to update object in database
     * @throws OptimisticLockException if object is not present in database
     */
    @Override
    public void update(E entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    /**
     * Deletes object with param id from the table mapped by the class described in this Dao,
     * if object with such param id is present in database, otherwise throws IllegalArgumentException.
     *
     * @param id id of deleting from database object
     * @throws IllegalArgumentException if object with such param id is not present in database
     */
    @Override
    public void deleteById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            E entity = session.get(clazz, id);
            session.delete(entity);
            session.getTransaction().commit();
        }
    }
}
