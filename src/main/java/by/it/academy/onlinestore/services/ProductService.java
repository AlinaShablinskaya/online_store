package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);

    Product findProductById(Integer id);

    List<Product> findAllProduct(Pageable pageable);

    Product updateProduct(Product product);

    void removeProductById(Integer id);

    List<Product> findAllByCatalogName(String categoryName);

    void addProductToCatalog(Integer catalogId, Integer productId);

    void removeProductFromCatalog(Integer catalogId, Integer productId);
}
