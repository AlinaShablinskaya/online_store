package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.CatalogDto;
import by.it.academy.onlinestore.entities.Catalog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CatalogMapper {
    CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

    CatalogDto convertToCatalogDto(Catalog catalog);

    Catalog convertToCatalog(CatalogDto catalogDto);
}
