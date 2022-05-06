package by.it.academy.onlinestore.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class Product {
    private final Integer id;
    private final String productName;
    private final String brand;
    private final String photo;
    private final BigDecimal price;
}
