package by.it.academy.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import by.it.academy.onlinestore.dao.CartDao;
import by.it.academy.onlinestore.dao.OrderItemDao;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.OrderItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {
    @Mock
    private OrderItemDao orderItemDao;

    @Mock
    private CartDao cartDao;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    void addOrderItemOnCartShouldReturnCorrectResult() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("Name")
                .withBrand("Description")
                .withPrice(new BigDecimal(20.5))
                .build();

        OrderItem orderItem = OrderItem.builder()
                .withId(1)
                .withProduct(product)
                .withAmount(5)
                .build();

        Cart cart = Cart.builder()
                .withId(1)
                .withOrderItems(new ArrayList<>())
                .build();

        Integer orderItemId = orderItem.getId();
        Integer cartId = cart.getId();

        doNothing().when(orderItemDao).addOrderItemOnCart(orderItemId, cartId);
        orderItemService.addOrderItemToCart(orderItemId, cartId);
        verify(orderItemDao).addOrderItemOnCart(orderItemId, cartId);
    }

    @Test
    void removeOrderItemFromCartShouldReturnCorrectResult() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("Name")
                .withBrand("Description")
                .withPrice(new BigDecimal(20.5))
                .build();

        OrderItem orderItem = OrderItem.builder()
                .withId(1)
                .withProduct(product)
                .withAmount(5)
                .build();

        Cart cart = Cart.builder()
                .withId(1)
                .withOrderItems(new ArrayList<>())
                .build();

        Integer orderItemId = orderItem.getId();
        Integer cartId = cart.getId();

        when(orderItemDao.findById(orderItemId)).thenReturn(Optional.of(orderItem));
        when(cartDao.findById(cartId)).thenReturn(Optional.of(cart));
        doNothing().when(orderItemDao).removeOrderItemFromCart(orderItemId, cartId);

        orderItemService.removeOrderItemFromCart(orderItemId, cartId);

        verify(orderItemDao).findById(orderItemId);
        verify(cartDao).findById(cartId);
        verify(orderItemDao).removeOrderItemFromCart(orderItemId, cartId);
    }
}
