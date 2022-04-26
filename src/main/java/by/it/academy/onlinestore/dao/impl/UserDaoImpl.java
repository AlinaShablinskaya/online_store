package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.dao.UserDao;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.entities.Role;
import by.it.academy.onlinestore.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractCrudDaoImpl<User> implements UserDao {
    private static final String SAVE_QUERY =
            "INSERT INTO online_store.user(first_name, last_name, email, password, role_name, address_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM online_store.user " +
            "LEFT OUTER JOIN online_store.customer_address ON online_store.user.address_id = online_store.customer_address.id " +
            "WHERE online_store.user.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM online_store.user " +
            "LEFT OUTER JOIN online_store.customer_address ON online_store.user.address_id = online_store.customer_address.id " +
            "limit ? offset ?;";

    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM online_store.user " +
            "LEFT OUTER JOIN online_store.customer_address ON online_store.user.address_id = online_store.customer_address.id " +
            "WHERE online_store.user.email = ?";
    private static final String UPDATE_QUERY =
            "UPDATE online_store.user SET first_name = ?, last_name = ?, email = ?, password = ?, role_name = ?, address_id = ? " +
                    "WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM online_store.user WHERE id = ?";

    public UserDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getRole().getRoleName());
        if (user.getAddress() == null) {
            preparedStatement.setNull(6, java.sql.Types.INTEGER);
        } else {
            preparedStatement.setInt(6, user.getAddress().getId());
        }
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, User user) throws SQLException {
        insert(preparedStatement, user);
        preparedStatement.setInt(7, user.getId());
    }

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        CustomerAddress customerAddress = CustomerAddress.builder()
                .withId(resultSet.getInt("address_id"))
                .withZipcode(resultSet.getString("zipcode"))
                .withCountry(resultSet.getString("country"))
                .withStreet(resultSet.getString("street"))
                .build();

        Role role = new Role(resultSet.getString("role_name"));

        return User.builder().withId(resultSet.getInt("id"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .withRole(role)
                .withAddress(customerAddress)
                .build();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findByStringParameter(email, FIND_BY_EMAIL_QUERY);
    }
}
