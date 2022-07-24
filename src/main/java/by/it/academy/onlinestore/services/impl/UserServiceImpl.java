package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.repositories.UserRepository;
import by.it.academy.onlinestore.services.UserService;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class for managing user.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_ALREADY_EXISTS_EXCEPTION = "Specified user already exists.";
    private static final String USER_IS_NOT_FOUND_EXCEPTION = "Specified user is not found.";

    private final UserRepository userRepository;

    /**
     * Returns created user in table mapped by calling UserRepository.save(user)
     * @throws EntityExistsException if user with specified email already exists in database
     * @param user the entity to save
     * @return non-null value with defined id
     */
    @Override
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EntityExistsException(USER_ALREADY_EXISTS_EXCEPTION);
        }
        return userRepository.save(user);
    }

    /**
     * Returns the list of all user by calling UserRepository.findAll()
     * @return the list of all users
     */
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    /**
     * Returns the user by the defined id by calling UserRepository.findById(id) if specified user exists,
     * otherwise throws EntityNotFoundException
     * @param id the id of the user to retrieve
     * @throws EntityNotFoundException if user with specified id is not present in database
     * @return the non-null user with defined id
     */
    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(USER_IS_NOT_FOUND_EXCEPTION));
    }
}
