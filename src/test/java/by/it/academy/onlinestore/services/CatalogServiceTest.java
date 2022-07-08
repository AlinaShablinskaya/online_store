package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.repositories.CatalogRepository;
import by.it.academy.onlinestore.services.impl.CatalogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogServiceTest {
    @Mock
    private CatalogRepository catalogRepository;
    @InjectMocks
    private CatalogServiceImpl catalogService;

    @Test
    void createNewCatalogShouldReturnCorrectResult() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        when(catalogRepository.save(catalog)).thenReturn(catalog);
        catalogService.createNewCatalog(catalog);
        verify(catalogRepository).save(catalog);
    }

    @Test
    void findCatalogByIdShouldReturnCorrectResult() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        when(catalogRepository.findById(1)).thenReturn(Optional.of(catalog));
        catalogService.findCatalogById(1);
        verify(catalogRepository).findById(1);
    }

    @Test
    void showCatalogShouldReturnCorrectResult() {
        List<Catalog> catalogs = new ArrayList<>();

        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        catalogs.add(catalog);

        when(catalogRepository.findAll()).thenReturn(catalogs);
        catalogService.showCatalog();
        verify(catalogRepository).findAll();
    }

    @Test
    void removeCatalogByIdShouldReturnCorrectResult() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        when(catalogRepository.findById(1)).thenReturn(Optional.of(catalog));
        catalogService.removeCatalogById(1);
        verify(catalogRepository).deleteById(1);
    }

    @Test
    void updateCatalogShouldReturnCorrectResult() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        when(catalogRepository.findById(1)).thenReturn(Optional.of(catalog));
        when(catalogRepository.save(catalog)).thenReturn(catalog);
        catalogService.updateCatalog(catalog);
        verify(catalogRepository).save(catalog);
    }
}
