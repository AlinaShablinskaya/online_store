package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.order.OrderItemDto;
import by.it.academy.onlinestore.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class for order Item
 */
@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    /**
     * Converts entity to Dto
     * @param orderItem entity to convert
     * @return OrderItemDto
     */
    @Mapping(source = "product", target = "productDto")
    OrderItemDto convertToOrderItemDto(OrderItem orderItem);

    /**
     * Converts Dto to entity
     * @param orderItemDto Dto to convert
     * @return orderItem entity
     */
    @Mapping(source = "productDto", target = "product")
    OrderItem convertToOrderItem(OrderItemDto orderItemDto);
}
