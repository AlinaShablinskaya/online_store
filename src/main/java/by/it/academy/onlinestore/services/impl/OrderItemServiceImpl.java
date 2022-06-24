package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CartDao;
import by.it.academy.onlinestore.dao.OrderItemDao;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.OrderItemService;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OrderItemServiceImpl implements OrderItemService {
    private static final String ORDER_ITEM_IS_NOT_FOUND = "Specified order item is not found.";
    private static final String CART_IS_NOT_FOUND = "Specified cart is not found.";
    private  final OrderItemDao orderItemDao;
    private final CartDao cartDao;

    public OrderItemServiceImpl(OrderItemDao orderItemDao, CartDao cartDao) {
        this.orderItemDao = orderItemDao;
        this.cartDao = cartDao;
    }

    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        log.info("Adding order item {} started", orderItem);
        return orderItemDao.save(orderItem).orElseThrow(() -> {
            log.error("Order Item {} is not found", orderItem);
            return new EntityNotFoundException(ORDER_ITEM_IS_NOT_FOUND);
        });
    }

    @Override
    public void addOrderItemToCart(Integer orderItemId, Integer cartId) {
        log.info("Adding order item id = {} to cart id = {} started", orderItemId, cartId);
        orderItemDao.addOrderItemOnCart(orderItemId, cartId);
        log.info("Order successfully added to cart");
    }

    @Override
    public void removeOrderItemFromCart(Integer orderItemId, Integer cartId) {
        log.info("Order Item id = {} from cart id = {} removal started", orderItemId, cartId);
        if (!orderItemDao.findById(orderItemId).isPresent()) {
            log.error("Order Item is not found");
            throw new EntityNotFoundException(ORDER_ITEM_IS_NOT_FOUND);
        }
        if (!cartDao.findById(cartId).isPresent()) {
            log.error("Cart is not found");
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        orderItemDao.deleteById(orderItemId);
        log.info("Order successfully remove from cart");
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCartId(Integer cartId) {
        log.info("Find all order Item by cart id = {} started", cartId);
        if (!cartDao.findById(cartId).isPresent()) {
            log.error("Order Item is not found");
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        return orderItemDao.findByCartId(cartId);
    }

    @Override
    public BigDecimal calculatePrice(int amount, Product product) {
        BigDecimal price = product.getPrice();
        if (amount == 0 || price.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Amount can't be 0");
        }
        return  price.multiply(BigDecimal.valueOf(amount));
    }
}
