package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.repositories.CatalogRepository;
import by.it.academy.onlinestore.repositories.ProductRepository;
import by.it.academy.onlinestore.services.ProductService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_ALREADY_EXISTS = "Specified product already exists.";
    private static final String PRODUCT_IS_NOT_FOUND = "Specified product is not found.";
    private static final String CATALOG_IS_NOT_FOUND = "Specified catalog is not found.";
    private  final ProductRepository productRepository;
    private  final CatalogRepository catalogRepository;

    @Override
    public Product addProduct(Product product) {
        if (productRepository.findByProductName(product.getProductName()).isPresent()) {
            throw new EntityAlreadyExistException(PRODUCT_ALREADY_EXISTS);
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(PRODUCT_IS_NOT_FOUND));
    }

    @Override
    public List<Product> findAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable).toList();
    }

    @Override
    public Product updateProduct(Product product) {
        if (!productRepository.findById(product.getId()).isPresent()) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        product = productRepository.save(product);
        return product;
    }

    @Override
    public void removeProductById(Integer id) {
        if (!productRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAllByCatalogName(String categoryName) {
        Catalog catalog = catalogRepository.findByGroupName(categoryName).orElseThrow(() ->
                new EntityNotFoundException(CATALOG_IS_NOT_FOUND));
        return catalog.getProducts();
    }

    @Override
    public void addProductToCatalog(Integer catalogId, Integer productId) {
        Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(() ->
                new EntityNotFoundException(CATALOG_IS_NOT_FOUND));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException(PRODUCT_IS_NOT_FOUND));
        catalog.addProduct(product);
        catalogRepository.save(catalog);
    }

    @Override
    public void removeProductFromCatalog(Integer catalogId, Integer productId) {
        Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(() ->
                new EntityNotFoundException(CATALOG_IS_NOT_FOUND));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException(PRODUCT_IS_NOT_FOUND));
        catalog.deleteProduct(product);
        catalogRepository.save(catalog);
    }
}
