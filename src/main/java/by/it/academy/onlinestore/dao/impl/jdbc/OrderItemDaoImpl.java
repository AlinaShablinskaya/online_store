package by.it.academy.onlinestore.dao.impl.jdbc;

import by.it.academy.onlinestore.dao.DBConnector;
import by.it.academy.onlinestore.dao.OrderItemDao;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDaoImpl extends AbstractCrudDaoImpl<OrderItem> implements OrderItemDao {
    private static final String SAVE_QUERY =
            "INSERT INTO online_store.order_item(amount, product_id, total_price) VALUES (?, ?, ?) RETURNING id";
    private static final String SAVE_ALL_QUERY =
            "INSERT INTO online_store.order_item(amount, product_id, total_price) VALUES (?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM online_store.order_item " +
            "LEFT OUTER JOIN online_store.product ON online_store.order_item.product_id = online_store.product.id " +
            "WHERE online_store.order_item.id = ?";
    private static final String FIND_ALL_QUERY_ON_PAGE = "SELECT * FROM online_store.order_item " +
            "LEFT OUTER JOIN online_store.product ON online_store.order_item.product_id = online_store.product.id " +
            "limit ? offset ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM online_store.order_item ORDER BY id";
    private static final String UPDATE_QUERY =
            "UPDATE online_store.order_item SET amount = ?, product_id = ?, total_price = ?  WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM online_store.order_item WHERE id = ?";
    private static final String CREATE_CART_ORDER_ITEM_RELATION =
            "INSERT INTO online_store.cart_order_item(order_item_id, cart_id) VALUES (?, ?)";
    public static final String REMOVE_ORDER_ITEM_FROM_CART =
            "DELETE FROM online_store.cart_order_item WHERE order_item_id = ? AND cart_id = ?";
    private static final String FIND_BY_CART_ID_QUERY = "SELECT * FROM online_store.order_item " +
            "LEFT OUTER JOIN online_store.product ON online_store.order_item.product_id = online_store.product.id " +
            "WHERE online_store.order_item.id IN (SELECT order_item_id FROM online_store.cart_order_item WHERE cart_id IN " +
            "(SELECT id FROM online_store.cart WHERE id = ?)) ORDER BY online_store.order_item.id ";

    public OrderItemDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, SAVE_ALL_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY_ON_PAGE, FIND_ALL_QUERY,
                UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected OrderItem mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Product product = Product.builder()
                .withId(resultSet.getInt("product_id"))
                .withProductName(resultSet.getString("product_name"))
                .withBrand(resultSet.getString("brand"))
                .withPhoto(resultSet.getString("photo"))
                .withPrice(resultSet.getBigDecimal("price"))
                .build();

        return OrderItem.builder()
                .withId(resultSet.getInt("id"))
                .withAmount(resultSet.getInt("amount"))
                .withProduct(product)
                .withTotalPrice(resultSet.getBigDecimal("total_price"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, OrderItem orderItem) throws SQLException {
        preparedStatement.setInt(1, orderItem.getAmount());
        preparedStatement.setInt(2, orderItem.getProduct().getId());
        preparedStatement.setBigDecimal(3, orderItem.getTotalPrice());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, OrderItem orderItem) throws SQLException {
        preparedStatement.setInt(1, orderItem.getAmount());
        preparedStatement.setInt(2, orderItem.getProduct().getId());
        preparedStatement.setBigDecimal(3, orderItem.getTotalPrice());
        preparedStatement.setInt(4, orderItem.getId());
    }

    @Override
    public void addOrderItemOnCart(Integer orderItemId, Integer cartId) {
        saveRelation(orderItemId, cartId, CREATE_CART_ORDER_ITEM_RELATION);
    }

    @Override
    public void removeOrderItemFromCart(Integer orderItemId, Integer cartId) {
        removeRelation(orderItemId, cartId, REMOVE_ORDER_ITEM_FROM_CART);
    }

    @Override
    public List<OrderItem> findByCartId(Integer cartId) {
        return findAllByIntParameter(cartId, FIND_BY_CART_ID_QUERY);
    }
}
