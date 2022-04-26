package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.entities.User;

import java.util.Optional;

public interface UserDao extends CrudDao<User, Integer> {
    Optional<User> findByEmail(String email);
}
