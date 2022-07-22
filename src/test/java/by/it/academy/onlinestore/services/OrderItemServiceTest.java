package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.repositories.CartRepository;
import by.it.academy.onlinestore.repositories.OrderItemRepository;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    void addOrderItemShouldCreateOrderItem() {
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
    void addOrderItemToCartShouldSaveRelationshipBetweenOrderItemAndCart() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setProduct(new Product());
        orderItem.setAmount(10);

        Cart cart = new Cart();
        cart.setId(1);
        cart.setTotalSum(BigDecimal.valueOf(10));

        Integer orderItemId = orderItem.getId();
        Integer cartId = cart.getId();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));
        when(cartRepository.save(cart)).thenReturn(cart);
        orderItemService.addOrderItemToCart(orderItemId, cartId);
        verify(cartRepository).save(cart);
    }

    @Test
    void addOrderItemToCartShouldThrowEntityNotFoundExceptionIfOrderItemIsNotExists() {
        Integer cartId = 1;
        Integer nonExistentId = 1;

        when(orderItemRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> orderItemService.addOrderItemToCart(nonExistentId, cartId));
        String expectedMessage = "Specified order item is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(orderItemRepository).findById(nonExistentId);
        verifyNoMoreInteractions(orderItemRepository);
    }

    @Test
    void addOrderItemToCartShouldThrowEntityNotFoundExceptionIfCartIsNotExists() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setProduct(new Product());
        orderItem.setAmount(10);

        Integer orderItemId = orderItem.getId();
        Integer nonExistentId = 1;

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));
        when(cartRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> orderItemService.addOrderItemToCart(orderItemId, nonExistentId));
        String expectedMessage = "Specified cart is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(cartRepository).findById(nonExistentId);
        verifyNoMoreInteractions(cartRepository);
    }

    @Test
    void removeOrderItemShouldRemoveOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setProduct(new Product());
        orderItem.setAmount(10);

        Integer orderItemId = orderItem.getId();

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));
        doNothing().when(orderItemRepository).deleteById(orderItemId);
        orderItemService.removeOrderItem(orderItemId);
        verify(orderItemRepository).findById(orderItemId);
        verify(orderItemRepository).deleteById(orderItemId);
    }

    @Test
    void removeOrderItemShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Integer nonExistentId = 1;

        when(orderItemRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> orderItemService.removeOrderItem(1));
        String expectedMessage = "Specified order item is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(orderItemRepository).findById(nonExistentId);
        verifyNoMoreInteractions(orderItemRepository);
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

        Integer cartId = cart.getId();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        assertEquals(orderItems, orderItemService.findAllOrderItemsByCartId(cartId));
        verify(cartRepository).findById(cartId);
    }

    @Test
    void findAllOrderItemsByCartIdShouldThrowEntityNotFoundExceptionIfCartIsNotExists() {
        Integer nonExistentId = 1;

        when(cartRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> orderItemService.findAllOrderItemsByCartId(nonExistentId));
        String expectedMessage = "Specified cart is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(cartRepository).findById(nonExistentId);
        verifyNoMoreInteractions(cartRepository);
    }
}
