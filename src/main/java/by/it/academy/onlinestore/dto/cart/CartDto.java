package by.it.academy.onlinestore.dto.cart;

import by.it.academy.onlinestore.dto.user.UserRequestDto;
import by.it.academy.onlinestore.entities.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Integer id;
    private UserRequestDto userRequestDto;
    private BigDecimal totalSum;
    private List<OrderItem> orderItemsDto;
}
