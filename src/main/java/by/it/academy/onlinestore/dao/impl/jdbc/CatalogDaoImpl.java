package by.it.academy.onlinestore.dao.impl.jdbc;

import by.it.academy.onlinestore.dao.CatalogDao;
import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.entities.Catalog;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CatalogDaoImpl extends AbstractCrudDaoImpl<Catalog> implements CatalogDao {
    private static final String SAVE_QUERY = "INSERT INTO online_store.catalog(group_name) VALUES (?) RETURNING id";
    private static final String SAVE_ALL_QUERY = "INSERT INTO online_store.catalog(group_name) VALUES (?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM online_store.catalog WHERE id = ?";
    private static final String FIND_ALL_QUERY_ON_PAGE = "SELECT * FROM online_store.catalog limit ? offset ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM online_store.catalog ORDER BY id";
    private static final String UPDATE_QUERY =
            "UPDATE online_store.catalog SET group_name = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM online_store.catalog WHERE id = ?";

    private static final String CREATE_CATALOG_PRODUCT =
            "INSERT INTO online_store.catalog_product(catalog_id, product_id) VALUES (?, ?)";

    public static final String DELETE_PRODUCT_FROM_CATALOG =
            "DELETE FROM online_store.catalog_product WHERE catalog_id = ? AND product_id = ?";
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM online_store.catalog WHERE group_name = ?";

    public CatalogDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, SAVE_ALL_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY_ON_PAGE, FIND_ALL_QUERY,
                UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected Catalog mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Catalog.builder()
                .withId(resultSet.getInt("id"))
                .withGroupName(resultSet.getString("group_name"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Catalog catalog) throws SQLException {
        preparedStatement.setString(1, catalog.getGroupName());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Catalog catalog) throws SQLException {
        preparedStatement.setString(1, catalog.getGroupName());
        preparedStatement.setInt(2, catalog.getId());
    }

    @Override
    public Optional<Catalog> findByGroupName(String name) {
        return findByStringParameter(name, FIND_BY_NAME_QUERY);
    }
}
