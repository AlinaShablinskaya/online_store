package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.entities.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl extends AbstractCrudDaoImpl<Product> implements ProductDao {
    private static final String SAVE_QUERY =
            "INSERT INTO online_store.product(id, product_name, product_description, price) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM online_store.product WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM online_store.product limit ? offset ?";
    private static final String UPDATE_QUERY =
            "UPDATE online_store.product SET product_name = ?, product_description = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM online_store.product WHERE id = ?";

    private static final String CREATE_CATALOG_PRODUCT =
            "INSERT INTO online_store.catalog_product(catalog_id, product_id) VALUES (?, ?)";

    public static final String REMOVE_PRODUCT_FROM_CATALOG =
            "DELETE FROM online_store.catalog_product WHERE catalog_id = ? AND product_id = ?";

    public static final String FIND_ALL_PRODUCT_BY_GROUP_NAME = "SELECT * FROM online_store.product WHERE product_id IN"
            + "(SELECT product_id FROM online_store.catalog_product WHERE catalog_id IN "
            + "(SELECT catalog_id FROM online_store.catalog WHERE group_name = ?)) ORDER BY product_id";

    public ProductDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }


    @Override
    protected Product mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Product.builder().withId(resultSet.getInt("id"))
                .withProductName(resultSet.getString("product_name"))
                .withProductDescription(resultSet.getString("product_description"))
                .withPrice(resultSet.getInt("price"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setInt(1, product.getId());
        preparedStatement.setString(2, product.getProductName());
        preparedStatement.setString(3, product.getProductDescription());
        preparedStatement.setInt(4, product.getPrice());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setString(2, product.getProductDescription());
        preparedStatement.setInt(3, product.getPrice());
        preparedStatement.setInt(4, product.getId());
    }

    @Override
    public void addProductOnCatalog(Integer catalogId, Integer productId) {
        saveRelation(catalogId, productId, CREATE_CATALOG_PRODUCT);
    }

    @Override
    public void removeProductFromCatalog(Integer catalogId, Integer productId) {
        removeRelation(catalogId, productId, REMOVE_PRODUCT_FROM_CATALOG);
    }

    @Override
    public List<Product> findAllProductsByCategoryName(String categoryName) {
        return findAllByStringParam(categoryName, FIND_ALL_PRODUCT_BY_GROUP_NAME);
    }
}
