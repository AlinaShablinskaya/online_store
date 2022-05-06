package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.entities.Catalog;

import java.util.Optional;

public interface CatalogDao extends CrudDao<Catalog, Integer> {
    Optional<Catalog> findByGroupName(String name);
}
