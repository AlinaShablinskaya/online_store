package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.repositories.CartRepository;
import by.it.academy.onlinestore.services.CartService;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service class for managing cart.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private static final String CART_IS_NOT_FOUND_EXCEPTION = "Specified cart is not found.";
    private final CartRepository cartRepository;

    /**
     * Returns created customer address in table mapped by calling CartRepository.save(cart)
     * @param cart the entity to save
     * @return non-null value with defined id
     */
    @Override
    public Cart addCart(Cart cart) {
        cart.setTotalSum(calculateSum(cart.getOrderItems()));
        return cartRepository.save(cart);
    }

    /**
     * Returns a cart by the defined id by calling CartRepository.findById(id) if specified cart exists
     * @param id the id of the cart to retrieve
     * @throws EntityNotFoundException if cart with specified id is not present in database
     * @return the non-null cart with defined id
     */
    @Override
    public Cart findCartById(Integer id) {
        return cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CART_IS_NOT_FOUND_EXCEPTION));
    }

    /**
     * Deletes cart with param id from the table mapped by calling CartRepository.deleteById(id)
     * if specified cart exists, otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if cart with specified id is not present in database
     * @param id the id of the cart to delete
     */
    @Override
    public void deleteCart(Integer id) {
        if (!cartRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(CART_IS_NOT_FOUND_EXCEPTION);
        }
        cartRepository.deleteById(id);
    }

    /**
     * Updates cart in table mapped by calling CartRepository.save(cart) if specified address exists,
     * otherwise throws EntityNotFoundException
     * @param cart value to update cart in database
     * @throws EntityNotFoundException if cart with id defined in param cart is not present in database
     * @return updates cart
     */
    @Override
    public Cart updateCart(Cart cart) {
        if (!cartRepository.findById(cart.getId()).isPresent()) {
            throw new EntityNotFoundException(CART_IS_NOT_FOUND_EXCEPTION);
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
