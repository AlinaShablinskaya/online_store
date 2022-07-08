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
        cart.setTotalSum(calculateSum(cart.getOrderItems()));
        return cartRepository.save(cart);
    }

    @Override
    public Cart findCartById(Integer id) {
        return cartRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException(CART_IS_NOT_FOUND);
        });
    }

    @Override
    public void deleteCart(Integer id) {
        if (!cartRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        cartRepository.deleteById(id);
    }

    @Override
    public Cart updateCart(Cart cart) {
        if (!cartRepository.findById(cart.getId()).isPresent()) {
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        cart.setTotalSum(calculateSum(cart.getOrderItems()));
        cart = cartRepository.save(cart);
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
