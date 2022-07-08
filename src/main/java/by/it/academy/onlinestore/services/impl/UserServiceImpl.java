package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.repositories.UserRepository;
import by.it.academy.onlinestore.security.SecurityUser;
import by.it.academy.onlinestore.services.UserService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EntityAlreadyExistException(USER_ALREADY_EXISTS);
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(USER_IS_NOT_FOUND));
    }
}
