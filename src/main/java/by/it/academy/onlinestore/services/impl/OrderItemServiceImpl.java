package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.repositories.CartRepository;
import by.it.academy.onlinestore.repositories.OrderItemRepository;
import by.it.academy.onlinestore.services.OrderItemService;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service class for managing order item.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private static final String ORDER_ITEM_IS_NOT_FOUND_EXCEPTION = "Specified order item is not found.";
    private static final String CART_IS_NOT_FOUND_EXCEPTION = "Specified cart is not found.";
    private  final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    /**
     * Returns created order item in table mapped by calling OrderItemRepository.save(orderItem)
     * @param orderItem the entity to save
     * @return the non-null order item with defined id
     */
    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        orderItem.setTotalPrice(calculatePrice(orderItem.getAmount(), orderItem.getProduct()));
        return orderItemRepository.save(orderItem);
    }

    /**
     * Saves relationship between order item and cart in table mapped if specified cart and order item exists,
     * otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if cart or order item with specified id is not present in database
     * @param cartId the id of the cart to save
     * @param orderItemId the id of the order item to save
     */
    @Override
    public void addOrderItemToCart(Integer orderItemId, Integer cartId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() ->
                new EntityNotFoundException(ORDER_ITEM_IS_NOT_FOUND_EXCEPTION));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new EntityNotFoundException(CART_IS_NOT_FOUND_EXCEPTION));
        cart.addOrderItem(orderItem);
        cartRepository.save(cart);
    }

    /**
     * Deletes order item with param id from the table mapped by calling OrderItemRepository.deleteById(orderItemId),
     * if specified order item exists, otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if order item with specified id is not present in database
     * @param orderItemId the id of the order item to delete
     */
    @Override
    public void removeOrderItem(Integer orderItemId) {
        if (!orderItemRepository.findById(orderItemId).isPresent()) {
            throw new EntityNotFoundException(ORDER_ITEM_IS_NOT_FOUND_EXCEPTION);
        }
        orderItemRepository.deleteById(orderItemId);
    }

    /**
     * Returns the list of all order items by cartId if specified cart and order item exists,
     * otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if cart with specified id is not present in database
     * @param cartId the id of the cart to retrieve list of all order items
     * @return the list of all order items
     */
    @Override
    public List<OrderItem> findAllOrderItemsByCartId(Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new EntityNotFoundException(CART_IS_NOT_FOUND_EXCEPTION));
        return cart.getOrderItems();
    }

    private BigDecimal calculatePrice(int amount, Product product) {
        BigDecimal price = product.getPrice();
        if (amount == 0 || price.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Amount can't be 0");
        }
        return  price.multiply(BigDecimal.valueOf(amount));
    }
}
