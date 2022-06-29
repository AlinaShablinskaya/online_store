package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.CatalogHibernateDaoImpl;
import by.it.academy.onlinestore.entities.Catalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CatalogHibernateDaoTest {
    private final TestConfig testConfig = new TestConfig();
    private final List<Catalog> catalogs = new ArrayList<>();
    private CatalogDao catalogDao;

    @BeforeEach
    private void prepareTables() throws IOException {
        testConfig.createTables();
        catalogDao = new CatalogHibernateDaoImpl();
        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddCatalogToTheDatabase() {
        Catalog expected = Catalog.builder()
                .withId(3)
                .withGroupName("Wine")
                .build();

        catalogDao.save(expected);
        Catalog actual = catalogDao.findById(3).get();

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldAddListOfCatalogToTheDatabase() {
        List<Catalog> expected = new ArrayList<>();

        expected.add(Catalog.builder()
                .withId(3)
                .withGroupName("Wine")
                .build());

        expected.add(Catalog.builder()
                .withId(4)
                .withGroupName("Wine")
                .build());

        catalogDao.saveAll(expected);
        List<Catalog> actual = catalogDao.findAll(2, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByIdShouldReturnProductWhenGetId() {
        Catalog expected = Catalog.builder()
                .withId(1)
                .withGroupName("Rum")
                .build();

        Catalog actual = catalogDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findByGroupNameShouldReturnProductWhenGetGroupName() {
        Catalog expected = Catalog.builder()
                .withId(1)
                .withGroupName("Rum")
                .build();

        Catalog actual = catalogDao.findByGroupName(expected.getGroupName()).get();

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfCatalogOnPageWhenGetParameters() {
        List<Catalog> expected = new ArrayList<>();

        expected.add(Catalog.builder()
                .withId(1)
                .withGroupName("Rum")
                .build());

        expected.add(Catalog.builder()
                .withId(2)
                .withGroupName("Beer")
                .build());

        List<Catalog> actual = catalogDao.findAll(1, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findAllShouldReturnListOfProductWhenGetParameters() {
        List<Catalog> expected = new ArrayList<>();

        expected.add(Catalog.builder()
                .withId(1)
                .withGroupName("Rum")
                .build());

        expected.add(Catalog.builder()
                .withId(2)
                .withGroupName("Beer")
                .build());

        List<Catalog> actual = catalogDao.findAll();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {
        Catalog expected = Catalog.builder()
                .withId(1)
                .withGroupName("Whiskey")
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
        catalogs.add(Catalog.builder()
                .withId(1)
                .withGroupName("Rum")
                .build());

        catalogs.add(Catalog.builder()
                .withId(2)
                .withGroupName("Beer")
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        catalogDao.saveAll(catalogs);
    }
}
