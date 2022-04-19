package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.User;

import java.util.List;

public interface UserService {
    void createUser(User user);

    List<User> findAllUser(int page, int itemsPerPage);

    User findUserById(Integer id);
}
