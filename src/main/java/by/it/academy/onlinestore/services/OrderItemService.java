package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    Optional<OrderItem> addOrderItem(OrderItem orderItem);

    void addOrderItemToCart(Integer orderItemId, Integer cartId);

    void removeOrderItemFromCart(Integer orderItemId, Integer cartId);

    List<OrderItem> findAllOrderItemsByCartId(Integer cartId);
}
