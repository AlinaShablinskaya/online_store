package by.it.academy.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import by.it.academy.onlinestore.dao.CatalogDao;
import by.it.academy.onlinestore.dao.ProductDao;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.CustomerAddress;
import by.it.academy.onlinestore.entities.Product;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import by.it.academy.onlinestore.services.impl.CatalogServiceImpl;
import by.it.academy.onlinestore.services.impl.ProductServiceImpl;
import by.it.academy.onlinestore.services.validator.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {
    @Mock
    private CatalogDao catalogDao;

    @Mock
    private Validator<Catalog> validator;

    @InjectMocks
    private CatalogServiceImpl productService;

    @Test
    void createNewCatalogShouldReturnCorrectResult() {
        Catalog catalog = Catalog.builder()
                .withId(1)
                .withGroupName("Group")
                .build();

        when(catalogDao.save(catalog)).thenReturn(Optional.of(catalog));

        productService.createNewCatalog(catalog);

        verify(validator).validate(catalog);
        verify(catalogDao).save(catalog);
    }

    @Test
    void createNewCatalogShouldReturnExceptionWhenGetIncorrectParameters() {
        Catalog catalog = Catalog.builder()
                .withId(1)
                .withGroupName("Group")
                .build();

        when(catalogDao.findByGroupName(catalog.getGroupName())).thenReturn(Optional.of(catalog));
        assertThrows(EntityAlreadyExistException.class, () -> productService.createNewCatalog(catalog));
        verifyNoMoreInteractions(catalogDao);
    }

    @Test
    void findCatalogByIdShouldReturnCorrectResult() {
        Catalog catalog = Catalog.builder()
                .withId(1)
                .withGroupName("Group")
                .build();

        when(catalogDao.findById(catalog.getId())).thenReturn(Optional.of(catalog));
        productService.findCatalogById(catalog.getId());
        verify(catalogDao).findById(catalog.getId());
    }

    @Test
    void findCatalogByIdShouldReturnExceptionWhenGetIncorrectParameters() {
        Catalog catalog = Catalog.builder()
                .withId(1)
                .withGroupName("Group")
                .build();

        when(catalogDao.findById(catalog.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.findCatalogById(catalog.getId()));
        verifyNoMoreInteractions(catalogDao);
    }

    @Test
    void updateCatalogShouldReturnCorrectResult() {
        Catalog catalog = Catalog.builder()
                .withId(1)
                .withGroupName("Group")
                .build();

        when(catalogDao.findById(catalog.getId())).thenReturn(Optional.of(catalog));

        doNothing().when(validator).validate(catalog);
        doNothing().when(catalogDao).update(catalog);

        productService.updateCatalog(catalog);

        verify(validator).validate(catalog);
        verify(catalogDao).update(catalog);
    }

    @Test
    void removeCatalogByIdShouldReturnCorrectResult() {
        Catalog catalog = Catalog.builder()
                .withId(1)
                .withGroupName("Group")
                .build();

        when(catalogDao.findById(catalog.getId())).thenReturn(Optional.of(catalog));
        productService.removeCatalogById(catalog.getId());
        verify(catalogDao).deleteById(catalog.getId());
    }
}
