package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.OrderItemDto;
import by.it.academy.onlinestore.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "product", target = "productDto")
    OrderItemDto convertToOrderItemDto(OrderItem orderItem);

    @Mapping(source = "productDto", target = "product")
    OrderItem convertToOrderItem(OrderItemDto orderItemDto);
}
