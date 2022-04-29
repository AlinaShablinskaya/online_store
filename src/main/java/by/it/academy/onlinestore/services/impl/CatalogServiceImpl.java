package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.CatalogDao;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.services.CatalogService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CatalogServiceImpl implements CatalogService {
    private static final Logger lOGGER = LoggerFactory.getLogger(CatalogServiceImpl.class);
    private static final String CATALOG_ALREADY_EXISTS = "Specified catalog already exists.";
    private static final String CATALOG_IS_NOT_FOUND = "Specified catalog is not found.";

    private final CatalogDao catalogDao;

    public CatalogServiceImpl(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    @Override
    public void createNewCatalog(Catalog catalog) {
        if (catalogDao.findById(catalog.getId()).isPresent()) {
            throw new EntityAlreadyExistException(CATALOG_ALREADY_EXISTS);
        }
        catalogDao.save(catalog);

        lOGGER.info("Catalog successfully added.");
    }

    @Override
    public Catalog findCatalogById(Integer id) {
        return catalogDao.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        });
    }

    @Override
    public List<Catalog> showCatalog() {
        return catalogDao.findAll();
    }

    @Override
    public void removeCatalogById(Integer id) {
        if (!catalogDao.findById(id).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        catalogDao.deleteById(id);

        lOGGER.info("Catalog successfully deleted.");
    }

    @Override
    public void updateCatalog(Catalog catalog) {
        if (!catalogDao.findById(catalog.getId()).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        catalogDao.update(catalog);

        lOGGER.info("Catalog successfully updated.");
    }
}
