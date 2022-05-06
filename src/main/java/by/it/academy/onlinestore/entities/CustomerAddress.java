package by.it.academy.onlinestore.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder(setterPrefix = "with")
public class CustomerAddress {
    private final Integer id;
    private final String zipcode;
    private final String country;
    private final String street;
}
