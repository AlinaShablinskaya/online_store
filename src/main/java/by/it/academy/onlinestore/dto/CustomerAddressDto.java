package by.it.academy.onlinestore.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class CustomerAddressDto {
    private Integer id;
    @Pattern(regexp = "^\\\\d{6}$",
            message = "Zip code should contains six digits")
    private String zipcode;
    private String country;
    private String street;
}
