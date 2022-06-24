package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.UserDao;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.PasswordEncryptor;
import by.it.academy.onlinestore.services.UserService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
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
        log.info("Registration user started");
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            log.error("User {} already exists.", user);
            throw new EntityAlreadyExistException(USER_ALREADY_EXISTS);
        }
        log.info("Validating user");
        validator.validate(user);
        userDao.save(user);
        log.info("User successfully added.");
    }

    @Override
    public List<User> findAllUser(int page, int itemsPerPage) {
        log.info("Find all user started");
        return userDao.findAll(page, itemsPerPage);
    }

    @Override
    public User findUserById(Integer id) {
        log.info("Find user by id started");
        return userDao.findById(id).orElseThrow(() -> {
            log.error("User is not found.");
            return new EntityNotFoundException(USER_IS_NOT_FOUND);
        });
    }

    @Override
    public User findUserByEmail(String email) {
        log.info("Find user by email started");
        return userDao.findByEmail(email).orElseThrow(() -> {
            log.error("User by email = {} is not found.", email);
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
