package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);

    Product findProductById(Integer id);

    List<Product> findAllProduct(int page, int itemsPerPage);

    void updateProduct(Product product);

    void removeProductById(Integer id);

    List<Product> findAllByCatalogName(String categoryName);

    void addProductOnCatalog(Integer catalogId, Integer productId);

    void removeProductFromCatalog(Integer catalogId, Integer productId);
}
