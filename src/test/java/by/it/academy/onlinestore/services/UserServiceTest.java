package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.repositories.UserRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser() {
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
    void findAllUser() {
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
    void findUserById() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Name");
        user.setEmail("email");
        user.setPassword("pass");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.findUserById(user.getId());
        verify(userRepository).findById(user.getId());
    }
}
