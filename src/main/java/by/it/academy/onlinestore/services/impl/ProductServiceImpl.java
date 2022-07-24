package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.repositories.CatalogRepository;
import by.it.academy.onlinestore.repositories.ProductRepository;
import by.it.academy.onlinestore.services.ProductService;

import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityExistsException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing product.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_ALREADY_EXISTS_EXCEPTION = "Specified product already exists.";
    private static final String PRODUCT_IS_NOT_FOUND_EXCEPTION = "Specified product is not found.";
    private static final String CATALOG_IS_NOT_FOUND_EXCEPTION = "Specified catalog is not found.";
    private  final ProductRepository productRepository;
    private  final CatalogRepository catalogRepository;

    /**
     * Returns created product in table mapped by calling ProductRepository.save(product)
     * @param product the entity to save
     * @throws EntityExistsException if product with specified name already exists in database
     * @return non-null value with defined id
     */
    @Override
    public Product addProduct(Product product) {
        if (productRepository.findByProductName(product.getProductName()).isPresent()) {
            throw new EntityExistsException(PRODUCT_ALREADY_EXISTS_EXCEPTION);
        }
        return productRepository.save(product);
    }

    /**
     * Returns the product by the defined id by calling ProductRepository.findById(id) if specified product exists,
     * otherwise throws EntityNotFoundException
     * @param id id the id of the product to retrieve
     * @throws EntityNotFoundException if product with specified id is not present in database
     * @return the non-null product with defined id
     */
    @Override
    public Product findProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(PRODUCT_IS_NOT_FOUND_EXCEPTION));
    }

    /**
     * Returns the list of all product by calling ProductRepository.findAll()
     * @param pageable display only a chunk of data based on the page-number and page-size parameters specified
     * @return the list of all products
     */
    @Override
    public List<Product> findAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable).toList();
    }

    /**
     * Updates product in table mapped by calling ProductRepository.save(product) if specified product exists,
     * otherwise throws EntityNotFoundException
     * @param product value to update product in database
     * @throws EntityNotFoundException if product with id defined in param product is not present in database
     * @return updates product
     */
    @Override
    public Product updateProduct(Product product) {
        if (!productRepository.findById(product.getId()).isPresent()) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND_EXCEPTION);
        }
        product = productRepository.save(product);
        return product;
    }

    /**
     * Deletes product with param id from the table mapped by calling ProductRepository.deleteById(id),
     * if specified product exists, otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if product with specified id is not present in database
     * @param id the id of the product to delete
     */
    @Override
    public void removeProductById(Integer id) {
        if (!productRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND_EXCEPTION);
        }
        productRepository.deleteById(id);
    }

    /**
     * Returns the list of all products by catalog name if specified catalog exists,
     * otherwise throws EntityNotFoundException
     * @param categoryName the catalog name to retrieve list of all products
     * @throws EntityNotFoundException if catalog with specified catalog name is not present in database
     * @return the list of all product
     */
    @Override
    public List<Product> findAllByCatalogName(String categoryName) {
        Catalog catalog = catalogRepository.findByGroupName(categoryName).orElseThrow(() ->
                new EntityNotFoundException(CATALOG_IS_NOT_FOUND_EXCEPTION));
        return catalog.getProducts();
    }

    /**
     * Creates relationship between catalog and product and save it to the table mapped
     * if specified catalog and product exists, otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if product or catalog with specified id is not present in database
     * @param catalogId the id of the catalog to save
     * @param productId the id of the product to save
     */
    @Override
    public void addProductToCatalog(Integer catalogId, Integer productId) {
        Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(() ->
                new EntityNotFoundException(CATALOG_IS_NOT_FOUND_EXCEPTION));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException(PRODUCT_IS_NOT_FOUND_EXCEPTION));
        catalog.addProduct(product);
        catalogRepository.save(catalog);
    }

    /**
     * Deletes relationship between catalog and product from the table mapped
     * if specified catalog and product exists, otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if product or catalog with specified id is not present in database
     * @param catalogId the id of the catalog to delete
     * @param productId the id of the product to delete
     */
    @Override
    public void removeProductFromCatalog(Integer catalogId, Integer productId) {
        Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(() ->
                new EntityNotFoundException(CATALOG_IS_NOT_FOUND_EXCEPTION));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException(PRODUCT_IS_NOT_FOUND_EXCEPTION));
        catalog.deleteProduct(product);
        catalogRepository.save(catalog);
    }
}
