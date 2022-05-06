package by.it.academy.onlinestore.services.validator;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.services.exeption.InvalidCatalogNameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogValidatorTest {
    private final Validator<Catalog> validator = new CatalogValidator();

    @Test
    void validateShouldNotThrowException() {
        Catalog catalog = Catalog.builder()
                .withId(1)
                .withGroupName("Product")
                .build();

        assertDoesNotThrow(() -> validator.validate(catalog));
    }

    @Test
    void validateShouldThrowExceptionForInvalidCatalogName() {
        Catalog catalog = Catalog.builder()
                .withId(1)
                .withGroupName("Prod/12")
                .build();

        Exception exception = assertThrows(InvalidCatalogNameException.class, () -> validator.validate(catalog));

        String expectedMessage = "Catalog name contains non-characters";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
