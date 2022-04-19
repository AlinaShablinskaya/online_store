package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.UserDao;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.UserService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger lOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_ALREADY_EXISTS = "Specified user already exists.";
    private static final String USER_IS_NOT_FOUND = "Specified user is not found.";

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(User user) {
        if (userDao.findById(user.getId()).isPresent()) {
            throw new EntityAlreadyExistException(USER_ALREADY_EXISTS);
        }
        userDao.save(user);

        lOGGER.info("User successfully added.");
    }

    @Override
    public List<User> findAllUser(int page, int itemsPerPage) {
        return userDao.findAll(page, itemsPerPage);
    }

    @Override
    public User findUserById(Integer id) {
        return userDao.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException(USER_IS_NOT_FOUND);
        });
    }
}
