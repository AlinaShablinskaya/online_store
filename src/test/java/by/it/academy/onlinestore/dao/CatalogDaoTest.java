package by.it.academy.onlinestore.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;

import by.it.academy.onlinestore.dao.impl.CatalogDaoImpl;
import by.it.academy.onlinestore.entities.Catalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CatalogDaoTest {
    private final static String PROPERTIES = "src/test/resources/h2.properties";
    private final static String SCRIPT_SQL = "src/test/resources/schema.sql";

    private List<Catalog> catalogs = new ArrayList<>();
    private DBConnector connector;
    private TableCreator tableCreator;
    private CatalogDao catalogDao;

    @BeforeEach
    private void prepareTables() {
        connector = new DBConnector(PROPERTIES);
        tableCreator = new TableCreator(connector);
        catalogDao = new CatalogDaoImpl(connector);

        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddCatalogToTheDatabase() {

        Catalog expected = Catalog.builder()
                .withId(3)
                .withName("Wine")
                .build();

        catalogDao.save(expected);
        Catalog actual = catalogDao.findById(3).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldAddListOfProductToTheDatabase() {
        List<Catalog> expected = new ArrayList<>();

        expected.add(Catalog.builder()
                .withId(3)
                .withName("Wine")
                .build());

        expected.add(Catalog.builder()
                .withId(4)
                .withName("Wine")
                .build());

        catalogDao.saveAll(expected);
        List<Catalog> actual = catalogDao.findAll(2, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByIdShouldReturnProductWhenGetId() {
        Catalog expected = Catalog.builder()
                .withId(1)
                .withName("Rum")
                .build();

        Catalog actual = catalogDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfProductWhenGetParameters() {
        List<Catalog> expected = new ArrayList<>();

        expected.add(Catalog.builder()
                .withId(1)
                .withName("Rum")
                .build());

        expected.add(Catalog.builder()
                .withId(2)
                .withName("Beer")
                .build());

        List<Catalog> actual = catalogDao.findAll(0, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {
        Catalog expected = Catalog.builder()
                .withId(1)
                .withName("Whiskey")
                .build();

        catalogDao.update(expected);
        Catalog actual = catalogDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdShouldDeleteProductWhenGetId() {
        catalogDao.deleteById(1);
        Catalog actual = catalogDao.findById(1).orElse(null);

        assertThat(actual).isNull();
    }

    private void createTestData() {
        tableCreator.runScript(SCRIPT_SQL);

        catalogs.add(Catalog.builder()
                .withId(1)
                .withName("Rum")
                .build());

        catalogs.add(Catalog.builder()
                .withId(2)
                .withName("Beer")
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        catalogDao.saveAll(catalogs);
    }
}
