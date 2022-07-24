package by.it.academy.onlinestore.dto.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class ProductDto {
    private Integer id;
    @NotBlank
    private String productName;
    @NotBlank
    private String brand;
    @NotBlank
    private String photo;
    private BigDecimal price;
}
