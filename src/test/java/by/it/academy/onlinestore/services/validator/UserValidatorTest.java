package by.it.academy.onlinestore.services.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.exeption.InvalidEmailException;
import by.it.academy.onlinestore.services.exeption.InvalidNameException;
import by.it.academy.onlinestore.services.exeption.InvalidPasswordException;
import org.junit.jupiter.api.Test;


public class UserValidatorTest {
    private final Validator<User> validator = new UserValidator();

    @Test
    void validateShouldNotThrowException() {
        User user = User.builder()
                .withId(1)
                .withFirstName("Name")
                .withLastName("Surname")
                .withEmail("name@mail.ru")
                .withPassword("Aa123456")
                .build();

        assertDoesNotThrow(() -> validator.validate(user));
    }

    @Test
    void validateShouldThrowExceptionForInvalidUserEmail() {
        User user = User.builder()
                .withId(1)
                .withFirstName("Name")
                .withLastName("Surname")
                .withEmail("name@maru")
                .withPassword("Aa123456")
                .build();

        Exception exception = assertThrows(InvalidEmailException.class, () -> validator.validate(user));

        String expectedMessage = "Email invalid";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void validateShouldThrowExceptionForInvalidUserPassword() {
        User user = User.builder()
                .withId(1)
                .withFirstName("Name")
                .withLastName("Surname")
                .withEmail("name@mail.ru")
                .withPassword("a1456")
                .build();

        Exception exception = assertThrows(InvalidPasswordException.class, () -> validator.validate(user));

        String expectedMessage =
                "Password must have at least 8 symbols, 1 upper-case symbol, 1 digit and 1 special character";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void validateShouldThrowExceptionForInvalidUserName() {
        User user = User.builder()
                .withId(1)
                .withFirstName("n124")
                .withLastName("Surname")
                .withEmail("name@mail.ru")
                .withPassword("Aa123456")
                .build();

        Exception exception = assertThrows(InvalidNameException.class, () -> validator.validate(user));

        String expectedMessage = "Name is less than 2 symbols or contains non-characters";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void validateShouldThrowExceptionForInvalidUserLastName() {
        User user = User.builder()
                .withId(1)
                .withFirstName("Name")
                .withLastName("su..2")
                .withEmail("name@mail.ru")
                .withPassword("Aa123456")
                .build();

        Exception exception = assertThrows(InvalidNameException.class, () -> validator.validate(user));

        String expectedMessage = "Name is less than 2 symbols or contains non-characters";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}
