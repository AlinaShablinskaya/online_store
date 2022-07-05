package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.CartDto;
import by.it.academy.onlinestore.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "user", target = "userDto")
    @Mapping(source = "orderItems", target = "orderItemsDto")
    CartDto convertToCartDto(Cart cart);

    @Mapping(source = "userDto", target = "user")
    @Mapping(source = "orderItemsDto", target = "orderItems")
    Cart convertToCart(CartDto cartDto);
}
