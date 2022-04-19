package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Catalog;

public interface CatalogService {
    void createNewCatalog(Catalog catalog);

    Catalog findCatalogById(Integer id);

    void removeCatalogById(Integer id);

    void updateCatalog(Catalog catalog);
}
