package by.it.academy.onlinestore.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class Cart {
    private final Integer id;
    private final List<OrderItem> orderItems;
    private final User user;
    private final BigDecimal totalSum;
}
