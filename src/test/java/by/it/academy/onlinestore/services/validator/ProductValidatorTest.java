package by.it.academy.onlinestore.services.validator;


import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.exeption.InvalidPriceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductValidatorTest {
    private final Validator<Product> validator = new ProductValidator();

    @Test
    void validateShouldNotThrowException() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("name")
                .withBrand("brand")
                .withPrice(new BigDecimal(10.50))
                .build();

        assertDoesNotThrow(() -> validator.validate(product));
    }

    @Test
    void validateShouldThrowExceptionForInvalidProductPrice() {
        Product product = Product.builder()
                .withId(1)
                .withProductName("name")
                .withBrand("brand")
                .withPrice(new BigDecimal(150.005))
                .build();

        Exception exception = assertThrows(InvalidPriceException.class, () -> validator.validate(product));

        String expectedMessage = "Price should contains only digits";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
