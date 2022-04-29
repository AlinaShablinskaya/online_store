package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Cart;

public interface CartService {
    void addCart(Cart cart);

    Cart findCartById(Integer id);

    void deleteCart(Integer id);

    Cart findCartByCustomerId(Integer customerId);
}
