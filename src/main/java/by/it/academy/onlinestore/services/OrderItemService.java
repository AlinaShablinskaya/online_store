package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    OrderItem addOrderItem(OrderItem orderItem);

    void addOrderItemToCart(Integer orderItemId, Integer cartId);

    void removeOrderItem(Integer orderItemId);

    List<OrderItem> findAllOrderItemsByCartId(Integer cartId);
}
