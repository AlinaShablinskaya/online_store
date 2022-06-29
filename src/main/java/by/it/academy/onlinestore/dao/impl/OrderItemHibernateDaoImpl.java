package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.OrderItemDao;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO class OrderItemHibernateDaoImpl
 */
public class OrderItemHibernateDaoImpl extends AbstractCrudHibernateDaoImpl<OrderItem> implements OrderItemDao {
    /**
     * Saves object in table mapped
     *
     * @param orderItemId id of searching object in database
     * @param cartId id of searching object in database
     */
    @Override
    public void addOrderItemOnCart(Integer orderItemId, Integer cartId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            OrderItem orderItemToAdd = session.get(OrderItem.class, orderItemId);
            Cart cart = session.get(Cart.class, cartId);
            cart.addOrderItem(orderItemToAdd);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeOrderItemFromCart(Integer orderItemId, Integer cartId) {

    }

    /**
     * Returns a list of all values of type described this Dao in mapped table of the database
     *
     * @param cartId id of searching object in database
     * @return a list of all values of type described this Dao in mapped table of the database
     */
    @Override
    public List<OrderItem> findByCartId(Integer cartId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Cart cart = session.get(Cart.class, cartId);
            List<OrderItem> orderItems = new ArrayList<>(cart.getOrderItems());
            session.getTransaction().commit();
            return orderItems;
        }
    }
}
