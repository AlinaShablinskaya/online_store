package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Cart;

import java.util.Optional;

public interface CartService {
    Optional<Cart> addCart(Cart cart);

    Cart findCartById(Integer id);

    void deleteCart(Integer id);
}
