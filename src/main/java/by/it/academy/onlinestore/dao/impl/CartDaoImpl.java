package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.CartDao;
import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDaoImpl extends AbstractCrudDaoImpl<Cart> implements CartDao {
    private static final String SAVE_QUERY = "INSERT INTO online_store.cart(user_id, total_sum) " +
            "VALUES (?, ?) RETURNING id";
    private static final String SAVE_ALL_QUERY = "INSERT INTO online_store.cart(user_id, total_sum) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM online_store.cart " +
            "LEFT OUTER JOIN online_store.user ON online_store.cart.user_id = online_store.user.id " +
            "WHERE online_store.cart.id = ?";
    private static final String FIND_ALL_QUERY_ON_PAGE = "SELECT * FROM online_store.cart " +
            "LEFT OUTER JOIN online_store.user ON online_store.cart.user_id = online_store.user.id " +
            "limit ? offset ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM online_store.cart ORDER BY id";
    private static final String UPDATE_QUERY =
            "UPDATE online_store.cart SET total_sum = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM online_store.cart WHERE id = ?";

    public CartDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, SAVE_ALL_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY_ON_PAGE, FIND_ALL_QUERY,
                UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected Cart mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .withId(resultSet.getInt("user_id"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .build();

        return Cart.builder()
                .withId(resultSet.getInt("id"))
                .withTotalSum(resultSet.getBigDecimal("total_sum"))
                .withUser(user)
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Cart cart) throws SQLException {
        preparedStatement.setInt(1, cart.getUser().getId());
        preparedStatement.setBigDecimal(2, cart.getTotalSum());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Cart cart) throws SQLException {
        preparedStatement.setBigDecimal(1, cart.getTotalSum());
        preparedStatement.setInt(2, cart.getId());
    }
}
