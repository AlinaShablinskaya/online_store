package by.it.academy.onlinestore.services.validator;

import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.exeption.InvalidEmailException;
import by.it.academy.onlinestore.services.exeption.InvalidPriceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ProductValidator implements Validator<Product> {
    private static final Logger lOGGER = LoggerFactory.getLogger(ProductValidator.class);
    private static final Pattern PRICE_PATTERN = Pattern.compile("^[0-9]+(.[0-9]{1,2})?$");

    @Override
    public void validate(Product product) {
        validatePrice(product.getPrice());
    }

    private void validatePrice(BigDecimal price) {
        lOGGER.info("Validating price");
        if (!PRICE_PATTERN.matcher(price.toString()).matches()) {
            lOGGER.error("Price validation failed.");
            throw new InvalidPriceException("Price should contains only digits");
        }
    }
}
