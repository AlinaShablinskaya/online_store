package by.it.academy.onlinestore.services.validator;

import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.entities.User;
import by.it.academy.onlinestore.services.exeption.InvalidEmailException;
import by.it.academy.onlinestore.services.exeption.InvalidZipCodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerAddressValidatorTest {
    private final Validator<CustomerAddress> validator = new CustomerAddressValidator();

    @Test
    void validateShouldNotThrowException() {
        CustomerAddress address = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123456")
                .withCountry("Country")
                .withStreet("Street")
                .build();

        assertDoesNotThrow(() -> validator.validate(address));
    }

    @Test
    void validateShouldThrowExceptionForInvalidZipCode() {
        CustomerAddress address = CustomerAddress.builder()
                .withId(1)
                .withZipcode("123jk")
                .withCountry("Country")
                .withStreet("Street")
                .build();

        Exception exception = assertThrows(InvalidZipCodeException.class, () -> validator.validate(address));

        String expectedMessage = "Zip code should contains six digits";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
