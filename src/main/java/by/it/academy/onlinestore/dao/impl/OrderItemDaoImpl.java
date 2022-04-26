package by.it.academy.onlinestore.dao.impl;

import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.dao.OrderItemDao;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemDaoImpl extends AbstractCrudDaoImpl<OrderItem> implements OrderItemDao {
    private static final String SAVE_QUERY =
            "INSERT INTO online_store.order_item(amount, product_id) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM online_store.order_item " +
            "LEFT OUTER JOIN online_store.product ON online_store.order_item.product_id = online_store.product.id " +
            "WHERE online_store.order_item.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM online_store.order_item " +
            "LEFT OUTER JOIN online_store.product ON online_store.order_item.product_id = online_store.product.id " +
            "limit ? offset ?";
    private static final String UPDATE_QUERY =
            "UPDATE online_store.order_item SET amount = ?, product_id = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM online_store.order_item WHERE id = ?";

    private static final String CREATE_CART_ORDER_ITEM_RELATION =
            "INSERT INTO online_store.cart_order_item(cart_id, order_item_id) VALUES (?, ?)";

    public static final String REMOVE_ORDER_ITEM_FROM_CART =
            "DELETE FROM online_store.cart_order_item WHERE cart_id = ? AND order_item_id = ?";

    public OrderItemDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected OrderItem mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Product product = Product.builder()
                .withId(resultSet.getInt("product_id"))
                .withProductName(resultSet.getString("product_name"))
                .withBrand(resultSet.getString("brand"))
                .withPhoto(resultSet.getString("photo"))
                .withPrice(resultSet.getInt("price"))
                .build();

        return OrderItem.builder()
                .withId(resultSet.getInt("id"))
                .withAmount(resultSet.getInt("amount"))
                .withProduct(product)
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, OrderItem orderItem) throws SQLException {
        preparedStatement.setInt(1, orderItem.getAmount());
        preparedStatement.setInt(2, orderItem.getProduct().getId());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, OrderItem orderItem) throws SQLException {
        preparedStatement.setInt(1, orderItem.getAmount());
        preparedStatement.setInt(2, orderItem.getProduct().getId());
        preparedStatement.setInt(3, orderItem.getId());
    }

    @Override
    public void addOrderItemOnCart(Integer orderItemId, Integer cartId) {
        saveRelation(orderItemId, cartId, CREATE_CART_ORDER_ITEM_RELATION);
    }

    @Override
    public void removeOrderItemFromCart(Integer orderItemId, Integer cartId) {
        removeRelation(orderItemId, cartId, REMOVE_ORDER_ITEM_FROM_CART);
    }
}
