package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CartDao;
import by.it.academy.onlinestore.dao.OrderItemDao;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.services.OrderItemService;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void createOrderItem(OrderItem orderItem) {
        orderItemDao.save(orderItem);

        lOGGER.info("Order item successfully creates.");
    }

    @Override
    public void addOrderItemOnCart(Integer orderItemId, Integer cartId) {
        if (!orderItemDao.findById(orderItemId).isPresent()) {
            throw new EntityNotFoundException(ORDER_ITEM_IS_NOT_FOUND);
        }

        if (!cartDao.findById(cartId).isPresent()) {
            throw new EntityNotFoundException(CART_IS_NOT_FOUND);
        }

        orderItemDao.addOrderItemOnCart(orderItemId, cartId);

        lOGGER.info("Successfully added order to cart");
    }

    @Override
    public void removeOrderItemFromCart(Integer orderItemId, Integer cartId) {
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
    public OrderItem findOrderItemByProductId(Integer productId) {
        return orderItemDao.findByProductId(productId).orElseThrow(() -> {
            return new EntityNotFoundException(ORDER_ITEM_IS_NOT_FOUND);
        });
    }
}
