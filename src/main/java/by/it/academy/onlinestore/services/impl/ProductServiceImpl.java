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
        log.info("Adding product {} started", product);
        if (productRepository.findByProductName(product.getProductName()).isPresent()) {
            log.error("Product {} already exists", product);
            throw new EntityAlreadyExistException(PRODUCT_ALREADY_EXISTS);
        }
        log.info("Product {} successfully added.", product);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Integer id) {
        log.info("Find product by id = {} started", id);
        return productRepository.findById(id).orElseThrow(() -> {
            log.error("Product id = {} is not found", id);
            return new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        });
    }

    @Override
    public List<Product> findAllProduct(int page, int itemsPerPage) {
        log.info("Find all product on the page = {} started", page);
        Pageable paging = PageRequest.of(page, itemsPerPage);
        Page<Product> allProduct = productRepository.findAll(paging);
        return allProduct.toList();
    }

    @Override
    public List<Product> findAllProduct() {
        log.info("Find all product started");
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product product) {
        log.info("Updating product {} started", product);
        if (!productRepository.findById(product.getId()).isPresent()) {
            log.error("Product {} is not found", product);
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        product = productRepository.save(product);
        log.info("Product {} successfully updated.", product);
        return product;
    }

    @Override
    public void removeProductById(Integer id) {
        log.info("Product delete by id = {} started", id);
        if (!productRepository.findById(id).isPresent()) {
            log.error("Product is not found");
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        productRepository.deleteById(id);
        log.info("Product {} successfully deleted.", id);
    }

    @Override
    public List<Product> findAllByCatalogName(String categoryName) {
        log.info("Find all product by category name {} started", categoryName);
        Catalog catalog = catalogRepository.findByGroupName(categoryName).orElseThrow(() -> {
            log.error("Catalog name {} is not found", categoryName);
            return new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        });
        return catalog.getProducts();
    }

    @Override
    public void addProductToCatalog(Integer catalogId, Integer productId) {
        log.info("Adding product id = {} to catalog id = {} started", productId, catalogId);
        Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(() -> {
            log.error("Catalog is not found");
            return new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        });
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            log.error("Product is not found");
            return new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        });
        catalog.addProduct(product);
        catalogRepository.save(catalog);
        log.info("Product successfully added to catalog");
    }

    @Override
    public void removeProductFromCatalog(Integer catalogId, Integer productId) {
        log.info("Product id = {} from catalog id = {} removal started", productId, catalogId);
        Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(() -> {
            log.error("Catalog is not found");
            return new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        });
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            log.error("Product is not found");
            return new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        });
        catalog.deleteProduct(product);
        catalogRepository.save(catalog);
        log.info("Product successfully remove from catalog");
    }
}
