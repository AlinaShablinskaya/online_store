package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CartDao;
import by.it.academy.onlinestore.dao.OrderItemDao;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.OrderItemService;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderItemServiceImpl implements OrderItemService {
    private static final Logger lOGGER = LoggerFactory.getLogger(OrderItemServiceImpl.class);
    private static final String ORDER_ITEM_IS_NOT_FOUND = "Specified order item is not found.";
    private static final String CART_IS_NOT_FOUND = "Specified cart is not found.";
    private  final OrderItemDao orderItemDao;
    private final CartDao cartDao;

    public OrderItemServiceImpl(OrderItemDao orderItemDao, CartDao cartDao) {
        this.orderItemDao = orderItemDao;
        this.cartDao = cartDao;
    }

    @Override
    public Optional<OrderItem> addOrderItem(OrderItem orderItem) {
        lOGGER.info("Adding order item started");
        return orderItemDao.save(orderItem);
    }

    @Override
    public void addOrderItemToCart(Integer orderItemId, Integer cartId) {
        lOGGER.info("Adding order item to cart started");
        orderItemDao.addOrderItemOnCart(orderItemId, cartId);
        lOGGER.info("Successfully added order to cart");
    }

    @Override
    public void removeOrderItemFromCart(Integer orderItemId, Integer cartId) {
        lOGGER.info("Order Item from cart removal started");
        if (!orderItemDao.findById(orderItemId).isPresent()) {
            throw new EntityNotFoundException(ORDER_ITEM_IS_NOT_FOUND);
        }
        if (!cartDao.findById(cartId).isPresent()) {
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }
        orderItemDao.removeOrderItemFromCart(orderItemId, cartId);
        lOGGER.info("Successfully remove order from cart");
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCartId(Integer cartId) {
        lOGGER.info("Find all order Item by cart id started");
        if (!cartDao.findById(cartId).isPresent()) {
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
