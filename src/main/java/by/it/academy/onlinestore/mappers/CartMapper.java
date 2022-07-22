package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.CartDto;
import by.it.academy.onlinestore.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "orderItems", target = "orderItemsDto")
    @Mapping(source = "user", target = "userRequestDto")
    CartDto convertToCartDto(Cart cart);

    @Mapping(source = "orderItemsDto", target = "orderItems")
    @Mapping(source = "userRequestDto", target = "user")
    Cart convertToCart(CartDto cartDto);
}
