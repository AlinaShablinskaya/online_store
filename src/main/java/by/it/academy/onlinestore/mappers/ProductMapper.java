package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.product.ProductDto;
import by.it.academy.onlinestore.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class for cart
 */
@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * Converts entity to Dto
     * @param product entity to convert
     * @return ProductDto
     */
    ProductDto convertToProductDto(Product product);

    /**
     * Converts Dto to entity
     * @param productDto Dto to convert
     * @return product entity
     */
    Product convertToProduct(ProductDto productDto);
}
