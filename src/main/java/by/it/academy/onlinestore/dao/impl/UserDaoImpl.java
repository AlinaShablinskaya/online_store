package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.dao.UserDao;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends AbstractCrudDaoImpl<User> implements UserDao {
    private static final String SAVE_QUERY =
            "INSERT INTO online_store.user(id, first_name, last_name, login, password, address_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM online_store.user " +
            "LEFT OUTER JOIN online_store.customer_address ON online_store.user.address_id = online_store.customer_address.id " +
            "WHERE online_store.user.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM online_store.user " +
            "LEFT OUTER JOIN online_store.customer_address ON online_store.user.address_id = online_store.customer_address.id " +
            "limit ? offset ?;";
    private static final String UPDATE_QUERY =
            "UPDATE online_store.user SET first_name = ?, last_name = ?, login = ?, password = ?, address_id = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM online_store.user WHERE id = ?";

    public UserDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getLastName());
        preparedStatement.setString(4, user.getLogin());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setInt(6, user.getAddress().getId());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getLogin());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setInt(5, user.getAddress().getId());
        preparedStatement.setInt(6, user.getId());
    }

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        CustomerAddress customerAddress = CustomerAddress.builder()
                .withId(resultSet.getInt("address_id"))
                .withZipcode(resultSet.getString("zipcode"))
                .withCountry(resultSet.getString("country"))
                .withStreet(resultSet.getString("street"))
                .build();

        return User.builder().withId(resultSet.getInt("id"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withLogin(resultSet.getString("login"))
                .withPassword(resultSet.getString("password"))
                .withAddress(customerAddress)
                .build();
    }
}
