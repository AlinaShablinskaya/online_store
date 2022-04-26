package by.it.academy.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import by.it.academy.onlinestore.dao.UserDao;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserShouldReturnCorrectResult() {
        User user = User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withEmail("login")
                .withPassword("1111")
                .build();

        doNothing().when(userDao).save(user);
        userService.createUser(user);
        verify(userDao).save(user);
    }

    @Test
    void findAllUsersShouldReturnCorrectResult() {
        int page = 1;
        int itemsPerPage = 10;

        List<User> users = new ArrayList<>();

        users.add(User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withEmail("login")
                .withPassword("1111")
                .build());

        users.add(User.builder()
                .withId(2)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withEmail("login")
                .withPassword("1111")
                .build());

        when(userDao.findAll(page, itemsPerPage)).thenReturn(users);
        userService.findAllUser(page, itemsPerPage);
        verify(userDao).findAll(page, itemsPerPage);
    }

    @Test
    void findUserByIdShouldReturnCorrectResult() {
        User user = User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withEmail("login")
                .withPassword("1111")
                .build();

        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        userService.findUserById(user.getId());
        verify(userDao).findById(user.getId());
    }

    @Test
    void findUserByIdShouldReturnExceptionWhenGetIncorrectParameters() {
        User user = User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withEmail("login")
                .withPassword("1111")
                .build();

        when(userDao.findById(user.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(user.getId()));
        verifyNoMoreInteractions(userDao);
    }

    @Test
    void findUserByEmailShouldReturnCorrectResult() {
        User user = User.builder()
                .withId(1)
                .withFirstName("firstName")
                .withLastName("lastName")
                .withEmail("login")
                .withPassword("1111")
                .build();

        when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        userService.findUserByEmail(user.getEmail());
        verify(userDao).findByEmail(user.getEmail());
    }

}
