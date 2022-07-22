package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> findAllUser();

    User findUserById(Integer id);
}
