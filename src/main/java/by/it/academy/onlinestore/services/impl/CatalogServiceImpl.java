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
        if (catalogRepository.findByGroupName(catalog.getGroupName()).isPresent()) {
            throw new EntityAlreadyExistException(CATALOG_ALREADY_EXISTS);
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public Catalog findCatalogById(Integer id) {
        return catalogRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(CATALOG_IS_NOT_FOUND));
    }

    @Override
    public List<Catalog> showCatalog() {
        return catalogRepository.findAll();
    }

    @Override
    public void removeCatalogById(Integer id) {
        if (!catalogRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        catalogRepository.deleteById(id);
    }

    @Override
    public Catalog updateCatalog(Catalog catalog) {
        if (!catalogRepository.findById(catalog.getId()).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND);
        }
        catalog = catalogRepository.save(catalog);
        return catalog;
    }
}
