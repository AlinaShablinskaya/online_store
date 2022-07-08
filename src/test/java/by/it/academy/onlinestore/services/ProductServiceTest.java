package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.repositories.CatalogRepository;
import by.it.academy.onlinestore.repositories.ProductRepository;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.ProductServiceImpl;
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
class ProductServiceTest {
   @Mock
   private ProductRepository productRepository;
    @Mock
    private CatalogRepository catalogRepository;
   @InjectMocks
   private ProductServiceImpl productService;

    @Test
    void addProductShouldReturnCorrectResult() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal(20.5));

        when(productRepository.save(product)).thenReturn(product);
        productService.addProduct(product);
        verify(productRepository).save(product);
    }

    @Test
    void addProductShouldReturnExceptionWhenGetIncorrectParameters() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal(20.5));

        when(productRepository.findByProductName(product.getProductName())).thenReturn(Optional.of(product));
        assertThrows(EntityAlreadyExistException.class, () -> productService.addProduct(product));
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void findProductByIdShouldReturnCorrectResult() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        productService.findProductById(product.getId());
        verify(productRepository).findById(product.getId());
    }

    @Test
    void findProductByIdExceptionWhenGetIncorrectParameters() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.findProductById(product.getId()));
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void findAllProductShouldReturnCorrectResult() {
        List<Product> products = new ArrayList<>();

        Product firstProduct = new Product();
        firstProduct.setId(1);
        firstProduct.setProductName("First product name");
        firstProduct.setBrand("Brand");
        firstProduct.setPrice(new BigDecimal("20.5"));

        Product secondProduct = new Product();
        secondProduct.setId(2);
        secondProduct.setProductName("Second product name");
        secondProduct.setBrand("Brand");
        secondProduct.setPrice(new BigDecimal("20.5"));

        products.add(firstProduct);
        products.add(secondProduct);

        when(productRepository.findAll()).thenReturn(products);
        productService.findAllProduct();
        verify(productRepository).findAll();
    }

    @Test
    void updateProductShouldReturnCorrectResult() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        productService.updateProduct(product);
        verify(productRepository).save(product);
    }

    @Test
    void removeProductByIdShouldReturnCorrectResult() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        productService.removeProductById(1);
        verify(productRepository).deleteById(1);
    }

    @Test
    void findAllByCatalogName() {
        List<Product> products = new ArrayList<>();

        Product firstProduct = new Product();
        firstProduct.setId(1);
        firstProduct.setProductName("First product name");
        firstProduct.setBrand("Brand");
        firstProduct.setPrice(new BigDecimal("20.5"));

        Product secondProduct = new Product();
        secondProduct.setId(2);
        secondProduct.setProductName("Second product name");
        secondProduct.setBrand("Brand");
        secondProduct.setPrice(new BigDecimal("20.5"));

        Catalog catalog = new Catalog();
        catalog.setProducts(products);

        when(catalogRepository.findByGroupName(catalog.getGroupName())).thenReturn(Optional.of(catalog));
        assertEquals(products, productService.findAllByCatalogName(catalog.getGroupName()));
        verify(catalogRepository).findByGroupName(catalog.getGroupName());
    }

    @Test
    void addProductToCatalog() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        Integer productId = product.getId();
        Integer catalogId = catalog.getId();

        when(catalogRepository.findById(1)).thenReturn(Optional.of(catalog));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(catalogRepository.save(catalog)).thenReturn(catalog);
        productService.addProductToCatalog(catalogId, productId);
        verify(catalogRepository).save(catalog);
    }
}
