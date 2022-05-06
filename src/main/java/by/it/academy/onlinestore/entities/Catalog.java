package by.it.academy.onlinestore.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class Catalog {
    private final Integer id;
    private final String groupName;
    private final List<Product> products;
}
