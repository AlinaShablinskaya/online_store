package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CartDao;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.services.CartService;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static final Logger lOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    private static final String CART_ALREADY_EXISTS = "Specified cart already exists.";
    private static final String CART_IS_NOT_FOUND = "Specified cart is not found.";
    private final CartDao cartDao;

    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public Optional<Cart> addCart(Cart cart) {
        lOGGER.info("Adding cart started");
        return cartDao.save(cart);
    }

    @Override
    public Cart findCartById(Integer id) {
        lOGGER.info("Find cart by id started");
        return cartDao.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException(CART_IS_NOT_FOUND);
        });
    }

    @Override
    public void deleteCart(Integer id) {
        lOGGER.info("Cart delete started");
        if (!cartDao.findById(id).isPresent()) {
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        cartDao.deleteById(id);
        lOGGER.info("Cart successfully deleted.");
    }
}
