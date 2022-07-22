package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.repositories.CartRepository;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void addCartShouldCreateNewCart() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setOrderItems(new ArrayList<>());
        cart.setTotalSum(BigDecimal.valueOf(20));

        when(cartRepository.save(cart)).thenReturn(cart);
        cartService.addCart(cart);
        verify(cartRepository).save(cart);
    }

    @Test
    void findCartByIdShouldReturnCartWithSpecifiedId() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setOrderItems(new ArrayList<>());
        cart.setTotalSum(BigDecimal.valueOf(20));

        Integer cartId = cart.getId();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        cartService.findCartById(cartId);
        verify(cartRepository).findById(cartId);
    }

    @Test
    void findCartByIdShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Integer nonExistentId = 1;

        when(cartRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> cartService.findCartById(nonExistentId));
        String expectedMessage = "Specified cart is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(cartRepository).findById(nonExistentId);
        verifyNoMoreInteractions(cartRepository);
    }

    @Test
    void deleteCartShouldDeleteCart() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setOrderItems(new ArrayList<>());
        cart.setTotalSum(BigDecimal.valueOf(20));

        Integer cartId = cart.getId();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        doNothing().when(cartRepository).deleteById(cartId);
        cartService.deleteCart(cartId);
        verify(cartRepository).findById(cartId);
        verify(cartRepository).deleteById(cartId);
    }

    @Test
    void deleteCartShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Integer nonExistentId = 1;

        when(cartRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> cartService.deleteCart(nonExistentId));
        String expectedMessage = "Specified cart is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(cartRepository).findById(nonExistentId);
        verifyNoMoreInteractions(cartRepository);
    }

    @Test
    void updateCartShouldUpdateCart() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setOrderItems(new ArrayList<>());
        cart.setTotalSum(BigDecimal.valueOf(20));

        Integer cartId = cart.getId();

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        cartService.updateCart(cart);
        verify(cartRepository).save(cart);
    }

    @Test
    void updateCartShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setOrderItems(new ArrayList<>());
        cart.setTotalSum(BigDecimal.valueOf(20));

        Integer nonExistentId = 1;

        when(cartRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> cartService.updateCart(cart));
        String expectedMessage = "Specified cart is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(cartRepository).findById(nonExistentId);
        verifyNoMoreInteractions(cartRepository);
    }
}
