package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.entities.Product;

import java.util.List;

public interface ProductDao extends CrudDao<Product, Integer> {
    void  addProductOnCatalog(Integer catalogId, Integer productId);

    void removeProductFromCatalog(Integer catalogId, Integer productId);

    List<Product> findAllProductsByCategoryName(String categoryName);
}
