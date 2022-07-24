package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.catalog.CatalogDto;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.mappers.CatalogMapper;
import by.it.academy.onlinestore.services.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing catalog.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    /**
     * POST : create a new catalog
     * @param catalogDto the catalogDto to create
     * @return the new catalog in body
     */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CatalogDto saveCatalogName(@Valid @RequestBody CatalogDto catalogDto) {
        Catalog catalog = catalogService.createNewCatalog(CatalogMapper.INSTANCE.convertToCatalog(catalogDto));
        return CatalogMapper.INSTANCE.convertToCatalogDto(catalog);
    }

    /**
     * GET /{id} : get catalog by specified id
     * @param catalogId the id of the catalog to retrieve
     * @return the catalog with defined id in body
     */
    @GetMapping("/{id}")
    public CatalogDto findCatalogNameById(@PathVariable(value = "id") Integer catalogId) {
        Catalog catalog = catalogService.findCatalogById(catalogId);
        return CatalogMapper.INSTANCE.convertToCatalogDto(catalog);
    }

    /**
     * GET : get all product categories
     * @return the list of product categories in body
     */
    @GetMapping("/")
    public List<CatalogDto> showAllCatalogName() {
        List<Catalog> allCatalogName = catalogService.showCatalog();
        return allCatalogName.stream()
                .map(CatalogMapper.INSTANCE::convertToCatalogDto)
                .collect(Collectors.toList());
    }

    /**
     * DELETE /{id} : delete catalog by specified id
     * @param catalogId the id of the catalog to delete
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCatalog(@PathVariable(value = "id") Integer catalogId) {
        catalogService.removeCatalogById(catalogId);
    }

    /**
     * PUT : update an existing catalog
     * @param catalogDto the catalogDto to update catalog
     * @return updates catalog in body
     */
    @PutMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CatalogDto updateCatalog(@Valid @RequestBody CatalogDto catalogDto) {
        Catalog catalog = catalogService.updateCatalog(CatalogMapper.INSTANCE.convertToCatalog(catalogDto));
        return CatalogMapper.INSTANCE.convertToCatalogDto(catalog);
    }
}
