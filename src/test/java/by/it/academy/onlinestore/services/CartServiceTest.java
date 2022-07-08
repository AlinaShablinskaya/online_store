package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.repositories.CartRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void addCartShouldReturnCorrectResult() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setOrderItems(new ArrayList<>());
        cart.setTotalSum(BigDecimal.valueOf(20));

        when(cartRepository.save(cart)).thenReturn(cart);
        cartService.addCart(cart);
        verify(cartRepository).save(cart);
    }

    @Test
    void findCartByIdShouldReturnCorrectResult() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setOrderItems(new ArrayList<>());
        cart.setTotalSum(BigDecimal.valueOf(20));

        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        cartService.findCartById(1);
        verify(cartRepository).findById(1);
    }

    @Test
    void deleteCartShouldReturnCorrectResult() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setOrderItems(new ArrayList<>());
        cart.setTotalSum(BigDecimal.valueOf(20));

        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        cartService.deleteCart(1);
        verify(cartRepository).deleteById(1);
    }

    @Test
    void updateCartShouldReturnCorrectResult() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setOrderItems(new ArrayList<>());
        cart.setTotalSum(BigDecimal.valueOf(20));

        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);
        cartService.updateCart(cart);
        verify(cartRepository).save(cart);
    }
}
