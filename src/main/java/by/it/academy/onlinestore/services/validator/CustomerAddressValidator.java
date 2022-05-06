package by.it.academy.onlinestore.services.validator;

import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.services.exeption.InvalidPriceException;
import by.it.academy.onlinestore.services.exeption.InvalidZipCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class CustomerAddressValidator implements Validator<CustomerAddress> {
    private static final Logger lOGGER = LoggerFactory.getLogger(CustomerAddressValidator.class);
    private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("^\\d{6}$");

    @Override
    public void validate(CustomerAddress address) {
        validateZipCode(address.getZipcode());
    }

    private void validateZipCode(String zipCode) {
        lOGGER.info("Validating zip code");
        if (!ZIP_CODE_PATTERN.matcher(zipCode).matches()) {
            lOGGER.error("Zip code validation failed.");
            throw new InvalidZipCodeException("Zip code should contains six digits");
        }
    }
}
