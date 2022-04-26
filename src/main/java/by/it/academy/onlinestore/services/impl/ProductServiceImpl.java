package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CatalogDao;
import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.ProductService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static final Logger lOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private static final String PRODUCT_ALREADY_EXISTS = "Specified product already exists.";
    private static final String PRODUCT_IS_NOT_FOUND = "Specified product is not found.";
    private static final String CATALOG_IS_NOT_FOUND = "Specified catalog is not found.";
    private  final ProductDao productDao;
    private  final CatalogDao catalogDao;

    public ProductServiceImpl(ProductDao productDao, CatalogDao catalogDao) {
        this.productDao = productDao;
        this.catalogDao = catalogDao;
    }

    @Override
    public void addProduct(Product product) {
        if (productDao.findByName(product.getProductName()).isPresent()) {
            throw new EntityAlreadyExistException(PRODUCT_ALREADY_EXISTS);
        }
        productDao.save(product);

        lOGGER.info("Product successfully added.");
    }

    @Override
    public Product findProductById(Integer id) {
        return productDao.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        });
    }

    @Override
    public List<Product> findAllProduct(int page, int itemsPerPage) {
        return productDao.findAll(page, itemsPerPage);
    }

    @Override
    public void updateProduct(Product product) {
        if (!productDao.findById(product.getId()).isPresent()) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        productDao.update(product);

        lOGGER.info("Product successfully updated.");
    }

    @Override
    public void removeProductById(Integer id) {
        if (!productDao.findById(id).isPresent()) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }
        productDao.deleteById(id);

        lOGGER.info("Product successfully deleted.");
    }

    @Override
    public List<Product> findAllByCatalogName(String categoryName) {
        return productDao.findAllProductsByCategoryName(categoryName);
    }

    @Override
    public void addProductOnCatalog(Integer catalogId, Integer productId) {
        if (!productDao.findById(productId).isPresent()) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }

        if (!catalogDao.findById(catalogId).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }

        productDao.addProductOnCatalog(catalogId, productId);

        lOGGER.info("Successfully added product to catalog");
    }

    @Override
    public void removeProductFromCatalog(Integer catalogId, Integer productId) {
        if (!productDao.findById(productId).isPresent()) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_FOUND);
        }

        if (!catalogDao.findById(catalogId).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }

        productDao.removeProductFromCatalog(catalogId, productId);

        lOGGER.info("Successfully remove product from catalog");
    }
}
