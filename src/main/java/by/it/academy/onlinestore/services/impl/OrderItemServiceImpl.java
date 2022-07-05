package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.repositories.CartRepository;
import by.it.academy.onlinestore.repositories.OrderItemRepository;
import by.it.academy.onlinestore.services.OrderItemService;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private static final String ORDER_ITEM_IS_NOT_FOUND = "Specified order item is not found.";
    private static final String CART_IS_NOT_FOUND = "Specified cart is not found.";
    private  final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        log.info("Adding order item {} started", orderItem);
        orderItem.setTotalPrice(calculatePrice(orderItem.getAmount(), orderItem.getProduct()));
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void addOrderItemToCart(Integer orderItemId, Integer cartId) {
        log.info("Adding order item id = {} to cart id = {} started", orderItemId, cartId);
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> {
            log.error("Order item id = {} is not found", orderItemId);
            return new EntityNotFoundException(ORDER_ITEM_IS_NOT_FOUND);
        });
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> {
            log.error("Cart id = {} is not found", cartId);
            return new EntityNotFoundException(CART_IS_NOT_FOUND);
        });
        cart.addOrderItem(orderItem);
        cartRepository.save(cart);
        log.info("Order successfully added to cart");
    }

    @Override
    public void removeOrderItem(Integer orderItemId) {
        log.info("Order Item id = {} removal started", orderItemId);
        if (!orderItemRepository.findById(orderItemId).isPresent()) {
            log.error("Order Item is not found");
            throw new EntityNotFoundException(ORDER_ITEM_IS_NOT_FOUND);
        }
        orderItemRepository.deleteById(orderItemId);
        log.info("Order successfully remove from cart");
    }

    @Override
    public List<OrderItem> findAllOrderItemsByCartId(Integer cartId) {
        log.info("Find all order Item by cart id = {} started", cartId);
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> {
            log.error("Cart id = {} is not found", cartId);
            return new EntityNotFoundException(CART_IS_NOT_FOUND);
        });
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
