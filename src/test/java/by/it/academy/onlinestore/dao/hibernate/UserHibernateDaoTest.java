package by.it.academy.onlinestore.dao.hibernate;

import by.it.academy.onlinestore.dao.UserDao;
import by.it.academy.onlinestore.dao.exception.DataBaseRuntimeException;
import by.it.academy.onlinestore.dao.impl.hibernate.UserHibernateDaoImpl;
import by.it.academy.onlinestore.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserHibernateDaoTest {
    private final TestConfig testConfig = new TestConfig();
    private final List<User> users = new ArrayList<>();
    private UserDao userDao;

    @BeforeEach
    private void prepareTables() throws IOException {
        testConfig.createTables();
        userDao = new UserHibernateDaoImpl();
        createTestData();
        insertTestDataToDB();
    }

    @Test
    void saveShouldAddUserToTheDatabase() {
        User expected = User.builder()
                .withId(3)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build();

        userDao.save(expected);
        User actual = userDao.findById(3).get();

        assertEquals(expected, actual);
    }

    @Test
    void saveShouldAddListOfUsersToTheDatabase() {
        List<User> expected = new ArrayList<>();

        expected.add(User.builder()
                .withId(3)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build());

        expected.add(User.builder()
                .withId(4)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build());

        userDao.saveAll(expected);
        List<User> actual = userDao.findAll();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findByIdShouldReturnProductWhenGetId() {
        User expected = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build();

        User actual = userDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnListOfProductOnPageWhenGetParameters() {
        List<User> expected = new ArrayList<>();

        expected.add(User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build());

        expected.add(User.builder()
                .withId(2)
                .withFirstName("SecondUser")
                .withLastName("LastName")
                .withEmail("SecondLogin")
                .withPassword("78954")
                .build());

        List<User> actual = userDao.findAll(1, 2);

        assertThat(actual).containsAll(expected);
    }

    @Test
    void findAllShouldReturnListOfProductWhenGetParameters() {
        List<User> expected = new ArrayList<>();

        expected.add(User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build());

        expected.add(User.builder()
                .withId(2)
                .withFirstName("SecondUser")
                .withLastName("LastName")
                .withEmail("SecondLogin")
                .withPassword("78954")
                .build());

        List<User> actual = userDao.findAll();

        assertThat(actual).containsAll(expected);
    }

    @Test
    void updateShouldUpdateProduct() {
        User expected = User.builder()
                .withId(1)
                .withFirstName("NewName")
                .withLastName("NewLastName")
                .withEmail("NewLogin")
                .withPassword("12345")
                .build();

        userDao.update(expected);
        User actual = userDao.findById(1).orElse(null);

        assertEquals(expected, actual);
    }

    @Test
    void findByEmailShouldReturnUserWhenGetEmail() {
        User expected = User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
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
        users.add(User.builder()
                .withId(1)
                .withFirstName("FirstUser")
                .withLastName("LastName")
                .withEmail("Login")
                .withPassword("12345")
                .build());

        users.add(User.builder()
                .withId(2)
                .withFirstName("SecondUser")
                .withLastName("LastName")
                .withEmail("SecondLogin")
                .withPassword("78954")
                .build());
    }

    private void insertTestDataToDB() throws DataBaseRuntimeException {
        userDao.saveAll(users);
    }
}
