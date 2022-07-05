package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Catalog;

import java.util.List;

public interface CatalogService {
    Catalog createNewCatalog(Catalog catalog);

    Catalog findCatalogById(Integer id);

    List<Catalog> showCatalog();

    void removeCatalogById(Integer id);

    Catalog updateCatalog(Catalog catalog);
}
