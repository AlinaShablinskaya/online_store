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

class CartDaoTest {
    private final static String PROPERTIES = "src/test/resources/h2.properties";
    private final static String SCRIPT_SQL = "src/test/resources/schema.sql";

    private final List<Cart> carts = new ArrayList<>();
    private TableCreator tableCreator;
    private CartDao cartDao;
    private UserDao userDao;

    private CustomerAddressDao addressDao;

    @BeforeEach
    private void prepareTables() {
        DBConnector connector = new DBConnector(PROPERTIES);
        tableCreator = new TableCreator(connector);
        cartDao = new CartDaoImpl(connector);
        userDao = new UserDaoImpl(connector);
        addressDao = new CustomerAddressDaoImpl(connector);

        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveAllShouldAddListOfCartToTheDatabase() {
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
        List<CustomerAddress> addresses = new ArrayList<>();
        List<User> users = new ArrayList<>();

        addresses.add(CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build());

        addressDao.saveAll(addresses);

        Role role = new Role("USER");

        users.add(User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .withAddress(addresses.get(0))
                .withRole(role)
                .build());

        users.add(User.builder()
                .withId(2)
                .withFirstName("SecondUser")
                .withLastName("LastName")
                .withEmail("SecondLogin")
                .withPassword("78954")
                .withRole(role)
                .withAddress(addresses.get(0))
                .build());

        userDao.saveAll(users);

        carts.add(Cart.builder()
                .withId(1)
                .withUser(users.get(0))
                .build());

        carts.add(Cart.builder()
                .withId(2)
                .withUser(users.get(1))
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        cartDao.saveAll(carts);
    }
}
