package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.repositories.CatalogRepository;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.CatalogServiceImpl;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("create new catalog should add catalog")
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
    @DisplayName("create new catalog throws entity already exist exception if entity already exists")
    void createNewCatalogThrowEntityAlreadyExistExceptionIfEntityAlreadyExists() {
        Catalog catalog = new Catalog();
        catalog.setId(1);
        catalog.setGroupName("Name");

        String catalogName = catalog.getGroupName();

        when(catalogRepository.findByGroupName(catalogName)).thenReturn(Optional.of(catalog));
        Exception exception = assertThrows(EntityExistsException.class,
                () -> catalogService.createNewCatalog(catalog));
        String expectedMessage = "Specified catalog already exists.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(catalogRepository).findByGroupName(catalogName);
        verifyNoMoreInteractions(catalogRepository);
    }

    @Test
    @DisplayName("find catalog by id should return catalog with specified id")
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
    @DisplayName("find catalog should throw entity not found exception if no such entity exists")
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
    @DisplayName("show catalog should return all product categories")
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
    @DisplayName("remove catalog by id should remove catalog")
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
    @DisplayName("remove catalog by id should throw entity not found exception if no such entity exists")
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
    @DisplayName("update catalog should update catalog")
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
    @DisplayName("update catalog should throw entity not found exception if no such entity exists")
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
