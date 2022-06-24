package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CatalogDao;
import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.ProductService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;

import by.it.academy.onlinestore.services.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_ALREADY_EXISTS = "Specified product already exists.";
    private static final String PRODUCT_IS_NOT_FOUND = "Specified product is not found.";
    private static final String CATALOG_IS_NOT_FOUND = "Specified catalog is not found.";
    private  final ProductDao productDao;
    private  final CatalogDao catalogDao;
    private final Validator<Product> validator;

    public ProductServiceImpl(ProductDao productDao, CatalogDao catalogDao, Validator<Product> validator) {
        this.productDao = productDao;
        this.catalogDao = catalogDao;
        this.validator = validator;
    }

    @Override
    public void addProduct(Product product) {
        log.info("Adding product {} started", product);
        if (productDao.findByName(product.getProductName()).isPresent()) {
            log.error("Product {} already exists", product);
            throw new EntityAlreadyExistException(PRODUCT_ALREADY_EXISTS);
        }
        log.info("Validating product: {}", product);
        validator.validate(product);
        productDao.save(product);
        log.info("Product {} successfully added.", product);
    }

    @Override
    public Product findProductById(Integer id) {
        log.info("Find product by id = {} started", id);
        return productDao.findById(id).orElseThrow(() -> {
            log.error("Product is not found");
            return new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        });
    }

    @Override
    public List<Product> findAllProduct(int page, int itemsPerPage) {
        log.info("Find all product on the page = {} started", page);
        return productDao.findAll(page, itemsPerPage);
    }

    @Override
    public List<Product> findAllProduct() {
        log.info("Find all product started");
        return productDao.findAll();
    }

    @Override
    public void updateProduct(Product product) {
        log.info("Updating product {} started", product);
        if (!productDao.findById(product.getId()).isPresent()) {
            log.error("Product {} is not found", product);
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        log.info("Validating product: {}", product);
        validator.validate(product);
        productDao.update(product);
        log.info("Product {} successfully updated.", product);
    }

    @Override
    public void removeProductById(Integer id) {
        log.info("Product delete by id = {} started", id);
        if (!productDao.findById(id).isPresent()) {
            log.error("Product is not found");
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        productDao.deleteById(id);
        log.info("Product {} successfully deleted.", id);
    }

    @Override
    public List<Product> findAllByCatalogName(String categoryName) {
        log.info("Find all product by category name {} started", categoryName);
        return productDao.findAllProductsByCategoryName(categoryName);
    }

    @Override
    public void addProductToCatalog(Integer catalogId, Integer productId) {
        log.info("Adding product id = {} to catalog id = {} started", productId, catalogId);
        if (!productDao.findById(productId).isPresent()) {
            log.error("Product is not found");
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        if (!catalogDao.findById(catalogId).isPresent()) {
            log.error("Catalog is not found");
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        productDao.addProductOnCatalog(catalogId, productId);
        log.info("Product successfully added to catalog");
    }

    @Override
    public void removeProductFromCatalog(Integer catalogId, Integer productId) {
        log.info("Product id = {} from catalog id = {} removal started", productId, catalogId);
        if (!productDao.findById(productId).isPresent()) {
            log.error("Product is not found");
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        if (!catalogDao.findById(catalogId).isPresent()) {
            log.error("Catalog is not found");
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        productDao.removeProductFromCatalog(catalogId, productId);
        log.info("Product successfully remove from catalog");
    }
}
