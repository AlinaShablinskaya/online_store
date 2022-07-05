package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.ProductDto;
import by.it.academy.onlinestore.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto convertToProductDto(Product product);

    Product convertToProduct(ProductDto productDto);
}
