package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CatalogDao;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.services.CatalogService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CatalogServiceImpl implements CatalogService {
    private static final Logger lOGGER = LoggerFactory.getLogger(CatalogServiceImpl.class);
    private static final String CATALOG_ALREADY_EXISTS = "Specified catalog already exists.";
    private static final String CATALOG_IS_NOT_FOUND = "Specified catalog is not found.";

    private final CatalogDao catalogDao;
    private final Validator<Catalog> validator;

    public CatalogServiceImpl(CatalogDao catalogDao, Validator<Catalog> validator) {
        this.catalogDao = catalogDao;
        this.validator = validator;
    }

    @Override
    public void createNewCatalog(Catalog catalog) {
        lOGGER.info("Adding catalog started");
        if (catalogDao.findByGroupName(catalog.getGroupName()).isPresent()) {
            throw new EntityAlreadyExistException(CATALOG_ALREADY_EXISTS);
        }
        lOGGER.info("Validating catalog");
        validator.validate(catalog);
        catalogDao.save(catalog);
        lOGGER.info("Catalog successfully added.");
    }

    @Override
    public Catalog findCatalogById(Integer id) {
        lOGGER.info("Find catalog by id started");
        return catalogDao.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        });
    }

    @Override
    public List<Catalog> showCatalog() {
        lOGGER.info("Find all catalog started");
        return catalogDao.findAll();
    }

    @Override
    public void removeCatalogById(Integer id) {
        lOGGER.info("Catalog delete by id started");
        if (!catalogDao.findById(id).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        catalogDao.deleteById(id);
        lOGGER.info("Catalog successfully deleted.");
    }

    @Override
    public void updateCatalog(Catalog catalog) {
        lOGGER.info("Updating catalog started");
        if (!catalogDao.findById(catalog.getId()).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        lOGGER.info("Validating catalog");
        validator.validate(catalog);
        catalogDao.update(catalog);
        lOGGER.info("Catalog successfully updated.");
    }
}
