package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.ProductDaoImpl;
import by.it.academy.onlinestore.entities.Product;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProductDaoTest {
    private final static String PROPERTIES = "src/test/resources/h2.properties";
    private final static String SCRIPT_SQL = "src/test/resources/schema.sql";

    private List<Product> products = new ArrayList<>();
    private DBConnector connector;
    private TableCreator tableCreator;
    private ProductDao productDao;

    @BeforeEach
    private void prepareTables() {
        connector = new DBConnector(PROPERTIES);
        tableCreator = new TableCreator(connector);
        productDao = new ProductDaoImpl(connector);

        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddProductToTheDatabase() {
        Product expected = Product.builder()
                .withId(3)
                .withProductName("Beer")
                .withProductDescription("Description")
                .withPrice(5)
                .build();

        productDao.save(expected);
        Product actual = productDao.findById(3).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldAddListOfProductToTheDatabase() {
        List<Product> expected = new ArrayList<>();

        Product firstProduct = Product.builder()
                .withId(3)
                .withProductName("Beer")
                .withProductDescription("Description")
                .withPrice(5)
                .build();

        Product secondProduct = Product.builder()
                .withId(4)
                .withProductName("Rum")
                .withProductDescription("Description")
                .withPrice(5)
                .build();

        expected.add(firstProduct);
        expected.add(secondProduct);

        productDao.saveAll(expected);
        List<Product> actual = productDao.findAll(2, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByIdShouldReturnProductWhenGetId() {
        Product expected = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(30)
                .build();

        Product actual = productDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfProductWhenGetParameters() {
        List<Product> expected = new ArrayList<>();

        Product firstProduct = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(30)
                .build();

        Product secondProduct = Product.builder()
                .withId(2)
                .withProductName("Wine")
                .withProductDescription("Description")
                .withPrice(20)
                .build();

        expected.add(firstProduct);
        expected.add(secondProduct);

        List<Product> actual = productDao.findAll(0, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {
        Product expected = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(100)
                .build();

        productDao.update(expected);
        Product actual = productDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdShouldDeleteProductWhenGetId() {
        productDao.deleteById(1);
        Product actual = productDao.findById(1).orElse(null);

        assertThat(actual).isNull();
    }

    private void createTestData() {
        tableCreator.runScript(SCRIPT_SQL);

        products.add(Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withProductDescription("Description")
                .withPrice(30)
                .build());
        products.add(Product.builder()
                .withId(2)
                .withProductName("Wine")
                .withProductDescription("Description")
                .withPrice(20)
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        productDao.saveAll(products);
    }
}
