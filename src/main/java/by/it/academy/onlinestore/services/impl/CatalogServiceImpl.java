package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.repositories.CatalogRepository;
import by.it.academy.onlinestore.services.CatalogService;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Service class for managing catalog.
 */
@Slf4j
@Repository
@AllArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private static final String CATALOG_ALREADY_EXISTS_EXCEPTION = "Specified catalog already exists.";
    private static final String CATALOG_IS_NOT_FOUND_EXCEPTION = "Specified catalog is not found.";
    private final CatalogRepository catalogRepository;

    /**
     * Returns created cart in table mapped by calling CatalogRepository.save(catalog)
     * @param catalog the entity to save
     * @throws EntityExistsException if catalog with specified name already exists in database
     * @return non-null value with defined id
     */
    @Override
    public Catalog createNewCatalog(Catalog catalog) {
        if (catalogRepository.findByGroupName(catalog.getGroupName()).isPresent()) {
            throw new EntityExistsException(CATALOG_ALREADY_EXISTS_EXCEPTION);
        }
        return catalogRepository.save(catalog);
    }

    /**
     * Returns the catalog by the defined id by calling CatalogRepository.findById(id) if specified catalog exists
     * otherwise throws EntityNotFoundException
     * @param id the id of the catalog to retrieve
     * @throws EntityNotFoundException if catalog with specified id is not present in database
     * @return the non-null catalog with defined id
     */
    @Override
    public Catalog findCatalogById(Integer id) {
        return catalogRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(CATALOG_IS_NOT_FOUND_EXCEPTION));
    }

    /**
     * Returns the list of all catalogs by calling CatalogRepository.findAll()
     * @return the list of all catalogs
     */
    @Override
    public List<Catalog> showCatalog() {
        return catalogRepository.findAll();
    }

    /**
     * Deletes catalog with param id from the table mapped by calling CatalogRepository.deleteById(id),
     * if specified catalog exists, otherwise throws EntityNotFoundException
     * @throws EntityNotFoundException if catalog with specified id is not present in database
     * @param id the id of the cart to delete
     */
    @Override
    public void removeCatalogById(Integer id) {
        if (!catalogRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND_EXCEPTION);
        }
        catalogRepository.deleteById(id);
    }

    /**
     * Updates catalog in table mapped by calling CatalogRepository.save(catalog) if specified catalog exists,
     * otherwise throws EntityNotFoundException
     * @param catalog value to update catalog in database
     * @throws EntityNotFoundException if catalog with id defined in param catalog is not present in database
     * @return updates catalog
     */
    @Override
    public Catalog updateCatalog(Catalog catalog) {
        if (!catalogRepository.findById(catalog.getId()).isPresent()) {
            throw new EntityNotFoundException(CATALOG_IS_NOT_FOUND_EXCEPTION);
        }
        catalog = catalogRepository.save(catalog);
        return catalog;
    }
}
