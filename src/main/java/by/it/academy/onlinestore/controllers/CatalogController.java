package by.it.academy.onlinestore.controllers;

import by.it.academy.onlinestore.dto.CatalogDto;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.mappers.CatalogMapper;
import by.it.academy.onlinestore.services.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    @PostMapping("/")
    public CatalogDto saveCatalogName(@RequestBody CatalogDto catalogDto) {
        Catalog catalog = catalogService.createNewCatalog(CatalogMapper.INSTANCE.convertToCatalog(catalogDto));
        return CatalogMapper.INSTANCE.convertToCatalogDto(catalog);
    }

    @GetMapping("/{id}")
    public CatalogDto findCatalogNameById(@PathVariable(value = "id") Integer catalogId) {
        Catalog catalog = catalogService.findCatalogById(catalogId);
        return CatalogMapper.INSTANCE.convertToCatalogDto(catalog);
    }

    @GetMapping("/")
    public List<CatalogDto> showAllCatalogName() {
        List<Catalog> allCatalogName = catalogService.showCatalog();
        return allCatalogName.stream()
                .map(CatalogMapper.INSTANCE::convertToCatalogDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteCatalog(@PathVariable(value = "id") Integer catalogId) {
        catalogService.removeCatalogById(catalogId);
    }

    @PutMapping("/")
    public CatalogDto updateCatalog(@RequestBody CatalogDto catalogDto) {
        Catalog catalog = catalogService.updateCatalog(CatalogMapper.INSTANCE.convertToCatalog(catalogDto));
        return CatalogMapper.INSTANCE.convertToCatalogDto(catalog);
    }
}
