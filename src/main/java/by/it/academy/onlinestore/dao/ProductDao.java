package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends CrudDao<Product, Integer> {
    Optional<Product> findByName(String name);
    void  addProductOnCatalog(Integer catalogId, Integer productId);

    void removeProductFromCatalog(Integer catalogId, Integer productId);

    List<Product> findAllProductsByCategoryName(String categoryName);
}
