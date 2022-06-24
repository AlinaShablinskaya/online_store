package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CartDao;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.services.CartService;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class CartServiceImpl implements CartService {
    private static final String CART_ALREADY_EXISTS = "Specified cart already exists.";
    private static final String CART_IS_NOT_FOUND = "Specified cart is not found.";
    private final CartDao cartDao;

    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public Cart addCart(Cart cart) {
        log.info("Adding cart {} started", cart);
        return cartDao.save(cart).orElseThrow(() -> {
            log.error("Cart {} is not found", cart);
            return new EntityNotFoundException(CART_IS_NOT_FOUND);
        });
    }

    @Override
    public Cart findCartById(Integer id) {
        log.info("Find cart by id = {} started", id);
        return cartDao.findById(id).orElseThrow(() -> {
            log.error("Cart is not found");
            return new EntityNotFoundException(CART_IS_NOT_FOUND);
        });
    }

    @Override
    public void deleteCart(Integer id) {
        log.info("Cart delete by id = {} started", id);
        if (!cartDao.findById(id).isPresent()) {
            log.error("Cart is not found");
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        cartDao.deleteById(id);
        log.info("Cart successfully deleted by id = {}.", id);
    }

    @Override
    public void updateCart(Cart cart) {
        log.info("Updating cart {} started", cart);
        if (!cartDao.findById(cart.getId()).isPresent()) {
            log.error("Cart is not found");
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        cartDao.update(cart);
        log.info("Cart {} successfully updated.", cart);
    }

    @Override
    public BigDecimal calculateSum(List<OrderItem> orderItems) {
        return BigDecimal.valueOf(orderItems
                .stream()
                .map(orderItem -> orderItem.getTotalPrice().longValue())
                .reduce(Long::sum)
                .orElse(0L));
    }
}
