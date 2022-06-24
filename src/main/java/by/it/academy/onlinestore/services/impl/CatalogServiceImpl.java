package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CatalogDao;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.services.CatalogService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public class CatalogServiceImpl implements CatalogService {
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
        log.info("Adding catalog {} started", catalog);
        if (catalogDao.findByGroupName(catalog.getGroupName()).isPresent()) {
            log.error("Catalog {} already exists in database", catalog);
            throw new EntityAlreadyExistException(CATALOG_ALREADY_EXISTS);
        }
        log.info("Validating catalog: {}", catalog);
        validator.validate(catalog);
        catalogDao.save(catalog);
        log.info("Catalog {} successfully added.", catalog);
    }

    @Override
    public Catalog findCatalogById(Integer id) {
        log.info("Find catalog by id = {} started", id);
        return catalogDao.findById(id).orElseThrow(() -> {
            log.error("Catalog is not found");
            return new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        });
    }

    @Override
    public List<Catalog> showCatalog() {
        log.info("Find all catalog started");
        return catalogDao.findAll();
    }

    @Override
    public void removeCatalogById(Integer id) {
        log.info("Catalog delete by id = {} started", id);
        if (!catalogDao.findById(id).isPresent()) {
            log.error("Catalog is not found");
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        catalogDao.deleteById(id);
        log.info("Catalog successfully deleted by id = {}.", id);
    }

    @Override
    public void updateCatalog(Catalog catalog) {
        log.info("Updating catalog {} started", catalog);
        if (!catalogDao.findById(catalog.getId()).isPresent()) {
            log.error("Catalog {} is not found", catalog);
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        log.info("Validating catalog: {}", catalog);
        validator.validate(catalog);
        catalogDao.update(catalog);
        log.info("Catalog {} successfully updated.", catalog);
    }
}
