package by.it.academy.onlinestore.dao;

import static org.assertj.core.api.Assertions.assertThat;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.CartDaoImpl;
import by.it.academy.onlinestore.dao.impl.OrderItemDaoImpl;
import by.it.academy.onlinestore.dao.impl.ProductDaoImpl;
import by.it.academy.onlinestore.dao.impl.UserDaoImpl;
import by.it.academy.onlinestore.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemDaoTest {
    private final static String PROPERTIES = "src/test/resources/h2.properties";
    private final static String SCRIPT_SQL = "src/test/resources/schema.sql";

    private List<OrderItem> orderItems = new ArrayList<>();
    private List<Cart> carts = new ArrayList<>();
    private DBConnector connector;
    private TableCreator tableCreator;
    private OrderItemDao orderItemDao;
    private ProductDao productDao;
    private CartDao cartDao;
    private UserDao userDao;

    @BeforeEach
    private void prepareTables() {
        connector = new DBConnector(PROPERTIES);
        tableCreator = new TableCreator(connector);
        orderItemDao = new OrderItemDaoImpl(connector);
        productDao = new ProductDaoImpl(connector);
        cartDao = new CartDaoImpl(connector);
        userDao = new UserDaoImpl(connector);

        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddOrderItemToTheDatabase() {

        Product firstProduct = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
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
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
                .build();

        Product secondProduct = Product.builder()
                .withId(2)
                .withProductName("Wine")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(20))
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
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
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
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
                .build();

        Product secondProduct = Product.builder()
                .withId(2)
                .withProductName("Wine")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(20))
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
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
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

    @Test
    void findAllOrderItemsByCartIdShouldReturnOrderItemWhenGetCartId() {
        List<OrderItem> expected = new ArrayList<>();

        Product firstProduct = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
                .build();

        expected.add(OrderItem.builder()
                .withId(1)
                .withAmount(3)
                .withProduct(firstProduct)
                .build());

        List<OrderItem> actual = orderItemDao.findByCartId(1);

        assertEquals(expected, actual);
    }

    private void createTestData() {
        tableCreator.runScript(SCRIPT_SQL);
        List<Product> products = new ArrayList<>();
        List<User> users = new ArrayList<>();

        products.add(Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
                .build());

        products.add(Product.builder()
                .withId(2)
                .withProductName("Wine")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(20))
                .build());

        productDao.saveAll(products);

        orderItems.add(OrderItem.builder()
                .withId(1)
                .withAmount(3)
                .withProduct(products.get(0))
                .build());

        orderItems.add(OrderItem.builder()
                .withId(2)
                .withAmount(2)
                .withProduct(products.get(1))
                .build());

        users.add(User.builder()
                .withId(1)
                .withFirstName("name")
                .withLastName("name")
                .withEmail("email@email.ru")
                .withPassword("Aa123456")
                .withRole(new Role("USER"))
                .build());

        userDao.saveAll(users);

        carts.add(Cart.builder()
                .withId(1)
                .withUser(users.get(0))
                .build());
        cartDao.saveAll(carts);
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        orderItemDao.saveAll(orderItems);
        orderItemDao.addOrderItemOnCart(carts.get(0).getId(), orderItems.get(0).getId());
    }
}
