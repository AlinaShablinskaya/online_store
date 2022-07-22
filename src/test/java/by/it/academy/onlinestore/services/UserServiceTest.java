package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.repositories.UserRepository;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserShouldSaveNewUser() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Name");
        user.setEmail("email");
        user.setPassword("pass");

        when(userRepository.save(user)).thenReturn(user);
        userService.createUser(user);
        verify(userRepository).save(user);
    }

    @Test
    void createUserThrowEntityAlreadyExistExceptionIfEntityAlreadyExists() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Name");
        user.setEmail("email");
        user.setPassword("pass");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Exception exception = assertThrows(EntityAlreadyExistException.class,
                () -> userService.createUser(user));
        String expectedMessage = "Specified user already exists.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(userRepository).findByEmail(user.getEmail());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findAllUserShouldShowAllUsers() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setId(1);
        user.setFirstName("Name");
        user.setEmail("email");
        user.setPassword("pass");

        users.add(user);

        when(userRepository.findAll()).thenReturn(users);
        userService.findAllUser();
        verify(userRepository).findAll();
    }

    @Test
    void findUserByIdShouldReturnUserWithSpecifiedId() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Name");
        user.setEmail("email");
        user.setPassword("pass");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.findUserById(user.getId());
        verify(userRepository).findById(user.getId());
    }

    @Test
    void findUserByIdShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Name");
        user.setEmail("email");
        user.setPassword("pass");

        Integer nonExistentId = 1;

        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> userService.findUserById(nonExistentId));
        String expectedMessage = "Specified user is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(userRepository).findById(nonExistentId);
        verifyNoMoreInteractions(userRepository);
    }
}
