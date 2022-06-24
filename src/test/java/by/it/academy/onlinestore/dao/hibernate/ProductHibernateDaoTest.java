package by.it.academy.onlinestore.dao.hibernate;

import by.it.academy.onlinestore.dao.CatalogDao;
import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.hibernate.CatalogHibernateDaoImpl;
import by.it.academy.onlinestore.dao.impl.hibernate.ProductHibernateDaoImpl;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductHibernateDaoTest {
    private final TestConfig testConfig = new TestConfig();
    private final List<Product> products = new ArrayList<>();
    private final List<Catalog> catalog = new ArrayList<>();
    private ProductDao productDao;
    private CatalogDao catalogDao;

    @BeforeEach
    private void prepareTables() throws IOException {
        testConfig.createTables();
        productDao = new ProductHibernateDaoImpl();
        catalogDao = new CatalogHibernateDaoImpl();
        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddProductToTheDatabase() {
        Product product = Product.builder()
                .withId(3)
                .withProductName("Beer")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(5))
                .build();

        Product expected = productDao.save(product).get();
        Product actual = productDao.findById(3).get();
        assertEquals(expected, actual);
    }

    @Test
    void saveShouldAddListOfProductToTheDatabase() {
        List<Product> expected = new ArrayList<>();

        Product firstProduct = Product.builder()
                .withId(3)
                .withProductName("Beer")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(5))
                .build();

        Product secondProduct = Product.builder()
                .withId(4)
                .withProductName("Rum")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(5))
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
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
                .build();

        Product actual = productDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfProductOnPageWhenGetParameters() {
        List<Product> expected = new ArrayList<>();

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

        expected.add(firstProduct);
        expected.add(secondProduct);

        List<Product> actual = productDao.findAll(1, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findAllShouldReturnListOfProductWhenGetParameters() {
        List<Product> expected = new ArrayList<>();

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

        expected.add(firstProduct);
        expected.add(secondProduct);

        List<Product> actual = productDao.findAll();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByNameShouldReturnProductWhenGetProductName() {
        Product expected = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
                .build();

        Product actual = productDao.findByName("Whiskey").orElseThrow(null);
        assertEquals(expected, actual);
    }

    @Test
    void findAllProductsByCategoryNameShouldReturnListOfProducts() {
        List<Product> expected = new ArrayList<>();

        expected.add(Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(30))
                .build());

        List<Product> actual = productDao.findAllProductsByCategoryName("catalog");
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldUpdateProduct() {
        Product expected = Product.builder()
                .withId(1)
                .withProductName("Whiskey")
                .withBrand("Brand")
                .withPhoto("photo")
                .withPrice(new BigDecimal(100))
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

        catalog.add(Catalog.builder()
                .withId(1)
                .withGroupName("catalog")
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        catalogDao.saveAll(catalog);
        productDao.saveAll(products);
        productDao.addProductOnCatalog(1, 1);
    }
}
