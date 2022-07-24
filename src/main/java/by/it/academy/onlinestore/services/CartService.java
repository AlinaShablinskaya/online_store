package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartService {
    Cart addCart(Cart cart);

    Cart findCartById(Integer id);

    void deleteCart(Integer id);

    Cart updateCart(Cart cart);
}
