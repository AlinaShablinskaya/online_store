package by.it.academy.onlinestore.dao.impl.hibernate;

import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO class ProductHibernateDaoImpl
 */
public class ProductHibernateDaoImpl extends AbstractCrudHibernateDaoImpl<Product> implements ProductDao {
    /**
     * If a value with param name is present in table mapped by this class type,
     * returns an Optional describing the value, otherwise returns Optional with empty value.
     *
     * @param name name of searching catalog in database
     * @return an Optional describing the value of this Optional,
     * if a value with param name is present in table mapped and the value matches the given predicate,
     * otherwise returns Optional with empty value
     */
    @Override
    public Optional<Product> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Optional<Product> product = session.createQuery
                            ("SELECT p FROM Product p WHERE p.productName = :name", Product.class)
                    .setParameter("name", name).uniqueResultOptional();
            session.getTransaction().commit();
            return product;
        }
    }

    /**
     * Creates relationship between entities and save it to the table mapped
     * @param catalogId id of searching catalog in database
     * @param productId id of searching catalog in database
     */
    @Override
    public void addProductOnCatalog(Integer catalogId, Integer productId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Catalog catalog = session.get(Catalog.class, catalogId);
            Product productToAdd = session.get(Product.class, productId);
            catalog.addProduct(productToAdd);
            session.getTransaction().commit();
        }
    }

    /**
     * Deletes relationship between entities from the table mapped
     *
     * @param catalogId id of searching catalog in database
     * @param productId id of searching catalog in database
     */
    @Override
    public void removeProductFromCatalog(Integer catalogId, Integer productId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Catalog catalog = session.get(Catalog.class, catalogId);
            Product product = session.get(Product.class, productId);
            catalog.deleteProduct(product);
            session.getTransaction().commit();
        }
    }

    /**
     * Returns a list of all products in mapped table of a database
     *
     * @param categoryName of searching all products in database object
     * @return a list of all products in mapped table of the database
     */
    @Override
    public List<Product> findAllProductsByCategoryName(String categoryName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Catalog catalog = session.createQuery
                            ("SELECT c FROM Catalog c WHERE c.groupName = :name", Catalog.class)
                    .setParameter("name", categoryName).getSingleResult();
            List<Product> products = new ArrayList<>(catalog.getProducts());
            session.getTransaction().commit();
            return products;
        }
    }
}
