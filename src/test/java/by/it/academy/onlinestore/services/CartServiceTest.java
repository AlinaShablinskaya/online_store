package by.it.academy.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import by.it.academy.onlinestore.dao.CartDao;
import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.CartServiceImpl;
import by.it.academy.onlinestore.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private CartDao cartDao;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void addCartShouldReturnCorrectResult() {
        User user = User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withLogin("login")
                .withPassword("1111")
                .build();

        Cart cart = Cart.builder()
                .withId(1)
                .withUser(user)
                .build();

        doNothing().when(cartDao).save(cart);
        cartService.addCart(cart);
        verify(cartDao).save(cart);
    }

    @Test
    void addNewCartShouldReturnExceptionWhenGetIncorrectParameters() {
        User user = User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withLogin("login")
                .withPassword("1111")
                .build();

        Cart cart = Cart.builder()
                .withId(1)
                .withUser(user)
                .build();

        when(cartDao.findById(cart.getId())).thenReturn(Optional.of(cart));
        assertThrows(EntityAlreadyExistException.class, () -> cartService.addCart(cart));
        verifyNoMoreInteractions(cartDao);
    }

    @Test
    void findCartByIdShouldReturnCorrectResult() {
        User user = User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withLogin("login")
                .withPassword("1111")
                .build();

        Cart cart = Cart.builder()
                .withId(1)
                .withUser(user)
                .build();

        when(cartDao.findById(cart.getId())).thenReturn(Optional.of(cart));
        cartService.findCartById(cart.getId());
        verify(cartDao).findById(cart.getId());
    }

    @Test
    void findCartByIdShouldReturnExceptionWhenGetIncorrectParameters() {
        User user = User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withLogin("login")
                .withPassword("1111")
                .build();

        Cart cart = Cart.builder()
                .withId(1)
                .withUser(user)
                .build();

        when(cartDao.findById(cart.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> cartService.findCartById(cart.getId()));
        verifyNoMoreInteractions(cartDao);
    }

    @Test
    void removeCartByIdShouldReturnCorrectResult() {
        User user = User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withLogin("login")
                .withPassword("1111")
                .build();

        Cart cart = Cart.builder()
                .withId(1)
                .withUser(user)
                .build();

        when(cartDao.findById(cart.getId())).thenReturn(Optional.of(cart));
        cartService.deleteCart(cart.getId());
        verify(cartDao).deleteById(cart.getId());
    }
}
