package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.repositories.CatalogRepository;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogServiceTest {
    @Mock
    private CatalogRepository catalogRepository;
    @InjectMocks
    private CatalogServiceImpl catalogService;

    @Test
    void createNewCatalogShouldAddCatalog() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        String groupName = catalog.getGroupName();

        when(catalogRepository.findByGroupName(groupName)).thenReturn(Optional.empty());
        when(catalogRepository.save(catalog)).thenReturn(catalog);
        catalogService.createNewCatalog(catalog);
        verify(catalogRepository).findByGroupName(groupName);
        verify(catalogRepository).save(catalog);
    }

    @Test
    void createNewCatalogThrowEntityAlreadyExistExceptionIfEntityAlreadyExists() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        String catalogName = catalog.getGroupName();

        when(catalogRepository.findByGroupName(catalogName)).thenReturn(Optional.of(catalog));
        Exception exception = assertThrows(EntityAlreadyExistException.class,
                () -> catalogService.createNewCatalog(catalog));
        String expectedMessage = "Specified catalog already exists.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(catalogRepository).findByGroupName(catalogName);
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void findCatalogByIdShouldReturnCatalogWithSpecifiedId() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        Integer catalogId = catalog.getId();

        when(catalogRepository.findById(catalogId)).thenReturn(Optional.of(catalog));
        catalogService.findCatalogById(catalogId);
        verify(catalogRepository).findById(catalogId);
    }

    @Test
    void findCatalogShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Integer nonExistentId = 1;

        when(catalogRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> catalogService.findCatalogById(nonExistentId));
        String expectedMessage = "Specified catalog is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(catalogRepository).findById(nonExistentId);
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void showCatalogShouldReturnAllProductCategories() {
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
    void removeCatalogByIdShouldRemoveCatalog() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        Integer catalogId = catalog.getId();

        when(catalogRepository.findById(catalogId)).thenReturn(Optional.of(catalog));
        doNothing().when(catalogRepository).deleteById(catalogId);
        catalogService.removeCatalogById(catalogId);
        verify(catalogRepository).findById(catalogId);
        verify(catalogRepository).deleteById(catalogId);
    }

    @Test
    void removeCatalogByIdShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Integer nonExistentId = 1;

        when(catalogRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> catalogService.removeCatalogById(nonExistentId));
        String expectedMessage = "Specified catalog is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(catalogRepository).findById(nonExistentId);
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    void updateCatalogShouldUpdateCatalog() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        Integer catalogId = catalog.getId();

        when(catalogRepository.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(catalogRepository.save(catalog)).thenReturn(catalog);
        catalogService.updateCatalog(catalog);
        verify(catalogRepository).save(catalog);
    }

    @Test
    void updateCatalogShouldThrowEntityNotFoundExceptionIfNoSuchEntityExists() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        Integer nonExistentId = 1;

        when(catalogRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> catalogService.updateCatalog(catalog));
        String expectedMessage = "Specified catalog is not found.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(catalogRepository).findById(nonExistentId);
        verifyNoMoreInteractions(catalogRepository);
    }
}
