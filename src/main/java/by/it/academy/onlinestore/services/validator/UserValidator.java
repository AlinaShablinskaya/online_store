package by.it.academy.onlinestore.services.validator;

import by.it.academy.onlinestore.entities.User;

import java.util.regex.Pattern;

import by.it.academy.onlinestore.services.exeption.InvalidEmailException;
import by.it.academy.onlinestore.services.exeption.InvalidNameException;
import by.it.academy.onlinestore.services.exeption.InvalidPasswordException;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserValidator implements Validator<User> {
    private static final Logger lOGGER = LoggerFactory.getLogger(UserValidator.class);
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+$");

    @Override
    public void validate(User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        validateName(user.getFirstName());
        validateName(user.getLastName());
    }

    private void validateEmail(String email) {
        lOGGER.info("Validating user email");
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            lOGGER.error("Email validation failed.");
            throw new InvalidEmailException("Email invalid");
        }
    }

    private void validatePassword(String password) {
        lOGGER.info("Validating user password");
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            lOGGER.error("Password validation failed.");
            throw new InvalidPasswordException
                    ("Password must have at least 8 symbols, 1 upper-case symbol, 1 digit and 1 special character");
        }
    }

    private void validateName(String name) {
        lOGGER.info("Validating user name");
        if (!NAME_PATTERN.matcher(name).matches()) {
            lOGGER.error("Name validation failed.");
            throw new InvalidNameException
                    ("Name is less than 2 symbols or contains non-characters");
        }
    }
}
