package by.it.academy.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
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
public class ProductServiceTest {
    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void addProductShouldReturnCorrectResult() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(100)
                .build();

        doNothing().when(productDao).save(product);
        productService.addProduct(product);
        verify(productDao).save(product);
    }

    @Test
    void addNewProductShouldReturnExceptionWhenGetIncorrectParameters() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(100)
                .build();

        when(productDao.findById(product.getId())).thenReturn(Optional.of(product));
        assertThrows(EntityAlreadyExistException.class, () -> productService.addProduct(product));
        verifyNoMoreInteractions(productDao);
    }

    @Test
    void findProductByIdShouldReturnCorrectResult() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(100)
                .build();

        when(productDao.findById(product.getId())).thenReturn(Optional.of(product));
        productService.findProductById(product.getId());
        verify(productDao).findById(product.getId());
    }

    @Test
    void findProductByIdShouldReturnExceptionWhenGetIncorrectParameters() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(100)
                .build();

        when(productDao.findById(product.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.findProductById(product.getId()));
        verifyNoMoreInteractions(productDao);
    }

    @Test
    void findAllProductsShouldReturnCorrectResult() {
        int page = 1;
        int itemsPerPage = 10;

        List<Product> product = new ArrayList<>();

        product.add(Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(100)
                .build());

        product.add(Product.builder()
                .withId(2)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(100)
                .build());

        when(productDao.findAll(page, itemsPerPage)).thenReturn(product);
        productService.findAllProduct(page, itemsPerPage);
        verify(productDao).findAll(page, itemsPerPage);
    }

    @Test
    void updateProductShouldReturnCorrectResult() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(100)
                .build();

        when(productDao.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(productDao).update(product);
        productService.updateProduct(product);
        verify(productDao).update(product);
    }

    @Test
    void removeProductByIdShouldReturnCorrectResult() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(100)
                .build();

        when(productDao.findById(product.getId())).thenReturn(Optional.of(product));
        productService.removeProductById(product.getId());
        verify(productDao).deleteById(product.getId());
    }
}
