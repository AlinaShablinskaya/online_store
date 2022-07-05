package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.repositories.UserRepository;
import by.it.academy.onlinestore.services.UserService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_ALREADY_EXISTS = "Specified user already exists.";
    private static final String USER_IS_NOT_FOUND = "Specified user is not found.";

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        log.info("Registration user {} started", user);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.error("User {} already exists.", user);
            throw new EntityAlreadyExistException(USER_ALREADY_EXISTS);
        }
        User newUser = userRepository.save(user);
        log.info("User successfully added.");
        return newUser;
    }

    @Override
    public List<User> findAllUser() {
        log.info("Find all user started");
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Integer id) {
        log.info("Find user by id started");
        return userRepository.findById(id).orElseThrow(() -> {
            log.error("User is not found.");
            return new EntityNotFoundException(USER_IS_NOT_FOUND);
        });
    }
}
