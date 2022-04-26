package by.it.academy.onlinestore.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;

import by.it.academy.onlinestore.dao.impl.CartDaoImpl;
import by.it.academy.onlinestore.dao.impl.CustomerAddressDaoImpl;
import by.it.academy.onlinestore.dao.impl.UserDaoImpl;
import by.it.academy.onlinestore.entities.Cart;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.entities.Role;
import by.it.academy.onlinestore.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CartDaoTest {
    private final static String PROPERTIES = "src/test/resources/h2.properties";
    private final static String SCRIPT_SQL = "src/test/resources/schema.sql";

    private List<Cart> carts = new ArrayList<>();
    private DBConnector connector;
    private TableCreator tableCreator;
    private CartDao cartDao;
    private UserDao userDao;

    private CustomerAddressDao addressDao;

    @BeforeEach
    private void prepareTables() {
        connector = new DBConnector(PROPERTIES);
        tableCreator = new TableCreator(connector);
        cartDao = new CartDaoImpl(connector);
        userDao = new UserDaoImpl(connector);
        addressDao = new CustomerAddressDaoImpl(connector);

        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddCartToTheDatabase() {
        User firstUser = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build();

        Cart expected = Cart.builder()
                .withId(3)
                .withUser(firstUser)
                .build();

        cartDao.save(expected);
        Cart actual = cartDao.findById(3).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldAddListOfCartToTheDatabase() {
        List<Cart> expected = new ArrayList<>();

        User firstUser = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build();

        User secondUser = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build();

        expected.add(Cart.builder()
                .withId(3)
                .withUser(firstUser)
                .build());

        expected.add(Cart.builder()
                .withId(4)
                .withUser(secondUser)
                .build());

        cartDao.saveAll(expected);
        List<Cart> actual = cartDao.findAll(2, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByIdShouldReturnOrderItemWhenGetId() {
        User firstUser = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build();

        Cart expected = Cart.builder()
                .withId(1)
                .withUser(firstUser)
                .build();

        Cart actual = cartDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfOrderItemWhenGetParameters() {
        List<Cart> expected = new ArrayList<>();

        User firstUser = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build();

        User secondUser = User.builder()
                .withId(2)
                .withFirstName("SecondUser")
                .withLastName("LastName")
                .withEmail("SecondLogin")
                .withPassword("78954")
                .build();

        expected.add(Cart.builder()
                .withId(1)
                .withUser(firstUser)
                .build());

        expected.add(Cart.builder()
                .withId(2)
                .withUser(secondUser)
                .build());


        List<Cart> actual = cartDao.findAll(0, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {
        User firstUser = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build();

        Cart expected = Cart.builder()
                .withId(1)
                .withUser(firstUser)
                .build();

        cartDao.update(expected);
        Cart actual = cartDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdShouldDeleteOrderItemWhenGetId() {
        cartDao.deleteById(1);
        Cart actual = cartDao.findById(1).orElse(null);

        assertThat(actual).isNull();
    }

    private void createTestData() {
        tableCreator.runScript(SCRIPT_SQL);

        CustomerAddress customerAddress = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build();

        addressDao.save(customerAddress);

        Role role = new Role("USER");

        User firstUser = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .withAddress(customerAddress)
                .withRole(role)
                .build();

        User secondUser = User.builder()
                .withId(2)
                .withFirstName("SecondUser")
                .withLastName("LastName")
                .withEmail("SecondLogin")
                .withPassword("78954")
                .withAddress(customerAddress)
                .withRole(role)
                .build();

        userDao.save(firstUser);
        userDao.save(secondUser);

        carts.add(Cart.builder()
                .withId(1)
                .withUser(firstUser)
                .build());

        carts.add(Cart.builder()
                .withId(2)
                .withUser(secondUser)
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        cartDao.saveAll(carts);
    }
}
