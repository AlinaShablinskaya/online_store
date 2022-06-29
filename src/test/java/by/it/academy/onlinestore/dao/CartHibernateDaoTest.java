package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.CartHibernateDaoImpl;
import by.it.academy.onlinestore.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CartHibernateDaoTest {
    private final TestConfig testConfig = new TestConfig();
    private final List<Cart> carts = new ArrayList<>();
    private CartDao cartDao;

    @BeforeEach
    private void prepareTables() throws IOException {
        testConfig.createTables();
        cartDao = new CartHibernateDaoImpl();
        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddCartToTheDatabase() {
        Cart expected = Cart.builder()
                .withId(3)
                .withTotalSum(BigDecimal.valueOf(40))
                .build();

        cartDao.save(expected);
        Cart actual = cartDao.findById(3).get();
        assertEquals(expected, actual);
    }

    @Test
    void saveAllShouldAddCartsToTheDatabase() {
        List<Cart> expected = new ArrayList<>();
        expected.add(Cart.builder()
                .withId(3)
                .withTotalSum(BigDecimal.valueOf(40))
                .build());

        expected.add(Cart.builder()
                .withId(4)
                .withTotalSum(BigDecimal.valueOf(40))
                .build());

        cartDao.saveAll(expected);
        List<Cart> actual = cartDao.findAll(2, 2);
        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByIdShouldReturnCartWhenGetId() {
        Cart expected = Cart.builder()
                .withId(1)
                .withTotalSum(BigDecimal.valueOf(20))
                .build();

        Cart actual = cartDao.findById(1).orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfCartOnPageWhenGetParameters() {
        List<Cart> expected = new ArrayList<>();

        expected.add(Cart.builder()
                .withId(1)
                .withTotalSum(BigDecimal.valueOf(20))
                .build());

        expected.add(Cart.builder()
                .withId(2)
                .withTotalSum(BigDecimal.valueOf(40))
                .build());

        List<Cart> actual = cartDao.findAll(1, 2);
        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {

        Cart expected = Cart.builder()
                .withId(1)
                .withTotalSum(BigDecimal.valueOf(40))
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
        carts.add(Cart.builder()
                .withId(1)
                .withTotalSum(BigDecimal.valueOf(20))
                .build());

        carts.add(Cart.builder()
                .withId(2)
                .withTotalSum(BigDecimal.valueOf(40))
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        cartDao.saveAll(carts);
    }
}
