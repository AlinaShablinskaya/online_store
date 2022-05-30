package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.UserDao;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.PasswordEncryptor;
import by.it.academy.onlinestore.services.UserService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger lOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_ALREADY_EXISTS = "Specified user already exists.";
    private static final String USER_IS_NOT_FOUND = "Specified user is not found.";

    private final UserDao userDao;
    private final Validator<User> validator;
    private final PasswordEncryptor passwordEncryptor;

    public UserServiceImpl(UserDao userDao, PasswordEncryptor passwordEncryptor, Validator<User> validator) {
        this.userDao = userDao;
        this.passwordEncryptor = passwordEncryptor;
        this.validator = validator;
    }

    @Override
    public void createUser(User user) {
        lOGGER.info("Registration user started");
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new EntityAlreadyExistException(USER_ALREADY_EXISTS);
        }
        lOGGER.info("Validating user");
        validator.validate(user);
        userDao.save(user);
        lOGGER.info("User successfully added.");
    }

    @Override
    public List<User> findAllUser(int page, int itemsPerPage) {
        lOGGER.info("Find all user started");
        return userDao.findAll(page, itemsPerPage);
    }

    @Override
    public User findUserById(Integer id) {
        lOGGER.info("Find user by id started");
        return userDao.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException(USER_IS_NOT_FOUND);
        });
    }

    @Override
    public User findUserByEmail(String email) {
        lOGGER.info("Find user by email started");
        return userDao.findByEmail(email).orElseThrow(() -> {
            return new EntityNotFoundException(USER_IS_NOT_FOUND);
        });
    }

    @Override
    public boolean login(String email, String password) {
        return userDao.findByEmail(email)
                .map(User::getPassword)
                .filter(pass -> pass.equals(password))
                .isPresent();
    }
}
