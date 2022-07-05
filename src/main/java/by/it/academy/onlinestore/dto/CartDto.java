package by.it.academy.onlinestore.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Integer id;
    private UserDto userDto;
    private BigDecimal totalSum;
    private List<OrderItemDto> orderItemsDto;
}
