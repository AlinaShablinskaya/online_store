package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.repositories.CartRepository;
import by.it.academy.onlinestore.services.CartService;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private static final String CART_IS_NOT_FOUND = "Specified cart is not found.";
    private final CartRepository cartRepository;

    @Override
    public Cart addCart(Cart cart) {
        log.info("Adding cart {} started", cart);
        cart.setTotalSum(calculateSum(cart.getOrderItems()));
        return cartRepository.save(cart);
    }

    @Override
    public Cart findCartById(Integer id) {
        log.info("Find cart by id = {} started", id);
        return cartRepository.findById(id).orElseThrow(() -> {
            log.error("Cart id = {} is not found", id);
            return new EntityNotFoundException(CART_IS_NOT_FOUND);
        });
    }

    @Override
    public void deleteCart(Integer id) {
        log.info("Cart delete by id = {} started", id);
        if (!cartRepository.findById(id).isPresent()) {
            log.error("Cart is not found");
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        cartRepository.deleteById(id);
        log.info("Cart successfully deleted by id = {}.", id);
    }

    @Override
    public Cart updateCart(Cart cart) {
        log.info("Updating cart {} started", cart);
        if (!cartRepository.findById(cart.getId()).isPresent()) {
            log.error("Cart is not found");
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        cart.setTotalSum(calculateSum(cart.getOrderItems()));
        cart = cartRepository.save(cart);
        log.info("Cart {} successfully updated.", cart);
        return cart;
    }

    private BigDecimal calculateSum(List<OrderItem> orderItems) {
        return BigDecimal.valueOf(orderItems
                .stream()
                .map(orderItem -> orderItem.getTotalPrice().longValue())
                .reduce(Long::sum)
                .orElse(0L));
    }
}
