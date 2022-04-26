package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.UserDao;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.PasswordEncryptor;
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
    private final PasswordEncryptor passwordEncryptor;

    public UserServiceImpl(UserDao userDao, PasswordEncryptor passwordEncryptor) {
        this.userDao = userDao;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public void createUser(User user) {
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
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

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(() -> {
            return new EntityNotFoundException(USER_IS_NOT_FOUND);
        });
    }

    @Override
    public boolean login(String email, String password) {
        String encryptPassword = passwordEncryptor.encrypt(password);

        return userDao.findByEmail(email)
                .map(User::getPassword)
                .filter(pass -> pass.equals(encryptPassword))
                .isPresent();
    }
}
