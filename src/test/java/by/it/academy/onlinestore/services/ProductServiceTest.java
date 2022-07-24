package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.repositories.CatalogRepository;
import by.it.academy.onlinestore.repositories.ProductRepository;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    @DisplayName("add product should create new product")
    void addProductShouldCreateNewProduct() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        when(productRepository.save(product)).thenReturn(product);
        productService.addProduct(product);
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("add product throws entity already exist exception if entity already exists")
    void addProductThrowEntityAlreadyExistExceptionIfEntityAlreadyExists() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        String productName = product.getProductName();

        when(productRepository.findByProductName(productName)).thenReturn(Optional.of(product));
        Exception exception = assertThrows(EntityExistsException.class,
                () -> productService.addProduct(product));
        String expectedMessage = "Specified product already exists.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(productRepository).findByProductName(productName);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("find product by id should return product with specified id")
    void findProductByIdShouldReturnProductWithSpecifiedId() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        Integer productId = product.getId();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        productService.findProductById(productId);
        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("find product by id should throw entity not found exception if no such entity exists")
    void findProductByIdShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Integer nonExistentId = 1;

        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> productService.findProductById(nonExistentId));
        String expectedMessage = "Specified product is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(productRepository).findById(nonExistentId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("find all product should show all products")
    void findAllProductShouldShowAllProducts() {
        List<Product> products = new ArrayList<>();
        Pageable paging = PageRequest.of(0, 2);

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

        Page<Product> page = new PageImpl<>(products);

        when(productRepository.findAll(paging)).thenReturn(page);
        productService.findAllProduct(paging);
        verify(productRepository).findAll(paging);
    }

    @Test
    @DisplayName("update product should update product")
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
    @DisplayName("update product throw entity not found exception if no such entity exists")
    void updateProductThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        Integer nonExistentId = 1;

        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> productService.updateProduct(product));
        String expectedMessage = "Specified product is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(productRepository).findById(nonExistentId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("remove product by id should remove product")
    void removeProductByIdShouldRemoveProduct() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        Integer productId = product.getId();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(productId);
        productService.removeProductById(productId);
        verify(productRepository).findById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    @DisplayName("remove product by id should throw entity not found exception if no such entity exists")
    void removeProductByIdShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Integer nonExistentId = 1;

        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> productService.removeProductById(nonExistentId));
        String expectedMessage = "Specified product is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(productRepository).findById(nonExistentId);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("find all by catalog name should show all products by catalog name")
    void findAllByCatalogNameShouldShowAllProductsByCatalogName() {
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
    @DisplayName("find all by catalog name should throw entity not found exception if no such entity exists")
    void findAllByCatalogNameShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        when(catalogRepository.findByGroupName("catalog")).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> productService.findAllByCatalogName("catalog"));
        String expectedMessage = "Specified catalog is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(catalogRepository).findByGroupName("catalog");
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    @DisplayName("add product to catalog should save relationship between product and catalog")
    void addProductToCatalogShouldSaveRelationshipBetweenProductAndCatalog() {
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

        when(catalogRepository.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(catalogRepository.save(catalog)).thenReturn(catalog);
        productService.addProductToCatalog(catalogId, productId);
        verify(catalogRepository).save(catalog);
    }

    @Test
    @DisplayName("add product to catalog should throw entity not found exception if catalog is not exists")
    void addProductToCatalogShouldThrowEntityNotFoundExceptionIfCatalogIsNotExists() {
        Product product = new Product();
        product.setId(1);
        product.setProductName("Product name");
        product.setBrand("Brand");
        product.setPrice(new BigDecimal("20.5"));

        Integer productId = product.getId();
        Integer nonExistentId = 1;

        when(catalogRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> productService.addProductToCatalog(nonExistentId, productId));
        String expectedMessage = "Specified catalog is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(catalogRepository).findById(nonExistentId);
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    @DisplayName("add product to catalog should throw entity not found exception if product is not exists")
    void addProductToCatalogShouldThrowEntityNotFoundExceptionIfProductIsNotExists() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        Integer catalogId = catalog.getId();
        Integer nonExistentId = 1;

        when(catalogRepository.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> productService.addProductToCatalog(catalogId, nonExistentId));
        String expectedMessage = "Specified product is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(productRepository).findById(nonExistentId);
        verifyNoMoreInteractions(productRepository);
    }
}
