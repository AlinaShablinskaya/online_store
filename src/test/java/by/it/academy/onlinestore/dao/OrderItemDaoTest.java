package by.it.academy.onlinestore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.OrderItemDaoImpl;
import by.it.academy.onlinestore.dao.impl.ProductDaoImpl;
import by.it.academy.onlinestore.entities.OrderItem;
import by.it.academy.onlinestore.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemDaoTest {
    private final static String PROPERTIES = "src/test/resources/h2.properties";
    private final static String SCRIPT_SQL = "src/test/resources/schema.sql";

    private List<OrderItem> orderItems = new ArrayList<>();
    private DBConnector connector;
    private TableCreator tableCreator;
    private OrderItemDao orderItemDao;
    private ProductDao productDao;

    @BeforeEach
    private void prepareTables() {
        connector = new DBConnector(PROPERTIES);
        tableCreator = new TableCreator(connector);
        orderItemDao = new OrderItemDaoImpl(connector);
        productDao = new ProductDaoImpl(connector);

        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddOrderItemToTheDatabase() {

        Product firstProduct = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(30)
                .build();

        OrderItem expected = OrderItem.builder()
                .withId(3)
                .withAmount(3)
                .withProduct(firstProduct)
                .build();

        orderItemDao.save(expected);
        OrderItem actual = orderItemDao.findById(3).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldAddListOfOrderItemToTheDatabase() {
        List<OrderItem> expected = new ArrayList<>();

        Product firstProduct = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(30)
                .build();

        Product secondProduct = Product.builder()
                .withId(2)
                .withProductName("Beer")
                .withProductDescription("Description")
                .withPrice(5)
                .build();

        expected.add(OrderItem.builder()
                .withId(3)
                .withAmount(3)
                .withProduct(firstProduct)
                .build());

        expected.add(OrderItem.builder()
                .withId(4)
                .withAmount(2)
                .withProduct(secondProduct)
                .build());

        orderItemDao.saveAll(expected);
        List<OrderItem> actual = orderItemDao.findAll(2, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByIdShouldReturnOrderItemWhenGetId() {
        Product firstProduct = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(30)
                .build();

        OrderItem expected = OrderItem.builder()
                .withId(1)
                .withAmount(3)
                .withProduct(firstProduct)
                .build();

        OrderItem actual = orderItemDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfOrderItemWhenGetParameters() {
        List<OrderItem> expected = new ArrayList<>();

        Product firstProduct = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(30)
                .build();

        Product secondProduct = Product.builder()
                .withId(2)
                .withProductName("Beer")
                .withProductDescription("Description")
                .withPrice(5)
                .build();

        expected.add(OrderItem.builder()
                .withId(1)
                .withAmount(3)
                .withProduct(firstProduct)
                .build());

        expected.add(OrderItem.builder()
                .withId(2)
                .withAmount(2)
                .withProduct(secondProduct)
                .build());


        List<OrderItem> actual = orderItemDao.findAll(0, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {
        Product firstProduct = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(30)
                .build();

        OrderItem expected = OrderItem.builder()
                .withId(1)
                .withAmount(10)
                .withProduct(firstProduct)
                .build();

        orderItemDao.update(expected);
        OrderItem actual = orderItemDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdShouldDeleteOrderItemWhenGetId() {
        orderItemDao.deleteById(1);
        OrderItem actual = orderItemDao.findById(1).orElse(null);

        assertThat(actual).isNull();
    }

    private void createTestData() {
        tableCreator.runScript(SCRIPT_SQL);

        Product firstProduct = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(30)
                .build();

        Product secondProduct = Product.builder()
                .withId(2)
                .withProductName("Beer")
                .withProductDescription("Description")
                .withPrice(5)
                .build();

        productDao.save(firstProduct);
        productDao.save(secondProduct);

        orderItems.add(OrderItem.builder()
                .withId(1)
                .withAmount(3)
                .withProduct(firstProduct)
                .build());

        orderItems.add(OrderItem.builder()
                .withId(2)
                .withAmount(2)
                .withProduct(secondProduct)
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        orderItemDao.saveAll(orderItems);
    }
}
