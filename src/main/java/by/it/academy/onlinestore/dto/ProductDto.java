package by.it.academy.onlinestore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Integer id;
    private String productName;
    private String brand;
    private String photo;
    private BigDecimal price;
}
