package by.it.academy.onlinestore.services.validator;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.services.exeption.InvalidCatalogNameException;
import by.it.academy.onlinestore.services.exeption.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class CatalogValidator implements Validator<Catalog> {
    private static final Logger lOGGER = LoggerFactory.getLogger(CatalogValidator.class);
    private static final Pattern CATALOG_NAME_PATTERN = Pattern.compile("^[а-яА-Яa-zA-Z]+$");
    @Override
    public void validate(Catalog catalog) {
        validateCatalogName(catalog.getGroupName());
    }

    private void validateCatalogName(String groupName) {
        lOGGER.info("Validating catalog name");
        if (!CATALOG_NAME_PATTERN.matcher(groupName).matches()) {
            lOGGER.error("Catalog name validation failed.");
            throw new InvalidCatalogNameException
                    ("Catalog name contains non-characters");
        }
    }
}
