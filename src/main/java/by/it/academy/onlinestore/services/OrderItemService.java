package by.it.academy.onlinestore.services;

public interface OrderItemService {
    void addOrderItemOnCart(Integer orderItemId, Integer cartId);

    void removeOrderItemFromCart(Integer orderItemId, Integer cartId);
}
