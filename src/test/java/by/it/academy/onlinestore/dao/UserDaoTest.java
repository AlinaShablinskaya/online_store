package by.it.academy.onlinestore.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.CustomerAddressDaoImpl;
import by.it.academy.onlinestore.dao.impl.UserDaoImpl;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.entities.Role;
import by.it.academy.onlinestore.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserDaoTest {
    private final static String PROPERTIES = "src/test/resources/h2.properties";
    private final static String SCRIPT_SQL = "src/test/resources/schema.sql";

    private List<User> users = new ArrayList<>();
    private DBConnector connector;
    private TableCreator tableCreator;
    private UserDao userDao;
    private CustomerAddressDao addressDao;

    @BeforeEach
    private void prepareTables() {
        connector = new DBConnector(PROPERTIES);
        tableCreator = new TableCreator(connector);
        userDao = new UserDaoImpl(connector);
        addressDao = new CustomerAddressDaoImpl(connector);

        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddListOfUsersToTheDatabase() {
        List<User> expected = new ArrayList<>();

        CustomerAddress firstAddress = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build();

        CustomerAddress secondAddress = CustomerAddress.builder()
                .withId(2)
                .withZipcode("1564822")
                .withCountry("England")
                .withStreet("Piccadilly Street")
                .build();

        Role role = new Role("USER");

        expected.add(User.builder()
                .withId(3)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .withRole(role)
                .withAddress(firstAddress)
                .build());

        expected.add(User.builder()
                .withId(4)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .withRole(role)
                .withAddress(secondAddress)
                .build());

        userDao.saveAll(expected);
        List<User> actual = userDao.findAll(2, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByIdShouldReturnProductWhenGetId() {
        CustomerAddress firstAddress = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build();

        Role role = new Role("USER");

        User expected = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .withRole(role)
                .withAddress(firstAddress)
                .build();

        User actual = userDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfProductWhenGetParameters() {
        List<User> expected = new ArrayList<>();

        CustomerAddress firstAddress = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build();

        CustomerAddress secondAddress = CustomerAddress.builder()
                .withId(2)
                .withZipcode("1564822")
                .withCountry("England")
                .withStreet("Piccadilly Street")
                .build();

        Role role = new Role("USER");

        expected.add(User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .withRole(role)
                .withAddress(firstAddress)
                .build());

        expected.add(User.builder()
                .withId(2)
                .withFirstName("SecondUser")
                .withLastName("LastName")
                .withEmail("SecondLogin")
                .withPassword("78954")
                .withRole(role)
                .withAddress(secondAddress)
                .build());

        List<User> actual = userDao.findAll(0, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {
        CustomerAddress firstAddress = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build();

        Role role = new Role("USER");

        User expected = User.builder()
                .withId(1)
                .withFirstName("NewName")
                .withLastName("NewLastName")
                .withEmail("NewLogin")
                .withPassword("12345")
                .withRole(role)
                .withAddress(firstAddress)
                .build();

        userDao.update(expected);
        User actual = userDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findByEmailShouldReturnUserWhenGetEmail() {
        CustomerAddress firstAddress = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build();

        Role role = new Role("USER");

        User expected = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .withRole(role)
                .withAddress(firstAddress)
                .build();

        User actual = userDao.findByEmail(expected.getEmail()).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdShouldDeleteProductWhenGetId() {
        userDao.deleteById(1);
        User actual = userDao.findById(1).orElse(null);

        assertThat(actual).isNull();
    }

    private void createTestData() {
        tableCreator.runScript(SCRIPT_SQL);
        List<CustomerAddress> addresses = new ArrayList<>();

        addresses.add(CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build());

        addresses.add(CustomerAddress.builder()
                .withId(2)
                .withZipcode("1564822")
                .withCountry("England")
                .withStreet("Piccadilly Street")
                .build());

        Role role = new Role("USER");

        addressDao.saveAll(addresses);

        users.add(User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .withRole(role)
                .withAddress(addresses.get(0))
                .build());

        users.add(User.builder()
                .withId(2)
                .withFirstName("SecondUser")
                .withLastName("LastName")
                .withEmail("SecondLogin")
                .withPassword("78954")
                .withRole(role)
                .withAddress(addresses.get(1))
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        userDao.saveAll(users);
    }
}
