package by.it.academy.onlinestore.dto.order;

import by.it.academy.onlinestore.dto.product.ProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Integer id;
    private Integer amount;
    private BigDecimal totalPrice;
    private ProductDto productDto;
}
