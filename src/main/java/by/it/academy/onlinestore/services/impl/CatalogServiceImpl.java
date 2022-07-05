package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.repositories.CatalogRepository;
import by.it.academy.onlinestore.services.CatalogService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private static final String CATALOG_ALREADY_EXISTS = "Specified catalog already exists.";
    private static final String CATALOG_IS_NOT_FOUND = "Specified catalog is not found.";
    private final CatalogRepository catalogRepository;

    @Override
    public Catalog createNewCatalog(Catalog catalog) {
        log.info("Adding catalog {} started", catalog);
        if (catalogRepository.findByGroupName(catalog.getGroupName()).isPresent()) {
            log.error("Catalog {} already exists in database", catalog);
            throw new EntityAlreadyExistException(CATALOG_ALREADY_EXISTS);
        }
        Catalog newCatalog = catalogRepository.save(catalog);
        log.info("Catalog {} successfully added.", catalog);
        return  newCatalog;
    }

    @Override
    public Catalog findCatalogById(Integer id) {
        log.info("Find catalog by id = {} started", id);
        return catalogRepository.findById(id).orElseThrow(() -> {
            log.error("Catalog is not found");
            return new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        });
    }

    @Override
    public List<Catalog> showCatalog() {
        log.info("Find all catalog started");
        return catalogRepository.findAll();
    }

    @Override
    public void removeCatalogById(Integer id) {
        log.info("Catalog delete by id = {} started", id);
        if (!catalogRepository.findById(id).isPresent()) {
            log.error("Catalog is not found");
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        catalogRepository.deleteById(id);
        log.info("Catalog successfully deleted by id = {}.", id);
    }

    @Override
    public Catalog updateCatalog(Catalog catalog) {
        log.info("Updating catalog {} started", catalog);
        if (!catalogRepository.findById(catalog.getId()).isPresent()) {
            log.error("Catalog {} is not found", catalog);
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        catalog = catalogRepository.save(catalog);
        log.info("Catalog {} successfully updated.", catalog);
        return catalog;
    }
}
