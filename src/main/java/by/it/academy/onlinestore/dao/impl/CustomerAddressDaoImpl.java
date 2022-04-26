package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.CustomerAddressDao;
import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.entities.CustomerAddress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerAddressDaoImpl extends AbstractCrudDaoImpl<CustomerAddress> implements CustomerAddressDao {
    private static final String SAVE_QUERY =
            "INSERT INTO online_store.customer_address(zipcode, country, street) VALUES (?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM online_store.customer_address WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM online_store.customer_address limit ? offset ?";
    private static final String UPDATE_QUERY =
            "UPDATE online_store.customer_address SET zipcode = ?, country = ?, street = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM online_store.customer_address WHERE id = ?";

    public CustomerAddressDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected CustomerAddress mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return CustomerAddress.builder()
                .withId(resultSet.getInt("id"))
                .withZipcode(resultSet.getString("zipcode"))
                .withCountry(resultSet.getString("country"))
                .withStreet(resultSet.getString("street"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, CustomerAddress customerAddress) throws SQLException {
        preparedStatement.setString(1, customerAddress.getZipcode());
        preparedStatement.setString(2, customerAddress.getCountry());
        preparedStatement.setString(3, customerAddress.getStreet());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, CustomerAddress customerAddress) throws SQLException {
        preparedStatement.setString(1, customerAddress.getZipcode());
        preparedStatement.setString(2, customerAddress.getCountry());
        preparedStatement.setString(3, customerAddress.getStreet());
        preparedStatement.setInt(4, customerAddress.getId());
    }
}
