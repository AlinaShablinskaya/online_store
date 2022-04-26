package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.OrderItem;

public interface OrderItemService {
    void createOrderItem(OrderItem orderItem);
    void addOrderItemOnCart(Integer orderItemId, Integer cartId);

    void removeOrderItemFromCart(Integer orderItemId, Integer cartId);
}
