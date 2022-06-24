package by.it.academy.onlinestore.dao.hibernate;

import by.it.academy.onlinestore.dao.OrderItemDao;
import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.hibernate.OrderItemHibernateDaoImpl;
import by.it.academy.onlinestore.dao.impl.hibernate.ProductHibernateDaoImpl;
import by.it.academy.onlinestore.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemHibernateDaoTest {
    private final TestConfig testConfig = new TestConfig();
    private final List<OrderItem> orderItems = new ArrayList<>();
    private OrderItemDao orderItemDao;
    private ProductDao productDao;

    @BeforeEach
    private void prepareTables() throws IOException {
        testConfig.createTables();
        orderItemDao = new OrderItemHibernateDaoImpl();
        productDao = new ProductHibernateDaoImpl();
        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddOrderItemToTheDatabase() {
        OrderItem expected = OrderItem.builder()
                .withId(3)
                .withAmount(3)
                .build();

        orderItemDao.save(expected);
        OrderItem actual = orderItemDao.findById(3).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldAddListOfOrderItemToTheDatabase() {
        List<OrderItem> expected = new ArrayList<>();

        expected.add(OrderItem.builder()
                .withId(3)
                .withAmount(3)
                .build());

        expected.add(OrderItem.builder()
                .withId(4)
                .withAmount(2)
                .build());

        orderItemDao.saveAll(expected);
        List<OrderItem> actual = orderItemDao.findAll(2, 2);

        assertEquals(expected, actual);
    }

    @Test
    void findByIdShouldReturnOrderItemWhenGetId() {
        OrderItem expected = OrderItem.builder()
                .withId(1)
                .withAmount(3)
                .build();

        OrderItem actual = orderItemDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfOrderItemOnPageWhenGetParameters() {
        List<OrderItem> expected = new ArrayList<>();

        expected.add(OrderItem.builder()
                .withId(1)
                .withAmount(3)
                .build());

        expected.add(OrderItem.builder()
                .withId(2)
                .withAmount(2)
                .build());

        List<OrderItem> actual = orderItemDao.findAll(1, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findAllShouldReturnListOfOrderItemWhenGetParameters() {
        List<OrderItem> expected = new ArrayList<>();

        expected.add(OrderItem.builder()
                .withId(1)
                .withAmount(3)
                .build());

        expected.add(OrderItem.builder()
                .withId(2)
                .withAmount(2)
                .build());

        List<OrderItem> actual = orderItemDao.findAll();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {
        OrderItem expected = OrderItem.builder()
                .withId(1)
                .withAmount(10)
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
        orderItems.add(OrderItem.builder()
                .withId(1)
                .withAmount(3)
                .build());

        orderItems.add(OrderItem.builder()
                .withId(2)
                .withAmount(2)
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        orderItemDao.saveAll(orderItems);
    }
}
