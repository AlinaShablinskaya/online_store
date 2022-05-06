package by.it.academy.onlinestore.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class OrderItem {
    private final Integer id;
    private final Integer amount;
    private final Product product;
}
