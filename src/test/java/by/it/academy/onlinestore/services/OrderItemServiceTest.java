package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.repositories.CartRepository;
import by.it.academy.onlinestore.repositories.OrderItemRepository;
import by.it.academy.onlinestore.services.impl.OrderItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    void addOrderItem() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Name");
        product.setBrand("Brand");
        product.setPrice(BigDecimal.valueOf(10));

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setProduct(product);
        orderItem.setAmount(10);

        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);
        orderItemService.addOrderItem(orderItem);
        verify(orderItemRepository).save(orderItem);
    }

    @Test
    void addOrderItemToCart() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setProduct(new Product());
        orderItem.setAmount(10);

        Cart cart = new Cart();
        cart.setId(1);
        cart.setTotalSum(BigDecimal.valueOf(10));

        Integer orderItemId = orderItem.getId();
        Integer cartId = cart.getId();

        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        when(orderItemRepository.findById(1)).thenReturn(Optional.of(orderItem));
        when(cartRepository.save(cart)).thenReturn(cart);
        orderItemService.addOrderItemToCart(orderItemId, cartId);
        verify(cartRepository).save(cart);
    }

    @Test
    void removeOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setProduct(new Product());
        orderItem.setAmount(10);

        when(orderItemRepository.findById(1)).thenReturn(Optional.of(orderItem));
        orderItemService.removeOrderItem(1);
        verify(orderItemRepository).deleteById(1);
    }

    @Test
    void findAllOrderItemsByCartId() {
        List<OrderItem> orderItems = new ArrayList<>();

        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setProduct(new Product());
        orderItem.setAmount(10);

        orderItems.add(orderItem);

        Cart cart = new Cart();
        cart.setId(1);
        cart.setTotalSum(BigDecimal.valueOf(10));
        cart.setOrderItems(orderItems);

        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        assertEquals(orderItems, orderItemService.findAllOrderItemsByCartId(1));
        verify(cartRepository).findById(1);
    }
}
