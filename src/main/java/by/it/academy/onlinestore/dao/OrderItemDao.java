package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.entities.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemDao extends CrudDao<OrderItem, Integer> {
    void  addOrderItemOnCart(Integer orderItemId, Integer cartId);

    void removeOrderItemFromCart(Integer orderItemId, Integer cartId);

    List<OrderItem> findByCartId(Integer cartId);
}
