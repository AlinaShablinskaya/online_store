package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.catalog.CatalogDto;
import by.it.academy.onlinestore.entities.Catalog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class for catalog
 */
@Mapper
public interface CatalogMapper {
    CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

    /**
     * Converts entity to Dto
     * @param catalog entity to convert
     * @return CatalogDto
     */
    CatalogDto convertToCatalogDto(Catalog catalog);

    /**
     * Converts Dto to entity
     * @param catalogDto Dto to convert
     * @return catalog entity
     */
    Catalog convertToCatalog(CatalogDto catalogDto);
}
