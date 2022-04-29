package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.entities.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl extends AbstractCrudDaoImpl<Product> implements ProductDao {
    private static final String SAVE_QUERY =
            "INSERT INTO online_store.product(product_name, brand, photo, price) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM online_store.product WHERE id = ?";
    private static final String FIND_ALL_QUERY_ON_PAGE = "SELECT * FROM online_store.product limit ? offset ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM online_store.product ORDER BY id";
    private static final String UPDATE_QUERY =
            "UPDATE online_store.product SET product_name = ?, brand = ?, photo = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM online_store.product WHERE id = ?";

    private static final String CREATE_CATALOG_PRODUCT =
            "INSERT INTO online_store.catalog_product(catalog_id, product_id) VALUES (?, ?)";

    public static final String REMOVE_PRODUCT_FROM_CATALOG =
            "DELETE FROM online_store.catalog_product WHERE catalog_id = ? AND product_id = ?";

    public static final String FIND_ALL_PRODUCT_BY_GROUP_NAME = "SELECT * FROM online_store.product WHERE id IN "
            + "(SELECT product_id FROM online_store.catalog_product WHERE catalog_id IN "
            + "(SELECT id FROM online_store.catalog WHERE group_name = ?)) ORDER BY id";

    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM online_store.product WHERE product_name = ?";

    public ProductDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY_ON_PAGE, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected Product mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .withId(resultSet.getInt("id"))
                .withProductName(resultSet.getString("product_name"))
                .withBrand(resultSet.getString("brand"))
                .withPhoto(resultSet.getString("photo"))
                .withPrice(resultSet.getInt("price"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setString(2, product.getBrand());
        preparedStatement.setString(3, product.getPhoto());
        preparedStatement.setInt(4, product.getPrice());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getProductName());
        preparedStatement.setString(2, product.getBrand());
        preparedStatement.setString(3, product.getPhoto());
        preparedStatement.setInt(4, product.getPrice());
        preparedStatement.setInt(5, product.getId());
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

    @Override
    public Optional<Product> findByName(String name) {
        return findByStringParameter(name, FIND_BY_NAME_QUERY);
    }
}
