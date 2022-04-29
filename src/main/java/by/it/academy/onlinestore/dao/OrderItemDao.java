package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.entities.OrderItem;

import java.util.Optional;

public interface OrderItemDao extends CrudDao<OrderItem, Integer> {
    void  addOrderItemOnCart(Integer orderItemId, Integer cartId);

    void removeOrderItemFromCart(Integer orderItemId, Integer cartId);

    Optional<OrderItem> findByProductId(Integer productId);
}
