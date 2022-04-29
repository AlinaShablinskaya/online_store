package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.entities.Cart;

import java.util.Optional;

public interface CartDao extends CrudDao<Cart, Integer> {
    Optional<Cart> findByCustomerId(Integer customerId);
}
