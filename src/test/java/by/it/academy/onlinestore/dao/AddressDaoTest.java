package by.it.academy.onlinestore.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.CustomerAddressDaoImpl;
import by.it.academy.onlinestore.entities.CustomerAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AddressDaoTest {
    private final static String PROPERTIES = "src/test/resources/h2.properties";
    private final static String SCRIPT_SQL = "src/test/resources/schema.sql";

    private List<CustomerAddress> addresses = new ArrayList<>();
    private DBConnector connector;
    private TableCreator tableCreator;
    private CustomerAddressDao customerAddressDao;

    @BeforeEach
    private void prepareTables() {
        connector = new DBConnector(PROPERTIES);
        tableCreator = new TableCreator(connector);
        customerAddressDao = new CustomerAddressDaoImpl(connector);

        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddProductToTheDatabase() {

        CustomerAddress expected = CustomerAddress.builder()
                .withId(3)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build();

        customerAddressDao.save(expected);
        CustomerAddress actual = customerAddressDao.findById(3).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldAddListOfProductToTheDatabase() {
        List<CustomerAddress> expected = new ArrayList<>();

        expected.add(CustomerAddress.builder()
                .withId(3)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build());

        expected.add(CustomerAddress.builder()
                .withId(4)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build());

        customerAddressDao.saveAll(expected);
        List<CustomerAddress> actual = customerAddressDao.findAll(2, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByIdShouldReturnProductWhenGetId() {
        CustomerAddress expected = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build();

        CustomerAddress actual = customerAddressDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfProductWhenGetParameters() {
        List<CustomerAddress> expected = new ArrayList<>();

        CustomerAddress firstProduct = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Oxford Street")
                .build();

        CustomerAddress secondProduct = CustomerAddress.builder()
                .withId(2)
                .withZipcode("1564822")
                .withCountry("England")
                .withStreet("Piccadilly Street")
                .build();

        expected.add(firstProduct);
        expected.add(secondProduct);

        List<CustomerAddress> actual = customerAddressDao.findAll(0, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {
        CustomerAddress expected = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123654")
                .withCountry("England")
                .withStreet("Other Street")
                .build();

        customerAddressDao.update(expected);
        CustomerAddress actual = customerAddressDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdShouldDeleteProductWhenGetId() {
        customerAddressDao.deleteById(1);
        CustomerAddress actual = customerAddressDao.findById(1).orElse(null);

        assertThat(actual).isNull();
    }

    private void createTestData() {
        tableCreator.runScript(SCRIPT_SQL);

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
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        customerAddressDao.saveAll(addresses);
    }
}
