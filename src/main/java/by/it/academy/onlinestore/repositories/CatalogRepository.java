package by.it.academy.onlinestore.repositories;

import by.it.academy.onlinestore.entities.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
    Optional<Catalog> findByGroupName(String groupName);
}
