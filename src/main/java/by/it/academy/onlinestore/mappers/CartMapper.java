package by.it.academy.onlinestore.mappers;

import by.it.academy.onlinestore.dto.cart.CartDto;
import by.it.academy.onlinestore.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper class for cart
 */
@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    /**
     * Converts entity to Dto
     * @param cart entity to convert
     * @return CartDto
     */
    @Mapping(source = "orderItems", target = "orderItemsDto")
    @Mapping(source = "user", target = "userRequestDto")
    CartDto convertToCartDto(Cart cart);

    /**
     * Converts Dto to entity
     * @param cartDto Dto to convert
     * @return cart entity
     */
    @Mapping(source = "orderItemsDto", target = "orderItems")
    @Mapping(source = "userRequestDto", target = "user")
    Cart convertToCart(CartDto cartDto);
}
